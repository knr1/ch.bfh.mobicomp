package ch.quantasy.tinkerforge.position;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickIMU;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

public class IMUApplication extends AbstractTinkerforgeApplication {
	private BrickIMU imuBrick;

	private long updatePeriodeInMilliseconds = 1;
	private HightSensorFusionApplication imuApplication;

	public IMUApplication(HightSensorFusionApplication imuApplication) {
		super();
		this.imuApplication = imuApplication;

	}

	@Override
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.IMU) {
			if (this.imuBrick == null) {
				this.imuBrick = (BrickIMU) device;
				this.imuBrick.addQuaternionListener(imuApplication);
				this.imuBrick.addAccelerationListener(imuApplication);
				try {
					this.imuBrick.orientationCalculationOff();
					this.imuBrick.ledsOff();
					this.imuBrick.setQuaternionPeriod(6);
					this.imuBrick
							.setAccelerationPeriod(updatePeriodeInMilliseconds);

				} catch (final TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (final NotConnectedException e) {
					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.areEqual(this.imuBrick, device)) {
			this.imuBrick.removeQuaternionListener(imuApplication);
			this.imuBrick.removeAccelerationListener(imuApplication);
			try {

				this.imuBrick.setQuaternionPeriod(0);
				this.imuBrick.setAccelerationPeriod(0);
				this.imuBrick.ledsOn();
			} catch (TimeoutException | NotConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.imuBrick = null;
		}
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

}
