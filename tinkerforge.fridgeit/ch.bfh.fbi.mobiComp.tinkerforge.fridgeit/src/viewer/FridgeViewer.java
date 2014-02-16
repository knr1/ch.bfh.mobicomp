package viewer;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import ch.quantasy.tinkerforge.tinker.util.implementation.LCD20x4Manager;

import com.tinkerforge.BrickletDualRelay;
import com.tinkerforge.BrickletLCD20x4;
import com.tinkerforge.BrickletTemperatureIR;
import com.tinkerforge.Device;
import com.tinkerforge.TinkerforgeException;

/**
 * This class allows to present the sensor-information for FridgeIt. <br/>
 * It is ready to use the {@link BrickletLCD20x4} for visual presentation<br/>
 * It is ready to use the {@link BrickletDualRelay} for audio presentation<br/>
 * The {@link BrickletDualRelay} is abused as it clicks so nicely.
 * 
 * @author reto
 * 
 */
public class FridgeViewer extends AbstractTinkerforgeApplication {
	private static final short DOOR_LINE = 0;
	private static final short LIGHT_LINE = 0;
	private static final short TEMPERATURE_LINE = 0;
	private static final short AMBIENT_TEMP_LINE = 1;
	private static final short OBJECT_TEMP_LINE = 2;
	private static final short HUMIDITY_LINE = 3;
	private BrickletLCD20x4 lcd;
	private BrickletDualRelay relay;

	// Used for the 'special' alert
	private boolean isLightLit;
	private boolean isDoorClosed;

	@Override
	public void stackAgentIsDisconnecting(
			final TinkerforgeStackAgent tinkerforgeStackAgent) {
		// We do not care
	}

