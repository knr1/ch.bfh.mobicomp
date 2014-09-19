package ch.bfh.fbi.mobicomp.tinkerforge.tutorial.step05;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;

import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.BrickletMotionDetector.DetectionCycleEndedListener;
import com.tinkerforge.BrickletMotionDetector.MotionDetectedListener;
import com.tinkerforge.Device;

public class MotionDetectorApplication extends AbstractTinkerforgeApplication {
	private final MotionDetectorListener motionDetectorListener;

	public MotionDetectorApplication() {
		this.motionDetectorListener = new MotionDetectorListener();
	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (device instanceof BrickletMotionDetector) {
			this.motionDetectorDisconnect((BrickletMotionDetector) device);
		}
	}

	@Override
	public void deviceConnected(final TinkerforgeStackAgent satckAgent,
			final Device device) {
		if (device instanceof BrickletMotionDetector) {
			this.motionDetectionConnect((BrickletMotionDetector) device);
		}
	}

	private void motionDetectorDisconnect(
			final BrickletMotionDetector motionDetector) {
		motionDetector
				.removeDetectionCycleEndedListener(this.motionDetectorListener);
		motionDetector
				.removeMotionDetectedListener(this.motionDetectorListener);
	}

	private void motionDetectionConnect(
			final BrickletMotionDetector motionDetector) {
		motionDetector.addMotionDetectedListener(this.motionDetectorListener);
		motionDetector
				.addDetectionCycleEndedListener(this.motionDetectorListener);
	}

	class MotionDetectorListener implements MotionDetectedListener,
			DetectionCycleEndedListener {

		@Override
		public void motionDetected() {
			System.out.println("--> Motion detection");
		}

		@Override
		public void detectionCycleEnded() {
			System.out.println("--> Motion detection finished");
		}

	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		return this.getClass() == obj.getClass();
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
