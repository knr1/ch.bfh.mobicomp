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
 * Device for sensing Temperature
 */
public class BrickletTemperature extends Device {
	public final static int DEVICE_IDENTIFIER = 216;

	public final static byte FUNCTION_GET_TEMPERATURE = (byte)1;
	public final static byte FUNCTION_SET_TEMPERATURE_CALLBACK_PERIOD = (byte)2;
	public final static byte FUNCTION_GET_TEMPERATURE_CALLBACK_PERIOD = (byte)3;
	public final static byte FUNCTION_SET_TEMPERATURE_CALLBACK_THRESHOLD = (byte)4;
	public final static byte FUNCTION_GET_TEMPERATURE_CALLBACK_THRESHOLD = (byte)5;
	public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
	public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
	public final static byte CALLBACK_TEMPERATURE = (byte)8;
	public final static byte CALLBACK_TEMPERATURE_REACHED = (byte)9;
	public final static byte FUNCTION_SET_I2C_MODE = (byte)10;
	public final static byte FUNCTION_GET_I2C_MODE = (byte)11;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static char THRESHOLD_OPTION_OFF = 'x';
	public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
	public final static char THRESHOLD_OPTION_INSIDE = 'i';
	public final static char THRESHOLD_OPTION_SMALLER = '<';
	public final static char THRESHOLD_OPTION_GREATER = '>';
	public final static short I2C_MODE_FAST = (short)0;
	public final static short I2C_MODE_SLOW = (short)1;

	private List<TemperatureListener> listenerTemperature = new CopyOnWriteArrayList<TemperatureListener>();
	private List<TemperatureReachedListener> listenerTemperatureReached = new CopyOnWriteArrayList<TemperatureReachedListener>();

	public class TemperatureCallbackThreshold {
		public char option;
		public short min;
		public short max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletTemperature#setTemperatureCallbackPeriod(long)}. The parameter is the temperature
	 * of the sensor.
	 * 
	 * {@link BrickletTemperature.TemperatureListener} is only triggered if the temperature has changed since the
	 * last triggering.
	 */
	public interface TemperatureListener {
		public void temperature(short temperature);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletTemperature#setTemperatureCallbackThreshold(char, short, short)} is reached.
	 * The parameter is the temperature of the sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletTemperature#setDebouncePeriod(long)}.
	 */
	public interface TemperatureReachedListener {
		public void temperatureReached(short temperature);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletTemperature(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_TEMPERATURE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_TEMPERATURE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_TEMPERATURE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_TEMPERATURE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_TEMPERATURE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_I2C_MODE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_I2C_MODE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_TEMPERATURE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_TEMPERATURE_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_TEMPERATURE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short temperature = (bb.getShort());

				for(TemperatureListener listener: listenerTemperature) {
					listener.temperature(temperature);
				}
			}
		};

		callbacks[CALLBACK_TEMPERATURE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short temperature = (bb.getShort());

				for(TemperatureReachedListener listener: listenerTemperatureReached) {
					listener.temperatureReached(temperature);
				}
			}
		};
	}

	/**
	 * Returns the temperature of the sensor. The value
	 * has a range of -2500 to 8500 and is given in °C/100,
	 * e.g. a value of 4223 means that a temperature of 42.23 °C is measured.
	 * 
	 * If you want to get the temperature periodically, it is recommended 
	 * to use the listener {@link BrickletTemperature.TemperatureListener} and set the period with 
	 * {@link BrickletTemperature#setTemperatureCallbackPeriod(long)}.
	 */
	public short getTemperature() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_TEMPERATURE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short temperature = (bb.getShort());

		return temperature;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletTemperature.TemperatureListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletTemperature.TemperatureListener} is only triggered if the temperature has changed since the
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
	 * Returns the period as set by {@link BrickletTemperature#setTemperatureCallbackPeriod(long)}.
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
	 * Sets the thresholds for the {@link BrickletTemperature.TemperatureReachedListener} listener. 
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
	public void setTemperatureCallbackThreshold(char option, short min, short max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)13, FUNCTION_SET_TEMPERATURE_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putShort(min);
		bb.putShort(max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletTemperature#setTemperatureCallbackThreshold(char, short, short)}.
	 */
	public TemperatureCallbackThreshold getTemperatureCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_TEMPERATURE_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		TemperatureCallbackThreshold obj = new TemperatureCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getShort());
		obj.max = (bb.getShort());

		return obj;
	}

	/**
	 * Sets the period in ms with which the threshold listener
	 * 
	 * * {@link BrickletTemperature.TemperatureReachedListener}
	 * 
	 * is triggered, if the threshold
	 * 
	 * * {@link BrickletTemperature#setTemperatureCallbackThreshold(char, short, short)}
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
	 * Returns the debounce period as set by {@link BrickletTemperature#setDebouncePeriod(long)}.
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
	 * Sets the I2C mode. Possible modes are:
	 * 
	 * * 0: Fast (400kHz, default)
	 * * 1: Slow (100kHz)
	 * 
	 * If you have problems with obvious outliers in the
	 * Temperature Bricklet measurements, they may be caused by EMI issues.
	 * In this case it may be helpful to lower the I2C speed.
	 * 
	 * It is however not recommended to lower the I2C speed in applications where
	 * a high throughput needs to be achieved.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public void setI2CMode(short mode) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_SET_I2C_MODE, this);
		bb.put((byte)mode);

		sendRequest(bb.array());
	}

	/**
	 * Returns the I2C mode as set by {@link BrickletTemperature#setI2CMode(short)}.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public short getI2CMode() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_I2C_MODE, this);

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
}