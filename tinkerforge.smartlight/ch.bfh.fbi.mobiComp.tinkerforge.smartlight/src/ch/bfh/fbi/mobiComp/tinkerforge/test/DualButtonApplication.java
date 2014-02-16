package ch.bfh.fbi.mobiComp.tinkerforge.test;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletDualButton;
import com.tinkerforge.BrickletDualButton.StateChangedListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

public class DualButtonApplication extends AbstractTinkerforgeApplication
		implements StateChangedListener {
	private BrickletDualButton dualButton;
	private LightApplication lightApplication;

	public DualButtonApplication(LightApplication lightApplication) {
		this.lightApplication = lightApplication;
	}

	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DualButton && device.equals(dualButton)) {
			((BrickletDualButton) device).removeStateChangedListener(this);
			dualButton = null;
		}

	}

	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DualButton) {
			if (this.dualButton != null) // It is programmed for exactly one DualButton
				return;
			dualButton = (BrickletDualButton) device;
			dualButton.addStateChangedListener(this);

			this.setRightLEDState(lightApplication.getRightState());
			this.setLeftLEDState(lightApplication.getLeftState());

		}

	}

	public void setLeftLEDState(boolean ledState) {
		short led_request = ledState ? BrickletDualButton.LED_STATE_ON
				: BrickletDualButton.LED_STATE_OFF;
		try {
			dualButton.setSelectedLEDState(BrickletDualButton.LED_LEFT,
					led_request);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setRightLEDState(boolean ledState) {
		short led_request = ledState ? BrickletDualButton.LED_STATE_ON
				: BrickletDualButton.LED_STATE_OFF;
		try {
			dualButton.setSelectedLEDState(BrickletDualButton.LED_RIGHT,
					led_request);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void stateChanged(short buttonL, short buttonR, short ledL,
			short ledR) {

		if (buttonL == BrickletDualButton.BUTTON_STATE_PRESSED) {

			lightApplication.setLeftState(!lightApplication.getLeftState());
		}
		if (buttonR == BrickletDualButton.BUTTON_STATE_PRESSED) {
			lightApplication.setRightState(!lightApplication.getRightState());

		}
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
		DualButtonApplication other = (DualButtonApplication) obj;
		if (lightApplication == null) {
			if (other.lightApplication != null)
				return false;
		} else if (!lightApplication.equals(other.lightApplication))
			return false;
		return true;
	}

}
