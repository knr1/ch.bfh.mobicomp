package ch.quantasy.tinkerforge.position;

import ch.quantasy.tinkerforge.position.view.AccelerationProfileView;
import ch.quantasy.tinkerforge.position.view.AltitudeProfileView;
import ch.quantasy.tinkerforge.position.view.ErrorProfileView;
import ch.quantasy.tinkerforge.position.view.VelocityProfileView;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;

import com.tinkerforge.BrickIMU.AccelerationListener;
import com.tinkerforge.BrickIMU.QuaternionListener;
import com.tinkerforge.BrickletBarometer.AltitudeListener;
import com.tinkerforge.Device;

//https://github.com/Tinkerforge/imu-barometer-fusion/blob/master/imu_barometer_fusion.py

public class AltitudeSensorFusionApplication extends
		AbstractTinkerforgeApplication implements AccelerationListener,
		QuaternionListener, AltitudeListener {

	private final IMUApplication imuApplication;
	private final BarometerApplication barometerApplication;
	private double[] latestIMUQuaternion;
	private double[] latestIMUAcceleration;
	private int latestBarometricAltitudeInCentiMeter = Integer.MIN_VALUE;
	private double latestEstimatedAltitudeInMeter;

	public AltitudeSensorFusionApplication() {
		super();
		this.imuApplication = new IMUApplication(this);
		this.barometerApplication = new BarometerApplication(this);
		super.addTinkerforgeApplication(this.imuApplication,
				this.barometerApplication);
	}

	// Update measurements and compute new altitude.
	public void update() {
		if (this.latestIMUQuaternion == null) {
			return;
		}
		if (this.latestIMUAcceleration == null) {
			return;
		}
		if (this.latestBarometricAltitudeInCentiMeter == Integer.MIN_VALUE) {
			return;
		}

		final double[] q = this.latestIMUQuaternion;
		final double[] acceleration = this.latestIMUAcceleration;
		final double altitudeInMeter = this.latestBarometricAltitudeInCentiMeter / 100.0;

		final double[] compensated_acc_q = this
				.getGravityCompensatedAcceleration(q, acceleration);
		final double[] compensated_acc_q_earth = this.getDynamicAcceleration(q,
				compensated_acc_q);

		this.latestEstimatedAltitudeInMeter = this.computeAltitude(
				compensated_acc_q_earth[2], altitudeInMeter);
		
		// updateView

		AltitudeProfileView
				.addEstimatedAltitudeData(this.latestEstimatedAltitudeInMeter);
		AltitudeProfileView
				.addBarometricAltitudeData(this.latestBarometricAltitudeInCentiMeter / 100.0);
		VelocityProfileView.addEstimatedVelocityData(this.estimatedVelocity);
		AccelerationProfileView.addAccelerationData(this.instAcceleration);
		
		ErrorProfileView.addErrorData(this.altitudeErrorI);

	}

	// Remove gravity from accelerometer measurements
	public double[] getGravityCompensatedAcceleration(final double[] q,
			final double[] a) {
		final double qx = Quaternion.getComponent(q, Quaternion.X);
		final double qy = Quaternion.getComponent(q, Quaternion.Y);
		final double qz = Quaternion.getComponent(q, Quaternion.Z);
		final double qw = Quaternion.getComponent(q, Quaternion.W);

		final double[] g = new double[] { 2 * ((qx * qz) - (qw * qy)),
				2 * ((qw * qx) + (qy * qz)),
				((qw * qw) - (qx * qx) - (qy * qy)) + (qz * qz) };

		return new double[] { (a[0] / 1000.0) - g[0], (a[1] / 1000.0) - g[1],
				(a[2] / 1000.0) - g[2], 0.0 };

	}

	// Rotate dynamic acceleration vector from sensor frame to earth frame
	public double[] getDynamicAcceleration(final double[] q,
			final double[] gravityCompensatedAcceleration) {
		final double[] tmp = Quaternion.getMultiplication(q,
				gravityCompensatedAcceleration);
		return Quaternion.getMultiplication(tmp, Quaternion.getConjugatedQ(q));
	}

	private long latestTimeInNanoseconds;
	private double estimatedAltitude;
	private double estimatedVelocity;
	private double altitudeErrorI;
	private double instAcceleration;

	private static final int FACTOR = 1;
	private static final double KP1 = 0.55 * AltitudeSensorFusionApplication.FACTOR; // PI
	// observer
	// velocity
	// gain
	private static final double KP2 = 1.0 * AltitudeSensorFusionApplication.FACTOR; // PI
	// observer
	// position
	// gain
	private static final double KI = 0.001 / AltitudeSensorFusionApplication.FACTOR; // #
																						// PI

	// observer
	// integral

	// gain (bias
	// cancellation)

	// Computes estimation of altitude based on barometer and accelerometer
	// measurements
	// Code is based on blog post from Fabio Varesano:
	// http://www.varesano.net/blog/fabio/complementary-filtering-high-res-barometer-and-accelerometer-reliable-altitude-estimation
	// He seems to have got the idea from the MultiWii project
	public double computeAltitude(final double compensated_acceleration,
			final double altitude) {
		final long currentTimeInNanoseconds = System.nanoTime();
		if (this.latestTimeInNanoseconds == 0) {
			this.estimatedAltitude = altitude;
			this.estimatedVelocity = 0;
			this.altitudeErrorI = 0;
			this.latestTimeInNanoseconds = currentTimeInNanoseconds;
			return this.estimatedAltitude;
		}
		final double dt = (currentTimeInNanoseconds - this.latestTimeInNanoseconds) / 1000000000.0;

		// Estimation Error
		final double altitudeError = altitude - this.estimatedAltitude;
		this.altitudeErrorI += altitudeError;
		this.altitudeErrorI = Math.min(2500.0,
				Math.max(-2500.0, this.altitudeErrorI));

		this.instAcceleration = (compensated_acceleration * 9.80605)
				+ (this.altitudeErrorI * AltitudeSensorFusionApplication.KI);

		// Integrators
		final double delta = (this.instAcceleration * dt)
				+ ((AltitudeSensorFusionApplication.KP1 * dt) * altitudeError);
		this.estimatedAltitude += (((this.estimatedVelocity / 5.0) + delta) * (dt / 2.0))
				+ ((AltitudeSensorFusionApplication.KP2 * dt) * altitudeError);
		this.estimatedVelocity += delta * 10.0;

		this.latestTimeInNanoseconds = currentTimeInNanoseconds;
		return this.estimatedAltitude;
	}

	enum Quaternion {
		X(0), Y(1), Z(2), W(3);
		private final int position;

		private Quaternion(final int position) {
			this.position = position;
		}

		public static double getComponent(final double[] q,
				final Quaternion component) {
			return q[component.position];
		}

		public static double[] getConjugatedQ(final double[] q) {
			return new double[] { -q[0], -q[1], -q[2], q[3] };
		}

		public static double[] getMultiplication(final double[] q1,
				final double[] q2) {
			final double x1 = Quaternion.getComponent(q1, Quaternion.X);
			final double y1 = Quaternion.getComponent(q1, Quaternion.Y);
			final double z1 = Quaternion.getComponent(q1, Quaternion.Z);
			final double w1 = Quaternion.getComponent(q1, Quaternion.W);

			final double x2 = Quaternion.getComponent(q2, Quaternion.X);
			final double y2 = Quaternion.getComponent(q2, Quaternion.Y);
			final double z2 = Quaternion.getComponent(q2, Quaternion.Z);
			final double w2 = Quaternion.getComponent(q2, Quaternion.W);

			return new double[] {
					((w1 * x2) + (x1 * w2) + (y1 * z2)) - (z1 * y2),
					((w1 * y2) + (y1 * w2) + (z1 * x2)) - (x1 * z2),
					((w1 * z2) + (z1 * w2) + (x1 * y2)) - (y1 * x2),
					(w1 * w2) - (x1 * x2) - (y1 * y2) - (z1 * z2) };
		}

	}

	@Override
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
	}

	@Override
	public boolean equals(final Object obj) {
		return this == obj;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void quaternion(final float x, final float y, final float z,
			final float w) {
		this.latestIMUQuaternion = new double[] { x, y, z, w };
		this.update();
	}

	@Override
	public void acceleration(final short x, final short y, final short z) {
		this.latestIMUAcceleration = new double[] { x, y, z };
	}

	@Override
	public void altitude(final int airPressure) {
		this.latestBarometricAltitudeInCentiMeter = airPressure;

	}

}
