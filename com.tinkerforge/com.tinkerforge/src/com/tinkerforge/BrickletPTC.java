/* ***********************************************************
 * This file was automatically generated on 2013-12-19.      *
 *                                                           *
 * Bindings Version 2.0.14                                    *
 *                                                           *
 * If you have a bugfix for this file and want to commit it, *
 * please fix the bug in the generator. You can find a link  *
 * to the generator git on tinkerforge.com                   *
 *************************************************************/

package com.tinkerforge;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Device for reading temperatures from Pt100 or Pt1000 sensors
 */
public class BrickletPTC extends Device {
	public final static int DEVICE_IDENTIFIER = 226;

	public final static byte FUNCTION_GET_TEMPERATURE = (byte)1;
	public final static byte FUNCTION_GET_RESISTANCE = (byte)2;
	public final static byte FUNCTION_SET_TEMPERATURE_CALLBACK_PERIOD = (byte)3;
	public final static byte FUNCTION_GET_TEMPERATURE_CALLBACK_PERIOD = (byte)4;
	public final static byte FUNCTION_SET_RESISTANCE_CALLBACK_PERIOD = (byte)5;
	public final static byte FUNCTION_GET_RESISTANCE_CALLBACK_PERIOD = (byte)6;
	public final static byte FUNCTION_SET_TEMPERATURE_CALLBACK_THRESHOLD = (byte)7;
	public final static byte FUNCTION_GET_TEMPERATURE_CALLBACK_THRESHOLD = (byte)8;
	public final static byte FUNCTION_SET_RESISTANCE_CALLBACK_THRESHOLD = (byte)9;
	public final static byte FUNCTION_GET_RESISTANCE_CALLBACK_THRESHOLD = (byte)10;
	public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)11;
	public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)12;
	public final static byte CALLBACK_TEMPERATURE = (byte)13;
	public final static byte CALLBACK_TEMPERATURE_REACHED = (byte)14;
	public final static byte CALLBACK_RESISTANCE = (byte)15;
	public final static byte CALLBACK_RESISTANCE_REACHED = (byte)16;
	public final static byte FUNCTION_SET_NOISE_REJECTION_FILTER = (byte)17;
	public final static byte FUNCTION_GET_NOISE_REJECTION_FILTER = (byte)18;
	public final static byte FUNCTION_IS_SENSOR_CONNECTED = (byte)19;
	public final static byte FUNCTION_SET_WIRE_MODE = (byte)20;
	public final static byte FUNCTION_GET_WIRE_MODE = (byte)21;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static char THRESHOLD_OPTION_OFF = 'x';
	public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
	public final static char THRESHOLD_OPTION_INSIDE = 'i';
	public final static char THRESHOLD_OPTION_SMALLER = '<';
	public final static char THRESHOLD_OPTION_GREATER = '>';
	public final static short FILTER_OPTION_50HZ = (short)0;
	public final static short FILTER_OPTION_60HZ = (short)1;
	public final static short WIRE_MODE_2 = (short)2;
	public final static short WIRE_MODE_3 = (short)3;
	public final static short WIRE_MODE_4 = (short)4;

	private List<TemperatureListener> listenerTemperature = new CopyOnWriteArrayList<TemperatureListener>();
	private List<TemperatureReachedListener> listenerTemperatureReached = new CopyOnWriteArrayList<TemperatureReachedListener>();
	private List<ResistanceListener> listenerResistance = new CopyOnWriteArrayList<ResistanceListener>();
	private List<ResistanceReachedListener> listenerResistanceReached = new CopyOnWriteArrayList<ResistanceReachedListener>();

	public class TemperatureCallbackThreshold {
		public char option;
		public int min;
		public int max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	public class ResistanceCallbackThreshold {
		public char option;
		public int min;
		public int max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletPTC#setTemperatureCallbackPeriod(long)}. The parameter is the temperature
	 * of the connected sensor.
	 * 
	 * {@link BrickletPTC.TemperatureListener} is only triggered if the temperature has changed since the
	 * last triggering.
	 */
	public interface TemperatureListener {
		public void temperature(int temperature);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletPTC#setTemperatureCallbackThreshold(char, int, int)} is reached.
	 * The parameter is the temperature of the connected sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletPTC#setDebouncePeriod(long)}.
	 */
	public interface TemperatureReachedListener {
		public void temperatureReached(int temperature);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletPTC#setResistanceCallbackPeriod(long)}. The parameter is the resistance
	 * of the connected sensor.
	 * 
	 * {@link BrickletPTC.ResistanceListener} is only triggered if the resistance has changed since the
	 * last triggering.
	 */
	public interface ResistanceListener {
		public void resistance(int resistance);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletPTC#setResistanceCallbackThreshold(char, int, int)} is reached.
	 * The parameter is the resistance of the connected sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletPTC#setDebouncePeriod(long)}.
	 */
	public interface ResistanceReachedListener {
		public void resistanceReached(int resistance);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletPTC(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_TEMPERATURE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_RESISTANCE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_TEMPERATURE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_TEMPERATURE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_RESISTANCE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_RESISTANCE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_TEMPERATURE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_TEMPERATURE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_RESISTANCE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_RESISTANCE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_NOISE_REJECTION_FILTER)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_NOISE_REJECTION_FILTER)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_IS_SENSOR_CONNECTED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_WIRE_MODE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_WIRE_MODE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_TEMPERATURE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_TEMPERATURE_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_RESISTANCE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_RESISTANCE_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_TEMPERATURE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int temperature = (bb.getInt());

				for(TemperatureListener listener: listenerTemperature) {
					listener.temperature(temperature);
				}
			}
		};

		callbacks[CALLBACK_TEMPERATURE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int temperature = (bb.getInt());

				for(TemperatureReachedListener listener: listenerTemperatureReached) {
					listener.temperatureReached(temperature);
				}
			}
		};

		callbacks[CALLBACK_RESISTANCE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int resistance = IPConnection.unsignedShort(bb.getShort());

				for(ResistanceListener listener: listenerResistance) {
					listener.resistance(resistance);
				}
			}
		};

		callbacks[CALLBACK_RESISTANCE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int resistance = IPConnection.unsignedShort(bb.getShort());

				for(ResistanceReachedListener listener: listenerResistanceReached) {
					listener.resistanceReached(resistance);
				}
			}
		};
	}

	/**
	 * Returns the temperature of connected sensor. The value
	 * has a range of -246 to 849 °C and is given in °C/100,
	 * e.g. a value of 4223 means that a temperature of 42.23 °C is measured.
	 * 
	 * If you want to get the temperature periodically, it is recommended 
	 * to use the listener {@link BrickletPTC.TemperatureListener} and set the period with 
	 * {@link BrickletPTC#setTemperatureCallbackPeriod(long)}.
	 */
	public int getTemperature() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_TEMPERATURE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int temperature = (bb.getInt());

		return temperature;
	}

	/**
	 * Returns the value as measured by the MAX31865 precision delta-sigma ADC.
	 * 
	 * The value can be converted with the following formulas:
	 * 
	 * * Pt100:  resistance = (value * 390) / 32768
	 * * Pt1000: resistance = (value * 3900) / 32768
	 * 
	 * If you want to get the resistance periodically, it is recommended 
	 * to use the listener {@link BrickletPTC.ResistanceListener} and set the period with 
	 * {@link BrickletPTC#setResistanceCallbackPeriod(long)}.
	 */
	public int getResistance() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_RESISTANCE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int resistance = IPConnection.unsignedShort(bb.getShort());

		return resistance;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletPTC.TemperatureListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletPTC.TemperatureListener} is only triggered if the temperature has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setTemperatureCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_TEMPERATURE_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletPTC#setTemperatureCallbackPeriod(long)}.
	 */
	public long getTemperatureCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_TEMPERATURE_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletPTC.ResistanceListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletPTC.ResistanceListener} is only triggered if the resistance has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setResistanceCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_RESISTANCE_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletPTC#setResistanceCallbackPeriod(long)}.
	 */
	public long getResistanceCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_RESISTANCE_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the thresholds for the {@link BrickletPTC.TemperatureReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the temperature is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the temperature is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the temperature is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the temperature is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setTemperatureCallbackThreshold(char option, int min, int max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)17, FUNCTION_SET_TEMPERATURE_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putInt(min);
		bb.putInt(max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletPTC#setTemperatureCallbackThreshold(char, int, int)}.
	 */
	public TemperatureCallbackThreshold getTemperatureCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_TEMPERATURE_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		TemperatureCallbackThreshold obj = new TemperatureCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getInt());
		obj.max = (bb.getInt());

		return obj;
	}

	/**
	 * Sets the thresholds for the {@link BrickletPTC.ResistanceReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the temperature is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the temperature is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the temperature is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the temperature is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setResistanceCallbackThreshold(char option, int min, int max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)13, FUNCTION_SET_RESISTANCE_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putShort((short)min);
		bb.putShort((short)max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletPTC#setResistanceCallbackThreshold(char, int, int)}.
	 */
	public ResistanceCallbackThreshold getResistanceCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_RESISTANCE_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		ResistanceCallbackThreshold obj = new ResistanceCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = IPConnection.unsignedShort(bb.getShort());
		obj.max = IPConnection.unsignedShort(bb.getShort());

		return obj;
	}

	/**
	 * Sets the period in ms with which the threshold listener
	 * 
	 * * {@link BrickletPTC.TemperatureReachedListener},
	 * * {@link BrickletPTC.ResistanceReachedListener}
	 * 
	 * is triggered, if the threshold
	 * 
	 * * {@link BrickletPTC#setTemperatureCallbackThreshold(char, int, int)},
	 * * {@link BrickletPTC#setResistanceCallbackThreshold(char, int, int)}
	 * 
	 * keeps being reached.
	 * 
	 * The default value is 100.
	 */
	public void setDebouncePeriod(long debounce) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_DEBOUNCE_PERIOD, this);
		bb.putInt((int)debounce);

		sendRequest(bb.array());
	}

	/**
	 * Returns the debounce period as set by {@link BrickletPTC#setDebouncePeriod(long)}.
	 */
	public long getDebouncePeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_DEBOUNCE_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long debounce = IPConnection.unsignedInt(bb.getInt());

		return debounce;
	}

	/**
	 * Sets the noise rejection filter to either 50Hz (0) or 60Hz (1).
	 * Noise from 50Hz or 60Hz power sources (including
	 * harmonics of the AC power's fundamental frequency) is
	 * attenuated by 82dB.
	 * 
	 * Default value is 0 = 50Hz.
	 */
	public void setNoiseRejectionFilter(short filter) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_SET_NOISE_REJECTION_FILTER, this);
		bb.put((byte)filter);

		sendRequest(bb.array());
	}

	/**
	 * Returns the noise rejection filter option as set by 
	 * {@link BrickletPTC#setNoiseRejectionFilter(short)}
	 */
	public short getNoiseRejectionFilter() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_NOISE_REJECTION_FILTER, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short filter = IPConnection.unsignedByte(bb.get());

		return filter;
	}

	/**
	 * Returns *true* if the sensor is connected correctly. 
	 * 
	 * If this function
	 * returns *false*, there is either no Pt100 or Pt1000 sensor connected,
	 * the sensor is connected incorrectly or the sensor itself is faulty.
	 */
	public boolean isSensorConnected() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_IS_SENSOR_CONNECTED, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean connected = (bb.get()) != 0;

		return connected;
	}

	/**
	 * Sets the wire mode of the sensor. Possible values are 2, 3 and 4 which
	 * correspond to 2-, 3- and 4-wire sensors. The value has to match the jumper
	 * configuration on the Bricklet.
	 * 
	 * The default value is 2 = 2-wire.
	 */
	public void setWireMode(short mode) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_SET_WIRE_MODE, this);
		bb.put((byte)mode);

		sendRequest(bb.array());
	}

	/**
	 * Returns the wire mode as set by {@link BrickletPTC#setWireMode(short)}
	 */
	public short getWireMode() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_WIRE_MODE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short mode = IPConnection.unsignedByte(bb.get());

		return mode;
	}

	/**
	 * Returns the UID, the UID where the Bricklet is connected to, 
	 * the position, the hardware and firmware version as well as the
	 * device identifier.
	 * 
	 * The position can be 'a', 'b', 'c' or 'd'.
	 * 
	 * The device identifiers can be found :ref:`here <device_identifier>`.
	 * 
	 * .. versionadded:: 2.0.0~(Plugin)
	 */
	public Identity getIdentity() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_IDENTITY, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Identity obj = new Identity();
		obj.uid = IPConnection.string(bb, 8);
		obj.connectedUid = IPConnection.string(bb, 8);
		obj.position = (char)(bb.get());
		for(int i = 0; i < 3; i++) {
			obj.hardwareVersion[i] = IPConnection.unsignedByte(bb.get());
		}

		for(int i = 0; i < 3; i++) {
			obj.firmwareVersion[i] = IPConnection.unsignedByte(bb.get());
		}

		obj.deviceIdentifier = IPConnection.unsignedShort(bb.getShort());

		return obj;
	}

	/**
	 * Adds a Temperature listener.
	 */
	public void addTemperatureListener(TemperatureListener listener) {
		listenerTemperature.add(listener);
	}

	/**
	 * Removes a Temperature listener.
	 */
	public void removeTemperatureListener(TemperatureListener listener) {
		listenerTemperature.remove(listener);
	}

	/**
	 * Adds a TemperatureReached listener.
	 */
	public void addTemperatureReachedListener(TemperatureReachedListener listener) {
		listenerTemperatureReached.add(listener);
	}

	/**
	 * Removes a TemperatureReached listener.
	 */
	public void removeTemperatureReachedListener(TemperatureReachedListener listener) {
		listenerTemperatureReached.remove(listener);
	}

	/**
	 * Adds a Resistance listener.
	 */
	public void addResistanceListener(ResistanceListener listener) {
		listenerResistance.add(listener);
	}

	/**
	 * Removes a Resistance listener.
	 */
	public void removeResistanceListener(ResistanceListener listener) {
		listenerResistance.remove(listener);
	}

	/**
	 * Adds a ResistanceReached listener.
	 */
	public void addResistanceReachedListener(ResistanceReachedListener listener) {
		listenerResistanceReached.add(listener);
	}

	/**
	 * Removes a ResistanceReached listener.
	 */
	public void removeResistanceReachedListener(ResistanceReachedListener listener) {
		listenerResistanceReached.remove(listener);
	}
}