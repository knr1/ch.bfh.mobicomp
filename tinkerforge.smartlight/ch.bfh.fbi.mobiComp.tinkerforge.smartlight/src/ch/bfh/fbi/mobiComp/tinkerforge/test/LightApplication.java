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

		this.timer = new Timer(true);
		this.concurrentRemoteSwitchApplication = new ConcurrentRemoteSwitchApplication();
		this.dualButtonApplication = new DualButtonApplication(this);
		this.motionDetectionApplication = new MotionDetectionApplication(this);
		this.ambientLightApplication = new AmbientLightApplication(this);
		super.addTinkerforgeApplication(this.concurrentRemoteSwitchApplication,
				this.dualButtonApplication, this.motionDetectionApplication,
				this.ambientLightApplication);

	}

	@Override
	public void deviceDisconnected(final TinkerforgeStackAgent stackAgent,
			final Device device) {
	}

	@Override
	public void deviceConnected(final TinkerforgeStackAgent satckAgent,
			final Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Master) {
			try {
				final BrickMaster master = ((BrickMaster) device);
				if (master.isWifiPresent()) {
					master.setWifiPowerMode(BrickMaster.WIFI_POWER_MODE_LOW_POWER);
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setAmbientDarkState(final boolean isAmbientDark) {
		this.isAmbientDark = isAmbientDark;
		System.out.println("Is it dark?: " + this.isAmbientDark);
	}

	public boolean isAmbientDark() {
		return this.isAmbientDark;
	}

	public void setDelayInMilliseconds(final long delay) {
		this.delayInMilliseconds = delay;
	}

	public long getDelayInMilliseconds() {
		return this.delayInMilliseconds;
	}

	public void setLeftState(final boolean switchState) {
		this.leftState = switchState;
		this.cancelTimerTaskLeft();
		final short switchRequest = switchState ? BrickletRemoteSwitch.SWITCH_TO_ON
				: BrickletRemoteSwitch.SWITCH_TO_OFF;
		this.concurrentRemoteSwitchApplication.setRemoteSwitchSocketC(
				this.HOUSE_CODE, this.LEFT, switchRequest);

		this.dualButtonApplication.setLeftLEDState(!switchState);
	}

	public void setRightState(final boolean switchState) {
		this.rightState = switchState;
		this.cancelTimerTaskRight();
		final short switchRequest = switchState ? BrickletRemoteSwitch.SWITCH_TO_ON
				: BrickletRemoteSwitch.SWITCH_TO_OFF;
		this.concurrentRemoteSwitchApplication.setRemoteSwitchSocketC(
				this.HOUSE_CODE, this.RIGHT, switchRequest);

		this.dualButtonApplication.setRightLEDState(!switchState);
	}

	public boolean getLeftState() {
		return this.leftState;

	}

	public boolean getRightState() {
		return this.rightState;
	}

	public void initiateTimer() {
		if (this.leftMotionInduced) {
			this.initiateTimerLeft();
		}
		if (this.rightMotionInduced) {
			this.initiateTimerRight();
		}
	}

	public void motionDetected() {
		if (!this.isAmbientDark && (this.timerTaskLeft == null)
				&& (this.timerTaskRight == null)) {
			return;
		}
		if (!this.getLeftState()) {
			this.setLeftState(true);
			this.leftMotionInduced = true;

		} else {
			if (this.leftMotionInduced) {
				this.cancelTimerTaskLeft();
				this.leftMotionInduced = true;
			}
		}

		if (!this.getRightState()) {
			this.setRightState(true);
			this.rightMotionInduced = true;
		} else {
			if (this.rightMotionInduced) {
				this.cancelTimerTaskRight();
				this.rightMotionInduced = true;
			}
		}
	}

	private synchronized void cancelTimerTaskLeft() {
		this.leftMotionInduced = false;
		if (this.timerTaskLeft == null) {
			return;
		}
		System.out.println("Task left cancelled");
		this.timerTaskLeft.cancel();
		this.timer.purge();
		this.timerTaskLeft = null;

	}

	private synchronized void cancelTimerTaskRight() {
		this.rightMotionInduced = false;
		if (this.timerTaskRight == null) {
			return;
		}
		System.out.println("Task right cancelled");
		this.timerTaskRight.cancel();
		this.timer.purge();
		this.timerTaskRight = null;

	}

	private synchronized void initiateTimerLeft() {
		if (this.timerTaskLeft != null) {
			return;
		}
		System.out.println("Task left initiated");
		this.timerTaskLeft = new DelayedSwitchOffTaskLeft();
		this.timer.schedule(this.timerTaskLeft, this.delayInMilliseconds);
	}

	private synchronized void initiateTimerRight() {
		if (this.timerTaskRight != null) {
			return;
		}
		System.out.println("Task right initiated");
		this.timerTaskRight = new DelayedSwitchOffTaskRight();
		this.timer.schedule(this.timerTaskRight, this.delayInMilliseconds);
	}

	class DelayedSwitchOffTaskLeft extends TimerTask {
		@Override
		public void run() {
			LightApplication.this.setLeftState(false);
		}
	}

	class DelayedSwitchOffTaskRight extends TimerTask {
		@Override
		public void run() {
			LightApplication.this.setRightState(false);
		}
	}

	@Override
	public boolean equals(final Object obj) {
		return this == obj;
	}

	@Override
	public int hashCode() {
		return 0;
	}
}
