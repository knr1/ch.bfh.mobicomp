package ch.bfh.fbi.mobiComp.tinkerforge.tutorial.step01;

import java.util.Set;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickMaster;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import examples.Brick.Master.ExampleStackStatus;

/**
 * This does exactly the same as the example in {@link ExampleStackStatus}.
 * However, this implementation is programmed to work in the 'rough' world: It
 * just works even under heavy circumstances! You do not have to know the UIDs
 * and such to make it run. Just have fun programming the logic.
 *<br/> 
 * 
 * By extending the {@link AbstractTinkerforgeApplication} almost everything is
 * set and ready. The only methods to be implemented are:
 * <ul>
 * <li>DeviceConnected: What shall happen with that device...</li>
 * <li>DeviceDisconnected: What shall happen when the device vanished...</li>
 * </ul>
 * Well... there are two more to implement:
 * <ul>
 * <li>equals</li>
 * <li>hashCode</li>
 * </ul>
 * But why that? Because internally all {@link TinkerforgeApplication} are held
 * within a {@link Set}. And that is why it is so important to know when YOU
 * think two {@link TinkerforgeApplication}s should be alike (=equals)
 * 
 * @author reto
 * 
 */
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
