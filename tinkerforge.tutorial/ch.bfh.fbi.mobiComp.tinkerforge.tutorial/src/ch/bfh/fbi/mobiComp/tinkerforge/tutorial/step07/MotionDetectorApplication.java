package ch.bfh.fbi.mobiComp.tinkerforge.tutorial.step07;

import java.util.HashMap;
import java.util.Map;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.BrickletMotionDetector.DetectionCycleEndedListener;
import com.tinkerforge.BrickletMotionDetector.MotionDetectedListener;
import com.tinkerforge.Device;

public class MotionDetectorApplication extends AbstractTinkerforgeApplication {
	private final Map<Device, MotionDetectorListener> listenerMap;

	public MotionDetectorApplication() {
		this.listenerMap = new HashMap<Device, MotionDetectorListener>();
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
		final MotionDetectorListener listener = this.listenerMap
				.get(motionDetector);
		if (listener != null) {
			motionDetector.removeDetectionCycleEndedListener(listener);
			motionDetector.removeMotionDetectedListener(listener);

			this.listenerMap.remove(motionDetector);
		}

	}

	private void motionDetectionConnect(
			final BrickletMotionDetector motionDetector) {
		if (!this.listenerMap.containsKey(motionDetector)) {
			final MotionDetectorListener listener = new MotionDetectorListener(
					motionDetector);

			motionDetector.addMotionDetectedListener(listener);
			motionDetector.addDetectionCycleEndedListener(listener);
			this.listenerMap.put(motionDetector, listener);
		}
	}

	class MotionDetectorListener implements MotionDetectedListener,
			DetectionCycleEndedListener {
		private final BrickletMotionDetector device;

		public MotionDetectorListener(final BrickletMotionDetector device) {
			this.device = device;
		}

		public BrickletMotionDetector getDevice() {
			return this.device;
		}

		@Override
		public void motionDetected() {
			System.out.println(TinkerforgeDevice.toString(this.device)
					+ ":  Motion detection");
		}

		@Override
		public void detectionCycleEnded() {
			System.out.println(TinkerforgeDevice.toString(this.device)
					+ ":  Motion detection finished");
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
