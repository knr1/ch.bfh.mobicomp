package ch.bfh.fbi.mobiComp.tinkerforge.tutorial.step01;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickMaster;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

public class MasterBrickApplication extends AbstractTinkerforgeApplication {

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		System.out.println("Device disconnected: "
				+ TinkerforgeDevice.toString(device));
	}

	@Override
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		System.out.println("Device connected: "
				+ TinkerforgeDevice.toString(device));
		if (device instanceof BrickMaster) {
			this.masterConnect((BrickMaster) device);
		}

	}

	private void masterConnect(final BrickMaster master) {
		try {
			final int current = master.getStackCurrent();
			final int voltage = master.getStackVoltage();
			// Get voltage and current from stack (in mV/mA)
			System.out.println("Stack Voltage: " + (voltage / 1000.0) + " V");
			System.out.println("Stack Current: " + (current / 1000.0) + " A");
		} catch (final TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
