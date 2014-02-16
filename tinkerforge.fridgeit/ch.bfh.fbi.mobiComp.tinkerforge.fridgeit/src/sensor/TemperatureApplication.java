package sensor;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletTemperatureIR;
import com.tinkerforge.BrickletTemperatureIR.AmbientTemperatureListener;
import com.tinkerforge.BrickletTemperatureIR.ObjectTemperatureListener;
import com.tinkerforge.Device;
import com.tinkerforge.TinkerforgeException;

import fridgeit.FridgeIt;

/**
 * This class is responsible for receiving, processing and delegating data about
 * the ambient-temperature and Object-IR-temperature
 * 
 * @author reto
 * 
 */
public class TemperatureApplication extends AbstractTinkerforgeApplication implements
		ObjectTemperatureListener, AmbientTemperatureListener {
	private FridgeIt fridgeIt;

	public TemperatureApplication(FridgeIt fridgeIt) {
		this.fridgeIt = fridgeIt;
	}

	
	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.TemperatureIR) {
			final BrickletTemperatureIR tempIR = (BrickletTemperatureIR) device;
			tempIR.removeObjectTemperatureListener(this);
			tempIR.removeAmbientTemperatureListener(this);

			try {
				tempIR.setObjectTemperatureCallbackPeriod(500);
				tempIR.setAmbientTemperatureCallbackPeriod(500);
			} catch (final TinkerforgeException ex) {
			}

		}

	}

	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.TemperatureIR) {
			final BrickletTemperatureIR tempIR = (BrickletTemperatureIR) device;
			tempIR.addObjectTemperatureListener(this);
			tempIR.addAmbientTemperatureListener(this);

			try {
				tempIR.setObjectTemperatureCallbackPeriod(500);
				tempIR.setAmbientTemperatureCallbackPeriod(500);
			} catch (final TinkerforgeException ex) {
			}

		}
	}

	/**
	 * It sends all data to the {@link FridgeIt}
	 */
	@Override
	public void objectTemperature(final short temperature) {
		fridgeIt.setObjectIRTemperature(temperature);
	}

	/**
	 * It sends all data to the {@link FridgeIt}
	 */
	@Override
	public void ambientTemperature(final short temperature) {
		fridgeIt.setAmbientTemperature(temperature);
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
		TemperatureApplication other = (TemperatureApplication) obj;
		if (fridgeIt == null) {
			if (other.fridgeIt != null)
				return false;
		} else if (!fridgeIt.equals(other.fridgeIt))
			return false;
		return true;
	}

}
