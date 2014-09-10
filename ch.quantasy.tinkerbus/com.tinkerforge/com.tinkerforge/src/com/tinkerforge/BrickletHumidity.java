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
 * Device for sensing Humidity
 */
public class BrickletHumidity extends Device {
	public final static int DEVICE_IDENTIFIER = 27;

	public final static byte FUNCTION_GET_HUMIDITY = (byte)1;
	public final static byte FUNCTION_GET_ANALOG_VALUE = (byte)2;
	public final static byte FUNCTION_SET_HUMIDITY_CALLBACK_PERIOD = (byte)3;
	public final static byte FUNCTION_GET_HUMIDITY_CALLBACK_PERIOD = (byte)4;
	public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)5;
	public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)6;
	public final static byte FUNCTION_SET_HUMIDITY_CALLBACK_THRESHOLD = (byte)7;
	public final static byte FUNCTION_GET_HUMIDITY_CALLBACK_THRESHOLD = (byte)8;
	public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)9;
	public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)10;
	public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)11;
	public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)12;
	public final static byte CALLBACK_HUMIDITY = (byte)13;
	public final static byte CALLBACK_ANALOG_VALUE = (byte)14;
	public final static byte CALLBACK_HUMIDITY_REACHED = (byte)15;
	public final static byte CALLBACK_ANALOG_VALUE_REACHED = (byte)16;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static char THRESHOLD_OPTION_OFF = 'x';
	public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
	public final static char THRESHOLD_OPTION_INSIDE = 'i';
	public final static char THRESHOLD_OPTION_SMALLER = '<';
	public final static char THRESHOLD_OPTION_GREATER = '>';

	private List<HumidityListener> listenerHumidity = new CopyOnWriteArrayList<HumidityListener>();
	private List<AnalogValueListener> listenerAnalogValue = new CopyOnWriteArrayList<AnalogValueListener>();
	private List<HumidityReachedListener> listenerHumidityReached = new CopyOnWriteArrayList<HumidityReachedListener>();
	private List<AnalogValueReachedListener> listenerAnalogValueReached = new CopyOnWriteArrayList<AnalogValueReachedListener>();

	public class HumidityCallbackThreshold {
		public char option;
		public short min;
		public short max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	public class AnalogValueCallbackThreshold {
		public char option;
		public int min;
		public int max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletHumidity#setHumidityCallbackPeriod(long)}. The parameter is the humidity of the
	 * sensor.
	 * 
	 * {@link BrickletHumidity.HumidityListener} is only triggered if the humidity has changed since the
	 * last triggering.
	 */
	public interface HumidityListener {
		public void humidity(int humidity);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletHumidity#setAnalogValueCallbackPeriod(long)}. The parameter is the analog value of the
	 * sensor.
	 * 
	 * {@link BrickletHumidity.AnalogValueListener} is only triggered if the humidity has changed since the
	 * last triggering.
	 */
	public interface AnalogValueListener {
		public void analogValue(int value);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletHumidity#setHumidityCallbackThreshold(char, short, short)} is reached.
	 * The parameter is the humidity of the sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletHumidity#setDebouncePeriod(long)}.
	 */
	public interface HumidityReachedListener {
		public void humidityReached(int humidity);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletHumidity#setAnalogValueCallbackThreshold(char, int, int)} is reached.
	 * The parameter is the analog value of the sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletHumidity#setDebouncePeriod(long)}.
	 */
	public interface AnalogValueReachedListener {
		public void analogValueReached(int value);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletHumidity(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_HUMIDITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ANALOG_VALUE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_HUMIDITY_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_HUMIDITY_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_HUMIDITY_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_HUMIDITY_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_HUMIDITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_ANALOG_VALUE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_HUMIDITY_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_ANALOG_VALUE_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_HUMIDITY] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int humidity = IPConnection.unsignedShort(bb.getShort());

				for(HumidityListener listener: listenerHumidity) {
					listener.humidity(humidity);
				}
			}
		};

		callbacks[CALLBACK_ANALOG_VALUE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int value = IPConnection.unsignedShort(bb.getShort());

				for(AnalogValueListener listener: listenerAnalogValue) {
					listener.analogValue(value);
				}
			}
		};

		callbacks[CALLBACK_HUMIDITY_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int humidity = IPConnection.unsignedShort(bb.getShort());

				for(HumidityReachedListener listener: listenerHumidityReached) {
					listener.humidityReached(humidity);
				}
			}
		};

		callbacks[CALLBACK_ANALOG_VALUE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int value = IPConnection.unsignedShort(bb.getShort());

				for(AnalogValueReachedListener listener: listenerAnalogValueReached) {
					listener.analogValueReached(value);
				}
			}
		};
	}

	/**
	 * Returns the humidity of the sensor. The value
	 * has a range of 0 to 1000 and is given in %RH/10 (Relative Humidity), 
	 * i.e. a value of 421 means that a humidity of 42.1 %RH is measured.
	 * 
	 * If you want to get the humidity periodically, it is recommended to use the
	 * listener {@link BrickletHumidity.HumidityListener} and set the period with 
	 * {@link BrickletHumidity#setHumidityCallbackPeriod(long)}.
	 */
	public int getHumidity() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_HUMIDITY, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int humidity = IPConnection.unsignedShort(bb.getShort());

		return humidity;
	}

	/**
	 * Returns the value as read by a 12-bit analog-to-digital converter.
	 * The value is between 0 and 4095.
	 * 
	 * \note
	 *  The value returned by {@link BrickletHumidity#getHumidity()} is averaged over several samples
	 *  to yield less noise, while {@link BrickletHumidity#getAnalogValue()} gives back raw
	 *  unfiltered analog values. The returned humidity value is calibrated for
	 *  room temperatures, if you use the sensor in extreme cold or extreme
	 *  warm environments, you might want to calculate the humidity from
	 *  the analog value yourself. See the `HIH 5030 datasheet
	 *  <https://github.com/Tinkerforge/humidity-bricklet/raw/master/datasheets/hih-5030.pdf>`__.
	 * 
	 * If you want the analog value periodically, it is recommended to use the 
	 * listener {@link BrickletHumidity.AnalogValueListener} and set the period with 
	 * {@link BrickletHumidity#setAnalogValueCallbackPeriod(long)}.
	 */
	public int getAnalogValue() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ANALOG_VALUE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int value = IPConnection.unsignedShort(bb.getShort());

		return value;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletHumidity.HumidityListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletHumidity.HumidityListener} is only triggered if the humidity has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setHumidityCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_HUMIDITY_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletHumidity#setHumidityCallbackPeriod(long)}.
	 */
	public long getHumidityCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_HUMIDITY_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletHumidity.AnalogValueListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletHumidity.AnalogValueListener} is only triggered if the analog value has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setAnalogValueCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletHumidity#setAnalogValueCallbackPeriod(long)}.
	 */
	public long getAnalogValueCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the thresholds for the {@link BrickletHumidity.HumidityReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the humidity is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the humidity is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the humidity is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the humidity is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setHumidityCallbackThreshold(char option, short min, short max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)13, FUNCTION_SET_HUMIDITY_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putShort(min);
		bb.putShort(max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletHumidity#setHumidityCallbackThreshold(char, short, short)}.
	 */
	public HumidityCallbackThreshold getHumidityCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_HUMIDITY_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		HumidityCallbackThreshold obj = new HumidityCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getShort());
		obj.max = (bb.getShort());

		return obj;
	}

	/**
	 * Sets the thresholds for the {@link BrickletHumidity.AnalogValueReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the analog value is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the analog value is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the analog value is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the analog value is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setAnalogValueCallbackThreshold(char option, int min, int max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)13, FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putShort((short)min);
		bb.putShort((short)max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletHumidity#setAnalogValueCallbackThreshold(char, int, int)}.
	 */
	public AnalogValueCallbackThreshold getAnalogValueCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AnalogValueCallbackThreshold obj = new AnalogValueCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = IPConnection.unsignedShort(bb.getShort());
		obj.max = IPConnection.unsignedShort(bb.getShort());

		return obj;
	}

	/**
	 * Sets the period in ms with which the threshold listeners
	 * 
	 * * {@link BrickletHumidity.HumidityReachedListener},
	 * * {@link BrickletHumidity.AnalogValueReachedListener}
	 * 
	 * are triggered, if the thresholds
	 * 
	 * * {@link BrickletHumidity#setHumidityCallbackThreshold(char, short, short)},
	 * * {@link BrickletHumidity#setAnalogValueCallbackThreshold(char, int, int)}
	 * 
	 * keep being reached.
	 * 
	 * The default value is 100.
	 */
	public void setDebouncePeriod(long debounce) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_DEBOUNCE_PERIOD, this);
		bb.putInt((int)debounce);

		sendRequest(bb.array());
	}

	/**
	 * Returns the debounce period as set by {@link BrickletHumidity#setDebouncePeriod(long)}.
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
	 * Adds a Humidity listener.
	 */
	public void addHumidityListener(HumidityListener listener) {
		listenerHumidity.add(listener);
	}

	/**
	 * Removes a Humidity listener.
	 */
	public void removeHumidityListener(HumidityListener listener) {
		listenerHumidity.remove(listener);
	}

	/**
	 * Adds a AnalogValue listener.
	 */
	public void addAnalogValueListener(AnalogValueListener listener) {
		listenerAnalogValue.add(listener);
	}

	/**
	 * Removes a AnalogValue listener.
	 */
	public void removeAnalogValueListener(AnalogValueListener listener) {
		listenerAnalogValue.remove(listener);
	}

	/**
	 * Adds a HumidityReached listener.
	 */
	public void addHumidityReachedListener(HumidityReachedListener listener) {
		listenerHumidityReached.add(listener);
	}

	/**
	 * Removes a HumidityReached listener.
	 */
	public void removeHumidityReachedListener(HumidityReachedListener listener) {
		listenerHumidityReached.remove(listener);
	}

	/**
	 * Adds a AnalogValueReached listener.
	 */
	public void addAnalogValueReachedListener(AnalogValueReachedListener listener) {
		listenerAnalogValueReached.add(listener);
	}

	/**
	 * Removes a AnalogValueReached listener.
	 */
	public void removeAnalogValueReachedListener(AnalogValueReachedListener listener) {
		listenerAnalogValueReached.remove(listener);
	}
}