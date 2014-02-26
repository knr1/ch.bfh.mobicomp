package ch.bfh.fbi.mobiComp.tinkerforge.test;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletDualButton;
import com.tinkerforge.BrickletDualButton.StateChangedListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 * This class manages the {@link BrickletDualButton} in a way, that it
 * represents two independent toggle lights / buttons. <br/>
 * It will manage the first {@link BrickletDualButton} that will be presented to
 * this {@link TinkerforgeApplication}.
 * 
 * @author reto
 * 
 */
public class DualButtonApplication extends AbstractTinkerforgeApplication
		implements StateChangedListener {
	private BrickletDualButton dualButton;
	private final LightApplication lightApplication;

	public DualButtonApplication(final LightApplication lightApplication) {
		this.lightApplication = lightApplication;
	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if ((TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DualButton)
				&& device.equals(this.dualButton)) {
			((BrickletDualButton) device).removeStateChangedListener(this);
			this.dualButton = null;
		}

	}

	@Override
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DualButton) {
			if (this.dualButton != null) {
				return;
			}
			this.dualButton = (BrickletDualButton) device;
			this.dualButton.addStateChangedListener(this);

			this.setRightLEDState(this.lightApplication.getRightState());
			this.setLeftLEDState(this.lightApplication.getLeftState());

		}

	}

	public void setLeftLEDState(final boolean ledState) {
		final short led_request = ledState ? BrickletDualButton.LED_STATE_ON
				: BrickletDualButton.LED_STATE_OFF;
		try {
			this.dualButton.setSelectedLEDState(BrickletDualButton.LED_LEFT,
					led_request);
		} catch (final TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setRightLEDState(final boolean ledState) {
		final short led_request = ledState ? BrickletDualButton.LED_STATE_ON
				: BrickletDualButton.LED_STATE_OFF;
		try {
			this.dualButton.setSelectedLEDState(BrickletDualButton.LED_RIGHT,
					led_request);
		} catch (final TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void stateChanged(final short buttonL, final short buttonR,
			final short ledL, final short ledR) {

		if (buttonL == BrickletDualButton.BUTTON_STATE_PRESSED) {

			this.lightApplication.setLeftState(!this.lightApplication
					.getLeftState());
		}
		if (buttonR == BrickletDualButton.BUTTON_STATE_PRESSED) {
			this.lightApplication.setRightState(!this.lightApplication
					.getRightState());

		}
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
		final DualButtonApplication other = (DualButtonApplication) obj;
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
