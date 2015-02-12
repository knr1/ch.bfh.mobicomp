package ch.quantasy.tinkerforge.smartlight.applications;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.BrickletMotionDetector.DetectionCycleEndedListener;
import com.tinkerforge.BrickletMotionDetector.MotionDetectedListener;
import com.tinkerforge.Device;

/**
 * This class represents the logic for a specific / single
 * {@link BrickletMotionDetector}. <br/>
 * It will manage the first {@link BrickletMotionDetector} that will be
 * presented to this {@link TinkerforgeApplication}.
 * 
 * @author reto
 * 
 */
public class MotionDetectionApplication extends AbstractTinkerforgeApplication
		implements MotionDetectedListener, DetectionCycleEndedListener {

	private final LightApplication lightApplication;

	private BrickletMotionDetector motionDetector;

	public MotionDetectionApplication(final LightApplication lightApplication) {
		this.lightApplication = lightApplication;
	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if ((TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.MotionDetector)
				&& device.equals(this.motionDetector)) {
			((BrickletMotionDetector) device)
					.removeMotionDetectedListener(this);
			((BrickletMotionDetector) device)
					.removeDetectionCycleEndedListener(this);
			this.motionDetector = null;
		}

	}

	@Override
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.MotionDetector) {
			if (this.motionDetector != null) {
				// MotionDetector
				return;
			}
			this.motionDetector = (BrickletMotionDetector) device;
			this.motionDetector.addMotionDetectedListener(this);
			this.motionDetector.addDetectionCycleEndedListener(this);
		}

	}

	@Override
	public void motionDetected() {
		this.lightApplication.motionDetected();
	}

	@Override
	public void detectionCycleEnded() {
		System.out.println("Motion cycle ended");
		this.lightApplication.initiateTimer();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((this.lightApplication == null) ? 0 : this.lightApplication
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final MotionDetectionApplication other = (MotionDetectionApplication) obj;
		if (this.lightApplication == null) {
			if (other.lightApplication != null) {
				return false;
			}
		} else if (!this.lightApplication.equals(other.lightApplication)) {
			return false;
		}
		return true;
	}

}
