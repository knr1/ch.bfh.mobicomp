package sensor;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.BrickletDistanceIR.DistanceReachedListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import fridgeit.FridgeIt;

/**
 * This class represents a TinkerforgeApplication which is responsible for
 * telling the difference of 'near' and 'far' away.
 * 
 * 
 * It is written in a 'part of' style, meaning that it serves as a slave for the
 * {@link LightApplication}. It would be better to write it as a component which
 * allows to add a listener...
 * 
 * @author reto
 * 
 */
public class DistanceApplication extends AbstractTinkerforgeApplication
		implements DistanceReachedListener {
	private final FridgeIt fridgeIt;
	private BrickletDistanceIR distanceIRBricklet;
	private boolean latestAnswerIsItClosed;
	private int distanceHistereseMin = 7;
	private int distanceHistereseMax = 9;

	/**
	 * Creates a new instance of {@link DistanceApplication}
	 * 
	 * @param frdidgeIt
	 *            The Application to be informed onChange
	 */
	public DistanceApplication(final FridgeIt frdidgeIt) {
		this.fridgeIt = frdidgeIt;
	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if ((TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DistanceIR)
				&& device.equals(this.distanceIRBricklet)) {
			((BrickletDistanceIR) device).removeDistanceReachedListener(this);
			this.distanceIRBricklet = null;
		}
	}

	@Override
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DistanceIR) {
			if (this.distanceIRBricklet != null) {
				return;
			}
			this.distanceIRBricklet = (BrickletDistanceIR) device;
			this.distanceIRBricklet.addDistanceReachedListener(this);
			this.setDistanceHisteres();
		}
	}

	/**
	 * @return The histerese threshold max for 'near'.
	 */
	public int getDistanceHistereseMax() {
		return this.distanceHistereseMax / 10;
	}

	/**
	 * 
	 * @return The histerese threshold min for 'near'.
	 */
	public int getDistanceHistereseMin() {
		return this.distanceHistereseMin / 10;
	}

	/**
	 * Sets the maximum distance in mm which is STILL 'near'. In order to work
	 * properly, this value must be slightly higher (some mm) than the according
	 * minimum. This creates a histerese which avoids 'flickering'.
	 * 
	 * @param distanceHistereseMaxInLux
	 */
	public void setDistanceHistereseMax(final int distanceHistereseMaxInLux) {
		this.distanceHistereseMax = distanceHistereseMaxInLux * 10;
		this.setDistanceHisteres();
	}

	/**
	 * Sets the minimum amount of mm which is STILL 'far'. In order to work
	 * properly, this value must be slightly lower (some mm) than the according
	 * maximum. This creates a histerese which avoids 'flickering'.
	 * 
	 * @param ambientHistereseMaxInLux
	 */
	public void setDistanceHistereseMin(final int distanceHistereseMinInLux) {
		this.distanceHistereseMin = distanceHistereseMinInLux * 10;
		this.setDistanceHisteres();
	}

	private void setDistanceHisteres() {
		if (this.distanceIRBricklet == null) {
			return;
		}

		try {
			this.distanceIRBricklet.setDistanceCallbackThreshold(
					BrickletDistanceIR.THRESHOLD_OPTION_OUTSIDE,
					(short) (this.distanceHistereseMin),
					(short) (this.distanceHistereseMax));
			this.distanceIRBricklet.setDebouncePeriod(200);
			this.fridgeIt.setDoorShutState(this.distanceIRBricklet
					.getDistance() < this.distanceHistereseMin);

		} catch (final TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void distanceReached(final int distance) {
		if (this.latestAnswerIsItClosed == (distance < this.distanceHistereseMax)) {
			return;
		}
		this.latestAnswerIsItClosed = !this.latestAnswerIsItClosed;
		this.fridgeIt.setDoorShutState(this.latestAnswerIsItClosed);
	}

	@Override
	public int hashCode() {
		if(distanceIRBricklet!=null)
			try {
				return distanceIRBricklet.getIdentity().hashCode();
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
		final DistanceApplication other = (DistanceApplication) obj;
		return this.fridgeIt==other.fridgeIt;
	}

}
