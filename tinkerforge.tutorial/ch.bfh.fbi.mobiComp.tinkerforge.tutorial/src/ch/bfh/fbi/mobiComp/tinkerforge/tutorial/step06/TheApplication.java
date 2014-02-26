package ch.bfh.fbi.mobiComp.tinkerforge.tutorial.step06;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;

import com.tinkerforge.BrickMaster;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 * A {@link TinkerforgeApplication} can consist of multiple
 * {@link TinkerforgeApplication}s. Here, the two specialized applications are
 * managed by this one. The only thing this {@link TinkerforgeApplication} does
 * is, to lower the energy-consumption of {@link BrickMaster}s with WiFi
 * extension... if any is provided.
 * 
 * @author reto
 * 
 */
public class TheApplication extends AbstractTinkerforgeApplication {
	private final MasterBrickApplication masterBrickApplication;
	private final MotionDetectorApplication motionDetectorApplication;

	public TheApplication() {
		this.masterBrickApplication = new MasterBrickApplication();
		this.motionDetectorApplication = new MotionDetectorApplication();
		super.addTinkerforgeApplication(this.masterBrickApplication,
				this.motionDetectorApplication);
	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (device instanceof BrickMaster) {
			final BrickMaster masterDevice = (BrickMaster) device;
			try {
				if (masterDevice.isWifiPresent()) {
					System.out.println("The secret key is: "
							+ masterDevice.getWifiEncryption().key);
					masterDevice
							.setWifiPowerMode(BrickMaster.WIFI_POWER_MODE_LOW_POWER);
				}
			} catch (final TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final NotConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
