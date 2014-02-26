package sensor;

import viewer.FridgeViewer;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletHumidity.HumidityListener;
import com.tinkerforge.Device;
import com.tinkerforge.TinkerforgeException;

import fridgeit.FridgeIt;

/**
 * This class is responsible for receiving, processing and delegating data about
 * the relative ambient-humidity the {@link FridgeSensor} is sensing.
 * 
 * @author reto
 * 
 */
public class HumidityApplication extends AbstractTinkerforgeApplication
		implements HumidityListener {
	private final FridgeIt fridgeIt;

	public HumidityApplication(final FridgeIt fridgeIt) {
		this.fridgeIt = fridgeIt;
	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Humidity) {
			((BrickletHumidity) device).removeHumidityListener(this);
		}

	}

	@Override
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Humidity) {
			final BrickletHumidity humidity = (BrickletHumidity) device;
			humidity.addHumidityListener(this);
			try {
				humidity.setHumidityCallbackPeriod(500);
			} catch (final TinkerforgeException ex) {
			}
		}
	}

	/**
	 * It sends all data to the {@link FridgeViewer}
	 */
	@Override
	public void humidity(final int humidity) {
		this.fridgeIt.setHumidity(humidity);
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final HumidityApplication other = (HumidityApplication) obj;
		return this.fridgeIt==other.fridgeIt;
	}

}
