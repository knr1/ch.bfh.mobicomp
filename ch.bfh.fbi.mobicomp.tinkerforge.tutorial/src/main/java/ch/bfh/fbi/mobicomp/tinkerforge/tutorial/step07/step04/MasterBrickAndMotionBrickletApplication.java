package ch.bfh.fbi.mobicomp.tinkerforge.tutorial.step07.step04;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import com.tinkerforge.BrickMaster;
import com.tinkerforge.BrickMaster.StackCurrentListener;
import com.tinkerforge.BrickMaster.StackVoltageListener;
import com.tinkerforge.BrickMaster.USBVoltageListener;
import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.BrickletMotionDetector.DetectionCycleEndedListener;
import com.tinkerforge.BrickletMotionDetector.MotionDetectedListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 * This shows how to be interested in different {@link Device}s... Here, the {@link BrickletMotionDetector} is taken
 * into consideration... if any is available.
 *
 * @author reto
 *
 */
public class MasterBrickAndMotionBrickletApplication extends
	AbstractTinkerforgeApplication {

    @Override
    public void deviceDisconnected(
	    final TinkerforgeStackAgent tinkerforgeStackAgent,
	    final Device device) {
	System.out.println("Disconnection of device: " + device);
	if (device instanceof BrickMaster) {
	    this.masterDisconnect((BrickMaster) device);
	}
	if (device instanceof BrickletMotionDetector) {
	    this.motionDetectorDisconnect((BrickletMotionDetector) device);
	}

    }

    @Override
    public void deviceConnected(
	    final TinkerforgeStackAgent tinkerforgeStackAgent,
	    final Device device) {
	System.out.println("Connection of device: " + device);

	if (device instanceof BrickMaster) {
	    this.masterConnect((BrickMaster) device);
	}
	if (device instanceof BrickletMotionDetector) {
	    this.motionDetectionConnect((BrickletMotionDetector) device);
	}

    }

    private final MasterListener listener = new MasterListener();
    private final MotionDetectorListener motionDetectorListener = new MotionDetectorListener();

    private void masterDisconnect(final BrickMaster master) {
	master.removeStackCurrentListener(this.listener);
	master.removeStackVoltageListener(this.listener);
	master.removeUSBVoltageListener(this.listener);
    }

    private void masterConnect(final BrickMaster master) {
	master.addStackCurrentListener(this.listener);
	master.addStackVoltageListener(this.listener);
	master.addUSBVoltageListener(this.listener);

	try {
	    master.setStackCurrentCallbackPeriod(250);
	    master.setStackVoltageCallbackPeriod(250);
	    master.setUSBVoltageCallbackPeriod(250);
	} catch (final TimeoutException e) {
	    // master vanished
	} catch (final NotConnectedException e) {
	    // master vanished
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

    class MasterListener implements USBVoltageListener, StackVoltageListener,
	    StackCurrentListener {

	@Override
	public void stackCurrent(final int current) {
	    System.out.println("Stack Current: " + (current / 1000.0) + " A");

	}

	@Override
	public void stackVoltage(final int voltage) {
	    System.out.println("Stack Voltage: " + (voltage / 1000.0) + " V");

	}

	@Override
	public void usbVoltage(final int voltage) {
	    System.out.println("USB Voltage: " + (voltage / 1000.0) + " V");
	}

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