	/**
	 * Before really disconnecting, the viewer will say good-bye to the human. <br/>
	 * It will do a click (if the {@link BrickletDualRelay} is connected <br/>
	 * It will call the {@link LCD20x4Manager#goodBye(BrickletLCD20x4)}
	 */
	@Override
	public void deviceIsDisconnecting(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.areEqual(this.lcd, device)) {
			this.clickyAlert(200, 1);
			LCD20x4Manager.goodBye(this.lcd);
		}

	}

	/**
	 * Removes the instances to the 'output'-devices (This is somewhat
	 * dangerous, but for the sake of the example... it's ok)
	 */
	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {

		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.LCD20x4) {
			this.lcd = null;
		}
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DualRelay) {
			this.relay = null;
		}
	}

	/**
	 * Sets the instances to the 'output'-devices. It only expects two
	 * {@link Device}s <br/>
	 * {@link BrickletLCD20x4}<br/>
	 * {@link BrickletDualRelay}<br/>
	 * 
	 * @param device
	 */
	@Override
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.LCD20x4) {
			this.lcd = (BrickletLCD20x4) device;
			this.initLCD();
		}
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DualRelay) {
			this.relay = (BrickletDualRelay) device;
			this.initRelay();
		}
	}

	/**
	 * Allows to indicate if the door is open. It checks, if a 'special'
	 * {@link #alert()} has to be executed... It writes the status to the
	 * {@link BrickletLCD20x4}-instance if it is available
	 * 
	 * @param isDoorOpen
	 */
	public void setDoorStatus(final boolean isDoorOpen) {
		this.isDoorClosed = !isDoorOpen;
		this.alert();
		if (this.lcd != null) {

			synchronized (this.lcd) {
				final StringBuilder sb = new StringBuilder("____");
				if (isDoorOpen) {
					sb.replace(0, sb.length(), "Door");
				}
				try {
					LCD20x4Manager.write(this.lcd, FridgeViewer.DOOR_LINE,
							(short) 10, sb.toString());

				} catch (final TinkerforgeException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * Allows to indicate if the light is on. It checks, if a 'special'
	 * {@link #alert()} has to be executed... It writes the status to the
	 * {@link BrickletLCD20x4}-instance if it is available
	 * 
	 * @param isLightLit
	 */
	public void setLightStatus(final boolean isLightLit) {
		this.isLightLit = isLightLit;
		this.alert();
		if (this.lcd == null) {
			return;
		}
		synchronized (this.lcd) {
			final StringBuilder sb = new StringBuilder("_____");
			if (isLightLit) {
				sb.replace(0, sb.length(), "Light");
			}
			try {
				LCD20x4Manager.write(this.lcd, FridgeViewer.LIGHT_LINE,
						(short) 15, sb.toString());
				LCD20x4Manager.visualAlert(this.lcd, 50, 50, 10);

			} catch (final TinkerforgeException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Allows to indicate the object-temperature (The
	 * {@link BrickletTemperatureIR} is facing It writes the value to the
	 * {@link BrickletLCD20x4}-instance if it is available
	 * 
	 * @param temperature
	 */
	public void setObjectTemp(final int temperature) {
		final String temperatureString = "" + (temperature / 10.0) + "°C";
		this.writeValueToLCD(temperatureString, FridgeViewer.OBJECT_TEMP_LINE);
	}

	/**
	 * Allows to indicate the ambient-temperature (The {@link FridgeSensor} is
	 * in It writes the value to the {@link BrickletLCD20x4}-instance if it is
	 * available
	 * 
	 * @param temperature
	 */
	public void setAmbientTemp(final int temperature) {
		final String temperatureString = "" + (temperature / 10.0) + "°C";
		this.writeValueToLCD(temperatureString, FridgeViewer.AMBIENT_TEMP_LINE);
	}

	/**
	 * /** Allows to indicate the ambient-humidity (The {@link FridgeSensor} is
	 * in It writes the value to the {@link BrickletLCD20x4}-instance if it is
	 * available
	 * 
	 * @param humidity
	 */
	public void setHumidity(final double humidityInPercent) {
		if (this.lcd == null) {
			return;
		}
		synchronized (this.lcd) {
			final String humidityString = "" + humidityInPercent + " %";
			this.writeValueToLCD(humidityString, FridgeViewer.HUMIDITY_LINE);
		}
	}

	/**
	 * This method only fires the alert if the door of the fridge is shut, but
	 * the light is on. This method is the true reason for this example. I want
	 * to know! ;-)
	 */
	public void alert() {
		if (this.isDoorClosed && this.isLightLit) {
			this.clickyAlert(50, 10);
		}
	}

	private void initRelay() {
		if (this.relay == null) {
			return;
		}
		this.clickyAlert(100, 5);
	}

	private void initLCD() {
		if (this.lcd == null) {
			return;
		}
		synchronized (this.lcd) {
			try {
				LCD20x4Manager.hello(this.lcd);
				this.lcd.clearDisplay();
				this.lcd.writeLine(FridgeViewer.TEMPERATURE_LINE, (short) 0,
						"Temp");
				this.lcd.writeLine(FridgeViewer.AMBIENT_TEMP_LINE, (short) 0,
						" Ambient:");
				this.lcd.writeLine(FridgeViewer.OBJECT_TEMP_LINE, (short) 0,
						" Object :");
				this.lcd.writeLine(FridgeViewer.HUMIDITY_LINE, (short) 0,
						"Humidity:");
				this.lcd.backlightOn();
			} catch (final TinkerforgeException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Helps to write the values for temperature and humidity. (to Right)
	 * 
	 * @param text
	 * @param line
	 */
	private void writeValueToLCD(final String text, final short line) {
		if (this.lcd == null) {
			return;
		}
		synchronized (this.lcd) {
			final StringBuilder sb = new StringBuilder("           ");
			final int position = sb.length() - text.length();
			sb.replace(position, sb.length(), text);
			try {

				LCD20x4Manager.write(this.lcd, line, (short) 9, sb.toString());

			} catch (final TinkerforgeException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void clickyAlert(final int clickTime, final int amount) {
		if (this.relay == null) {
			return;
		}
		new Thread() {
			@Override
			public void run() {
				try {
					final com.tinkerforge.BrickletDualRelay.State initialStatus = FridgeViewer.this.relay
							.getState();
					for (int i = 0; i < (amount * 2); i++) {

						FridgeViewer.this.relay.setState(
								!FridgeViewer.this.relay.getState().relay1,
								!FridgeViewer.this.relay.getState().relay2);
						Thread.sleep(clickTime);

					}
					FridgeViewer.this.relay.setState(initialStatus.relay1,
							initialStatus.relay2);
				} catch (final Exception ex) {
				}
			}
		}.start();
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj == this;
	}
}
