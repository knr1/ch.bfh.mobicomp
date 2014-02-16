package sensor;

import viewer.FridgeViewer;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletHumidity.HumidityListener;
import com.tinkerforge.Device;
import com.tinkerforge.TinkerforgeException;

import fridgeit.FridgeIt;
/**
 * This class is responsible for receiving, processing and delegating data
 * about the relative ambient-humidity the {@link FridgeSensor} is sensing.
 * 
 * @author reto
 * 
 */
public class HumidityApplication extends AbstractTinkerforgeApplication implements HumidityListener{
	private FridgeIt fridgeIt;
	public HumidityApplication(FridgeIt fridgeIt){
		this.fridgeIt=fridgeIt;
	}
	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Humidity) {
			((BrickletHumidity) device)
					.removeHumidityListener(this);
		}
		
	}

	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
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
		fridgeIt.setHumidity(humidity);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fridgeIt == null) ? 0 : fridgeIt.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HumidityApplication other = (HumidityApplication) obj;
		if (fridgeIt == null) {
			if (other.fridgeIt != null)
				return false;
		} else if (!fridgeIt.equals(other.fridgeIt))
			return false;
		return true;
	}
	
	

}
