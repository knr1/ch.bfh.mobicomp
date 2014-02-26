package sensor;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
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
public class TemperatureApplication extends AbstractTinkerforgeApplication
		implements ObjectTemperatureListener, AmbientTemperatureListener {
	private final FridgeIt fridgeIt;

	public TemperatureApplication(final FridgeIt fridgeIt) {
		this.fridgeIt = fridgeIt;
	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
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
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
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
		this.fridgeIt.setObjectIRTemperature(temperature);
	}

	/**
	 * It sends all data to the {@link FridgeIt}
	 */
	@Override
	public void ambientTemperature(final short temperature) {
		this.fridgeIt.setAmbientTemperature(temperature);
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
		final TemperatureApplication other = (TemperatureApplication) obj;
		return this.fridgeIt==other.fridgeIt;
	}

}
