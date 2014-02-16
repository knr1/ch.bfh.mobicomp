package ch.bfh.fbi.mobiComp.tinkerforge.test;

import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.BrickletMotionDetector.DetectionCycleEndedListener;
import com.tinkerforge.BrickletMotionDetector.MotionDetectedListener;
import com.tinkerforge.Device;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

public class MotionDetectionApplication extends AbstractTinkerforgeApplication implements MotionDetectedListener,DetectionCycleEndedListener{

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((lightApplication == null) ? 0 : lightApplication.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MotionDetectionApplication other = (MotionDetectionApplication) obj;
		if (lightApplication == null) {
			if (other.lightApplication != null)
				return false;
		} else if (!lightApplication.equals(other.lightApplication))
			return false;
		return true;
	}

	private LightApplication lightApplication;
	
	private BrickletMotionDetector motionDetector;

	public MotionDetectionApplication(LightApplication lightApplication){
		this.lightApplication=lightApplication;
	}
	
	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.MotionDetector &&
				device.equals(motionDetector)) {
			((BrickletMotionDetector) device)
					.removeMotionDetectedListener(this);
			((BrickletMotionDetector) device)
					.removeDetectionCycleEndedListener(this);
			motionDetector=null;
		}

		
	}

	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.MotionDetector) {
			if(motionDetector!=null) // It is programmed for exactly one MotionDetector
				return;
			motionDetector = (BrickletMotionDetector) device;
			motionDetector
					.addMotionDetectedListener(this);
			motionDetector
					.addDetectionCycleEndedListener(this);
		}
		
	}
	
	
	@Override
	public void motionDetected() {
		lightApplication.motionDetected();
	}

	@Override
	public void detectionCycleEnded() {
		System.out.println("Motion cycle ended");
		lightApplication.initiateTimer();
	}


}
