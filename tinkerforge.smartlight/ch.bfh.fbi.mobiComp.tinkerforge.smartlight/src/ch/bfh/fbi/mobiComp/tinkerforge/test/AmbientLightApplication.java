package ch.bfh.fbi.mobiComp.tinkerforge.test;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.BrickletAmbientLight.IlluminanceReachedListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 * This class represents a TinkerforgeApplication which is responsible for
 * telling the difference of 'dark' and 'bright' ambient. <br/>
 * It will manage the first {@link BrickletAmbientLight} that will be presented
 * to this {@link TinkerforgeApplication}. <br/>
 * It is written in a 'part of' style, meaning that it serves as a slave for the
 * {@link LightApplication}. It would be better to write it as a component which
 * allows to add a listener...
 * 
 * @author reto
 * 
 */
public class AmbientLightApplication extends AbstractTinkerforgeApplication
		implements IlluminanceReachedListener {
	private final LightApplication lightApplication;
	private BrickletAmbientLight ambientLightBricklet;
	private boolean latestAnswerIsItDark;
	private int ambientHistereseMin = 400;
	private int ambientHistereseMax = 500;

	/**
	 * Creates a new instance of {@link AmbientLightApplication}
	 * 
	 * @param lightApplication
	 *            The Application to be informed onChange
	 */
	public AmbientLightApplication(final LightApplication lightApplication) {
		this.lightApplication = lightApplication;
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
	 * 
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
	 * Sets the maximum amount of Lux which is still 'dark'. In order to work
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
	 * Sets the minimum amount of Lux which will initiate the change to 'dark'.
	 * In order to work properly, this value must be slightly lower (some Lux)
	 * than the according maximum. This creates a histerese which avoids
	 * 'flickering'.
	 * 
	 * @param ambientHistereseMaxInLux
	 */
	public void setAmbientHistereseMin(final int ambientHistereseMinInLux) {
		this.ambientHistereseMin = ambientHistereseMinInLux * 10;
		this.setAmbientHisteres();
	}

	private void setAmbientHisteres() {
		try {
			this.ambientLightBricklet.setIlluminanceCallbackThreshold(
					BrickletAmbientLight.THRESHOLD_OPTION_OUTSIDE,
					(short) (this.ambientHistereseMin),
					(short) (this.ambientHistereseMax));
			this.ambientLightBricklet.setDebouncePeriod(1000 * 10);
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
		this.lightApplication.setAmbientDarkState(this.latestAnswerIsItDark);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((this.lightApplication == null) ? 0 : this.lightApplication
						.hashCode());
		return result;
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
		if (this.lightApplication == null) {
			if (other.lightApplication != null) {
				return false;
			}
		} else if (!this.lightApplication.equals(other.lightApplication)) {
			return false;
		}
		return true;
	}

}
