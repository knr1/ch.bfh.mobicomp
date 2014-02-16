package sensor;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.BrickletAmbientLight.IlluminanceReachedListener;
import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.BrickletDistanceIR.DistanceReachedListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import fridgeit.FridgeIt;

/**
 * This class represents a TinkerforgeApplication which is responsible for telling
 * the difference of 'near' and 'far' away.
 * 
 * 
 * It is written in a 'part of' style, meaning that it serves as a slave
 * for the {@link LightApplication}. It would be better to write it as a
 * component which allows to add a listener...
 * 
 * @author reto
 *
 */
public class DistanceApplication extends AbstractTinkerforgeApplication
		implements DistanceReachedListener {
	private FridgeIt fridgeIt;
	private BrickletDistanceIR distanceIRBricklet;
	private boolean latestAnswerIsItClosed;
	private int distanceHistereseMin = 7;
	private int distanceHistereseMax = 9;

	/**
	 * Creates a new instance of {@link DistanceApplication}
	 * @param frdidgeIt The Application to be informed onChange
	 */
	public DistanceApplication(FridgeIt frdidgeIt) {
		this.fridgeIt = frdidgeIt;
	}

	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DistanceIR
				&& device.equals(distanceIRBricklet)) {
			((BrickletDistanceIR) device)
					.removeDistanceReachedListener(this);
			distanceIRBricklet=null;
		}
	}

	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DistanceIR) {
			if(distanceIRBricklet!=null)
				return;
			distanceIRBricklet = (BrickletDistanceIR) device;
			distanceIRBricklet.addDistanceReachedListener(this);
			setDistanceHisteres();
		}
	}

	/**
	 * @return The histerese threshold max for 'near'.
	 */
	public int getDistanceHistereseMax() {
		return distanceHistereseMax/10;
	}

	/**
	 * 
	 * @return The histerese threshold min for 'near'.
	 */
	public int getDistanceHistereseMin() {
		return distanceHistereseMin/10;
	}

	/**
	 * Sets the maximum distance in mm which is STILL 'near'.
	 * In order to work properly, this value must be slightly higher (some mm)
	 * than the according minimum. This creates a histerese which avoids 'flickering'.
	 * @param distanceHistereseMaxInLux
	 */
	public void setDistanceHistereseMax(int distanceHistereseMaxInLux) {
		this.distanceHistereseMax = distanceHistereseMaxInLux*10;
		setDistanceHisteres();
	}

	/**
	 * Sets the minimum amount of mm which is STILL 'far'.
	 * In order to work properly, this value must be slightly lower (some mm)
	 * than the according maximum. This creates a histerese which avoids 'flickering'.
	 * @param ambientHistereseMaxInLux
	 */
	public void setDistanceHistereseMin(int distanceHistereseMinInLux) {
		this.distanceHistereseMin = distanceHistereseMinInLux*10;
		setDistanceHisteres();
	}

	private void setDistanceHisteres() {
		if(distanceIRBricklet==null) return;
		
		try {
			distanceIRBricklet.setDistanceCallbackThreshold(
					BrickletDistanceIR.THRESHOLD_OPTION_OUTSIDE,
					(short)(distanceHistereseMin), (short)(distanceHistereseMax));
			distanceIRBricklet.setDebouncePeriod(200);
			this.fridgeIt.setDoorShutState(distanceIRBricklet.getDistance() < this.distanceHistereseMin);

		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
@Override
public void distanceReached(int distance) {
		if(latestAnswerIsItClosed==distance<distanceHistereseMax)
			return;
		this.latestAnswerIsItClosed=!this.latestAnswerIsItClosed;
		fridgeIt
				.setDoorShutState(latestAnswerIsItClosed);
	}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((fridgeIt == null) ? 0 : fridgeIt.hashCode());
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
	DistanceApplication other = (DistanceApplication) obj;
	if (fridgeIt == null) {
		if (other.fridgeIt != null)
			return false;
	} else if (!fridgeIt.equals(other.fridgeIt))
		return false;
	return true;
}

}
