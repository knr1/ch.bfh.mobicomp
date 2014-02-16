package ch.bfh.fbi.mobiComp.tinkerforge.test;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.BrickletAmbientLight.IlluminanceReachedListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 * This class represents a TinkerforgeApplication which is responsible for telling
 * the difference of 'dark' and 'bright' ambience.
 * 
 * It is written in a 'part of' style, meaning that it serves as a slave
 * for the {@link LightApplication}. It would be better to write it as a
 * component which allows to add a listener...
 * 
 * @author reto
 *
 */
public class AmbientLightApplication extends AbstractTinkerforgeApplication
		implements IlluminanceReachedListener {
	private LightApplication lightApplication;
	private BrickletAmbientLight ambientLightBricklet;
	private boolean latestAnswerIsItDark;
	private int ambientHistereseMin = 400;
	private int ambientHistereseMax = 500;

	/**
	 * Creates a new instance of {@link AmbientLightApplication}
	 * @param lightApplication The Application to be informed onChange
	 */
	public AmbientLightApplication(LightApplication lightApplication) {
		this.lightApplication = lightApplication;
	}

	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.AmbientLight
				&& device.equals(ambientLightBricklet)) {
			((BrickletAmbientLight) device)
					.removeIlluminanceReachedListener(this);
			ambientLightBricklet=null;
		}
	}

	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.AmbientLight) {
			if(ambientLightBricklet!=null)
				return;
			ambientLightBricklet = (BrickletAmbientLight) device;
			ambientLightBricklet.addIlluminanceReachedListener(this);
			setAmbientHisteres();
		}
	}

	/**
	 * 
	 * @return The histerese threshold max for 'dark'.
	 */
	public int getAmbientHistereseMax() {
		return ambientHistereseMax/10;
	}

	/**
	 * 
	 * @return The histerese threshold min for 'dark'.
	 */
	public int getAmbientHistereseMin() {
		return ambientHistereseMin/10;
	}

	/**
	 * Sets the maximum amount of Lux which is still 'dark'.
	 * In order to work properly, this value must be slightly higher (some Lux)
	 * than the according minimum. This creates a histerese which avoids 'flickering'.
	 * @param ambientHistereseMaxInLux
	 */
	public void setAmbientHistereseMax(int ambientHistereseMaxInLux) {
		this.ambientHistereseMax = ambientHistereseMaxInLux*10;
		setAmbientHisteres();
	}

	/**
	 * Sets the minimum amount of Lux which will initiate the change to 'dark'.
	 * In order to work properly, this value must be slightly lower (some Lux)
	 * than the according maximum. This creates a histerese which avoids 'flickering'.
	 * @param ambientHistereseMaxInLux
	 */
	public void setAmbientHistereseMin(int ambientHistereseMinInLux) {
		this.ambientHistereseMin = ambientHistereseMinInLux*10;
		setAmbientHisteres();
	}

	private void setAmbientHisteres() {
		try {
			ambientLightBricklet.setIlluminanceCallbackThreshold(
					BrickletAmbientLight.THRESHOLD_OPTION_OUTSIDE,
					(short)(ambientHistereseMin), (short)(ambientHistereseMax));
			ambientLightBricklet.setDebouncePeriod(1000*10);
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
		if(latestAnswerIsItDark==illuminance<ambientHistereseMax)
			return;
		this.latestAnswerIsItDark=!this.latestAnswerIsItDark;
		lightApplication
				.setAmbientDarkState(latestAnswerIsItDark);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((lightApplication == null) ? 0 : lightApplication.hashCode());
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
		if (lightApplication == null) {
			if (other.lightApplication != null)
				return false;
		} else if (!lightApplication.equals(other.lightApplication))
			return false;
		return true;
	}

}
