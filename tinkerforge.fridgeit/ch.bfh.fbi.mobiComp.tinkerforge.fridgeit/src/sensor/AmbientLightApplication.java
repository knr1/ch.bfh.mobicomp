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
	private final FridgeIt fridgeIt;
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
	public AmbientLightApplication(final FridgeIt frdidgeIt) {
		this.fridgeIt = frdidgeIt;
	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if ((TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.AmbientLight)
				&& device.equals(this.ambientLightBricklet)) {
			((BrickletAmbientLight) device)
					.removeIlluminanceReachedListener(this);
			this.ambientLightBricklet = null;
		}
	}

	@Override
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.AmbientLight) {
			if (this.ambientLightBricklet != null) {
				return;
			}
			this.ambientLightBricklet = (BrickletAmbientLight) device;
			this.ambientLightBricklet.addIlluminanceReachedListener(this);
			this.setAmbientHisteres();
		}
	}

	/**
	 * @return The histerese threshold max for 'dark'.
	 */
	public int getAmbientHistereseMax() {
		return this.ambientHistereseMax / 10;
	}

	/**
	 * 
	 * @return The histerese threshold min for 'dark'.
	 */
	public int getAmbientHistereseMin() {
		return this.ambientHistereseMin / 10;
	}

	/**
	 * Sets the maximum amount of Lux which is STILL 'dark'. In order to work
	 * properly, this value must be slightly higher (some Lux) than the
	 * according minimum. This creates a histerese which avoids 'flickering'.
	 * 
	 * @param ambientHistereseMaxInLux
	 */
	public void setAmbientHistereseMax(final int ambientHistereseMaxInLux) {
		this.ambientHistereseMax = ambientHistereseMaxInLux * 10;
		this.setAmbientHisteres();
	}

	/**
	 * Sets the minimum amount of Lux which is STILL 'bright'. In order to work
	 * properly, this value must be slightly lower (some Lux) than the according
	 * maximum. This creates a histerese which avoids 'flickering'.
	 * 
	 * @param ambientHistereseMaxInLux
	 */
	public void setAmbientHistereseMin(final int ambientHistereseMinInLux) {
		this.ambientHistereseMin = ambientHistereseMinInLux * 10;
		this.setAmbientHisteres();
	}

	private void setAmbientHisteres() {
		if (this.ambientLightBricklet == null) {
			return;
		}
		try {
			this.ambientLightBricklet.setIlluminanceCallbackThreshold(
					BrickletAmbientLight.THRESHOLD_OPTION_OUTSIDE,
					(short) (this.ambientHistereseMin),
					(short) (this.ambientHistereseMax));
			this.ambientLightBricklet.setDebouncePeriod(1000 * 10);

			this.fridgeIt.setAmbientDarkState(this.ambientLightBricklet
					.getIlluminance() < this.ambientHistereseMin);

		} catch (final TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void illuminanceReached(final int illuminance) {
		if (this.latestAnswerIsItDark == (illuminance < this.ambientHistereseMax)) {
			return;
		}
		this.latestAnswerIsItDark = !this.latestAnswerIsItDark;
		this.fridgeIt.setAmbientDarkState(this.latestAnswerIsItDark);
	}

	@Override
	public int hashCode() {
		if(ambientLightBricklet!=null)
			try {
				return ambientLightBricklet.getIdentity().hashCode();
			} catch (Exception e) {
			}
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
		final AmbientLightApplication other = (AmbientLightApplication) obj;
		return this.fridgeIt==other.fridgeIt;
	}

}
