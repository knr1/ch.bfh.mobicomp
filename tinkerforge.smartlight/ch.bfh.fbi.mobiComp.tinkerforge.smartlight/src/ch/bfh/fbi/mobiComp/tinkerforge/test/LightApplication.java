package ch.bfh.fbi.mobiComp.tinkerforge.test;

import java.util.Timer;
import java.util.TimerTask;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickMaster;
import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.Device;

/**
 * This class represents the logic of the 'smart'light.<br/>
 * It assumes the existence and accessibility of
 * <ul>
 * <li>{@link DualButtonApplication}</li>
 * <li>{@link ConcurrentRemoteSwitchApplication}</li>
 * <li>{@link MotionDetectionApplication}</li>
 * </ul>
 * This application expects two remote-switchable lights at specific addresses:<br/>
 * Socket 'C', House 'A', DeviceCode '1' / '3' <br/>
 * 
 * @author reto
 * 
 */
public class LightApplication extends AbstractTinkerforgeApplication implements
		TinkerforgeApplication {
	private final char HOUSE_CODE = 'A';
	private final short LEFT = 3;
	private final short RIGHT = 1;
	

	private long delayInMilliseconds = 1000 * 60 * 2;

	private final ConcurrentRemoteSwitchApplication concurrentRemoteSwitchApplication;
	private final DualButtonApplication dualButtonApplication;
	private final MotionDetectionApplication motionDetectionApplication;
	private final AmbientLightApplication ambientLightApplication;

	private boolean isAmbientDark;
	
	private boolean leftState;
	private boolean leftMotionInduced;

	private boolean rightState;
	private boolean rightMotionInduced;

	private final Timer timer;
	private TimerTask timerTaskLeft;
	private TimerTask timerTaskRight;

	/**
	 * The LightApplication expects the listed set of Listeners which it will
	 * manage even under rough conditions
	 */
	public LightApplication() {

		timer = new Timer(true);
		this.concurrentRemoteSwitchApplication = new ConcurrentRemoteSwitchApplication();
		this.dualButtonApplication = new DualButtonApplication(this);
		this.motionDetectionApplication = new MotionDetectionApplication(this);
		this.ambientLightApplication=new AmbientLightApplication(this);
		super.addTinkerforgeApplication(this.concurrentRemoteSwitchApplication,dualButtonApplication,
				motionDetectionApplication,ambientLightApplication);

	}

	@Override
	public void deviceDisconnected(TinkerforgeStackAgent stackAgent,
			Device device) {
	}

	@Override
	public void deviceConnected(TinkerforgeStackAgent satckAgent, Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Master) {
			try {
				BrickMaster master = ((BrickMaster) device);
				if (master.isWifiPresent())
					master.setWifiPowerMode(BrickMaster.WIFI_POWER_MODE_LOW_POWER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setAmbientDarkState(boolean isAmbientDark){
		this.isAmbientDark=isAmbientDark;
		System.out.println("Is it dark?: "+this.isAmbientDark);
	}
	public boolean isAmbientDark(){
		return isAmbientDark;
	}
	public void setDelayInMilliseconds(long delay) {
		this.delayInMilliseconds = delay;
	}

	public long getDelayInMilliseconds() {
		return delayInMilliseconds;
	}

	public void setLeftState(boolean switchState) {
		this.leftState = switchState;
		cancelTimerTaskLeft();
		short switchRequest = switchState ? BrickletRemoteSwitch.SWITCH_TO_ON
				: BrickletRemoteSwitch.SWITCH_TO_OFF;
		concurrentRemoteSwitchApplication.setRemoteSwitchSocketC(HOUSE_CODE,
				LEFT, switchRequest);

		dualButtonApplication.setLeftLEDState(!switchState);
	}

	public void setRightState(boolean switchState) {
		this.rightState = switchState;
		cancelTimerTaskRight();
		short switchRequest = switchState ? BrickletRemoteSwitch.SWITCH_TO_ON
				: BrickletRemoteSwitch.SWITCH_TO_OFF;
		concurrentRemoteSwitchApplication.setRemoteSwitchSocketC(HOUSE_CODE,
				RIGHT, switchRequest);

		dualButtonApplication.setRightLEDState(!switchState);
	}

	public boolean getLeftState() {
		return this.leftState;

	}

	public boolean getRightState() {
		return this.rightState;
	}

	public void initiateTimer() {
		if (leftMotionInduced)
			initiateTimerLeft();
		if (rightMotionInduced)
			initiateTimerRight();
	}

	public void motionDetected() {
		if(!isAmbientDark && timerTaskLeft==null && timerTaskRight==null)
			return;
		if (!getLeftState()) {
			setLeftState(true);
			leftMotionInduced = true;

		} else {
			if (leftMotionInduced) {
				cancelTimerTaskLeft();
				leftMotionInduced = true;
			}
		}

		if (!getRightState()) {
			setRightState(true);
			rightMotionInduced = true;
		} else {
			if (rightMotionInduced) {
				cancelTimerTaskRight();
				rightMotionInduced = true;
			}
		}
	}

	private synchronized void cancelTimerTaskLeft() {
		leftMotionInduced = false;
		if (timerTaskLeft == null) {
			return;
		}
		System.out.println("Task left cancelled");
		timerTaskLeft.cancel();
		timer.purge();
		timerTaskLeft = null;

	}

	private synchronized void cancelTimerTaskRight() {
		rightMotionInduced = false;
		if (timerTaskRight == null) {
			return;
		}
		System.out.println("Task right cancelled");
		timerTaskRight.cancel();
		timer.purge();
		timerTaskRight = null;

	}

	private synchronized void initiateTimerLeft() {
		if (timerTaskLeft != null) {
			return;
		}
		System.out.println("Task left initiated");
		timerTaskLeft = new DelayedSwitchOffTaskLeft();
		timer.schedule(timerTaskLeft, delayInMilliseconds);
	}

	private synchronized void initiateTimerRight() {
		if (timerTaskRight != null) {
			return;
		}
		System.out.println("Task right initiated");
		timerTaskRight = new DelayedSwitchOffTaskRight();
		timer.schedule(timerTaskRight, delayInMilliseconds);
	}

	class DelayedSwitchOffTaskLeft extends TimerTask {
		@Override
		public void run() {
			setLeftState(false);
		}
	}

	class DelayedSwitchOffTaskRight extends TimerTask {
		@Override
		public void run() {
			setRightState(false);
		}
	}

	@Override
	public boolean equals(Object obj) {
		return this==obj;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
}
