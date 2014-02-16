package ch.bfh.fbi.mobiComp.tinkerforge.tutorial.step06;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;

import com.tinkerforge.BrickMaster;
import com.tinkerforge.BrickMaster.StackCurrentListener;
import com.tinkerforge.BrickMaster.StackVoltageListener;
import com.tinkerforge.BrickMaster.USBVoltageListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

public class MasterBrickApplication extends AbstractTinkerforgeApplication {
	private final MasterListener masterListener;

	public MasterBrickApplication() {
		this.masterListener = new MasterListener();
	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (device instanceof BrickMaster) {
			this.masterDisconnect((BrickMaster) device);
		}
	}

	@Override
	public void deviceConnected(final TinkerforgeStackAgent satckAgent,
			final Device device) {
		if (device instanceof BrickMaster) {
			this.masterConnect((BrickMaster) device);
		}

	}

	private void masterDisconnect(final BrickMaster master) {
		master.removeStackCurrentListener(this.masterListener);
		master.removeStackVoltageListener(this.masterListener);
		master.removeUSBVoltageListener(this.masterListener);
	}

	private void masterConnect(final BrickMaster master) {
		master.addStackCurrentListener(this.masterListener);
		master.addStackVoltageListener(this.masterListener);
		master.addUSBVoltageListener(this.masterListener);

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

	class MasterListener implements USBVoltageListener, StackVoltageListener,
			StackCurrentListener {

		@Override
		public void stackCurrent(final int current) {
			System.out
					.println("-->Stack Current: " + (current / 1000.0) + " A");

		}

		@Override
		public void stackVoltage(final int voltage) {
			System.out
					.println("-->Stack Voltage: " + (voltage / 1000.0) + " V");

		}

		@Override
		public void usbvoltage(final int voltage) {
			System.out.println("USB Voltage: " + (voltage / 1000.0) + " V");
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
