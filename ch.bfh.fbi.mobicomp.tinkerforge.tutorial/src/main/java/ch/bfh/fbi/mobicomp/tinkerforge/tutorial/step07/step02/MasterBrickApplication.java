package ch.bfh.fbi.mobicomp.tinkerforge.tutorial.step07.step02;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;

import com.tinkerforge.BrickMaster;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

public class MasterBrickApplication extends AbstractTinkerforgeApplication {

	@Override
	public void stackDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent) {
		System.out.println("Communication to Stack lost");

	}

	@Override
	public void stackConnected(final TinkerforgeStackAgent tinkerforgeSatckAgent) {
		System.out.println("Communication to Stack established");

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
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (device instanceof BrickMaster) {
			this.masterConnect((BrickMaster) device);
		}

	}

	private void masterDisconnect(final BrickMaster master) {
		System.out.println("Master has vanished...");
	}

	private void masterConnect(final BrickMaster master) {
		try {
			final int current = master.getStackCurrent();
			final int voltage = master.getStackVoltage();
			final int usbVoltage = master.getUSBVoltage();
			// Get voltage and current from stack (in mV/mA)
			System.out.println("USB Voltage: " + (usbVoltage / 1000.0) + " V");
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
