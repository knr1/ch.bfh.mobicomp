package ch.bfh.fbi.mobiComp.tinkerforge.tutorial.step07;

import java.util.HashMap;
import java.util.Map;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickMaster;
import com.tinkerforge.BrickMaster.StackCurrentListener;
import com.tinkerforge.BrickMaster.StackVoltageListener;
import com.tinkerforge.BrickMaster.USBVoltageListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

public class MasterBrickApplication extends AbstractTinkerforgeApplication {
	private final Map<Device, MasterListener> listenerMap;

	public MasterBrickApplication() {
		this.listenerMap = new HashMap<Device, MasterListener>();
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
		final MasterListener listener = this.listenerMap.get(master);
		if (listener != null) {
			master.removeStackCurrentListener(listener);
			master.removeStackVoltageListener(listener);
			master.removeUSBVoltageListener(listener);
			this.listenerMap.remove(master);
		}
	}

	private void masterConnect(final BrickMaster master) {
		if (!this.listenerMap.containsKey(master)) {
			final MasterListener listener = new MasterListener(master);
			master.addStackCurrentListener(listener);
			master.addStackVoltageListener(listener);
			master.addUSBVoltageListener(listener);

			try {
				master.setStackCurrentCallbackPeriod(250);
				master.setStackVoltageCallbackPeriod(250);
				master.setUSBVoltageCallbackPeriod(250);
			} catch (final TimeoutException e) {
				// master vanished
			} catch (final NotConnectedException e) {
				// master vanished
			}
			this.listenerMap.put(master, listener);
		}
	}

	class MasterListener implements USBVoltageListener, StackVoltageListener,
			StackCurrentListener {
		private final Device device;

		public MasterListener(final Device device) {
			this.device = device;
		}

		public Device getDevice() {
			return this.device;
		}

		@Override
		public void stackCurrent(final int current) {
			System.out.println(TinkerforgeDevice.toString(this.device)
					+ ": Stack Current: " + (current / 1000.0) + " A");

		}

		@Override
		public void stackVoltage(final int voltage) {
			System.out.println(TinkerforgeDevice.toString(this.device)
					+ ": Stack Voltage: " + (voltage / 1000.0) + " V");

		}

		@Override
		public void usbvoltage(final int voltage) {
			System.out.println(TinkerforgeDevice.toString(this.device)
					+ ": USB Voltage: " + (voltage / 1000.0) + " V");
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
