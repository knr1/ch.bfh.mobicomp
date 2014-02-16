package sensor;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.BrickletAmbientLight.IlluminanceReachedListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import fridgeit.FridgeIt;

/**
 * This class represents a TinkerforgeApplication which is responsible for
 * telling the difference of 'dark' and 'bright' ambience.
 * 
 * 
 * It is written in a 'part of' style, meaning that it serves as a slave for the
 * {@link LightApplication}. It would be better to write it as a component which
 * allows to add a listener...
 * 
 * @author reto
 * 
 */
public class AmbientLightApplication extends AbstractTinkerforgeApplication
		implements IlluminanceReachedListener {
	private FridgeIt fridgeIt;
	private BrickletAmbientLight ambientLightBricklet;
	private boolean latestAnswerIsItDark;
	private int ambientHistereseMin = 7;
	private int ambientHistereseMax = 9;

	/**
	 * Creates a new instance of {@link AmbientLightApplication}
	 * 
	 * @param frdidgeIt
	 *            The Application to be informed onChange
	 */
	public AmbientLightApplication(FridgeIt frdidgeIt) {
		this.fridgeIt = frdidgeIt;
	}

	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.AmbientLight
				&& device.equals(ambientLightBricklet)) {
			((BrickletAmbientLight) device)
					.removeIlluminanceReachedListener(this);
			ambientLightBricklet = null;
		}
	}

	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.AmbientLight) {
			if (ambientLightBricklet != null)
				return;
			ambientLightBricklet = (BrickletAmbientLight) device;
			ambientLightBricklet.addIlluminanceReachedListener(this);
			setAmbientHisteres();
		}
	}

	/**
	 * @return The histerese threshold max for 'dark'.
	 */
	public int getAmbientHistereseMax() {
		return ambientHistereseMax / 10;
	}

	/**
	 * 
	 * @return The histerese threshold min for 'dark'.
	 */
	public int getAmbientHistereseMin() {
		return ambientHistereseMin / 10;
	}

	/**
	 * Sets the maximum amount of Lux which is STILL 'dark'. In order to work
	 * properly, this value must be slightly higher (some Lux) than the
	 * according minimum. This creates a histerese which avoids 'flickering'.
	 * 
	 * @param ambientHistereseMaxInLux
	 */
	public void setAmbientHistereseMax(int ambientHistereseMaxInLux) {
		this.ambientHistereseMax = ambientHistereseMaxInLux * 10;
		setAmbientHisteres();
	}

	/**
	 * Sets the minimum amount of Lux which is STILL 'bright'. In order to work
	 * properly, this value must be slightly lower (some Lux) than the according
	 * maximum. This creates a histerese which avoids 'flickering'.
	 * 
	 * @param ambientHistereseMaxInLux
	 */
	public void setAmbientHistereseMin(int ambientHistereseMinInLux) {
		this.ambientHistereseMin = ambientHistereseMinInLux * 10;
		setAmbientHisteres();
	}

	private void setAmbientHisteres() {
		if (ambientLightBricklet == null)
			return;
		try {
			ambientLightBricklet.setIlluminanceCallbackThreshold(
					BrickletAmbientLight.THRESHOLD_OPTION_OUTSIDE,
					(short) (ambientHistereseMin),
					(short) (ambientHistereseMax));
			ambientLightBricklet.setDebouncePeriod(1000 * 10);

			this.fridgeIt.setAmbientDarkState(ambientLightBricklet
					.getIlluminance() < this.ambientHistereseMin);

		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void illuminanceReached(int illuminance) {
		if (latestAnswerIsItDark == illuminance < ambientHistereseMax)
			return;
		this.latestAnswerIsItDark = !this.latestAnswerIsItDark;
		fridgeIt.setAmbientDarkState(latestAnswerIsItDark);
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
		AmbientLightApplication other = (AmbientLightApplication) obj;
		if (fridgeIt == null) {
			if (other.fridgeIt != null)
				return false;
		} else if (!fridgeIt.equals(other.fridgeIt))
			return false;
		return true;
	}

}
