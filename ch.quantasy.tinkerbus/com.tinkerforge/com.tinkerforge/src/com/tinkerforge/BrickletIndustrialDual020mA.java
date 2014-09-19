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
 * Device for sensing two currents between 0 and 20mA (IEC 60381-1)
 */
public class BrickletIndustrialDual020mA extends Device {
	public final static int DEVICE_IDENTIFIER = 228;

	public final static byte FUNCTION_GET_CURRENT = (byte)1;
	public final static byte FUNCTION_SET_CURRENT_CALLBACK_PERIOD = (byte)2;
	public final static byte FUNCTION_GET_CURRENT_CALLBACK_PERIOD = (byte)3;
	public final static byte FUNCTION_SET_CURRENT_CALLBACK_THRESHOLD = (byte)4;
	public final static byte FUNCTION_GET_CURRENT_CALLBACK_THRESHOLD = (byte)5;
	public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
	public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
	public final static byte FUNCTION_SET_SAMPLE_RATE = (byte)8;
	public final static byte FUNCTION_GET_SAMPLE_RATE = (byte)9;
	public final static byte CALLBACK_CURRENT = (byte)10;
	public final static byte CALLBACK_CURRENT_REACHED = (byte)11;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static char THRESHOLD_OPTION_OFF = 'x';
	public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
	public final static char THRESHOLD_OPTION_INSIDE = 'i';
	public final static char THRESHOLD_OPTION_SMALLER = '<';
	public final static char THRESHOLD_OPTION_GREATER = '>';
	public final static short SAMPLE_RATE_240_SPS = (short)0;
	public final static short SAMPLE_RATE_60_SPS = (short)1;
	public final static short SAMPLE_RATE_15_SPS = (short)2;
	public final static short SAMPLE_RATE_4_SPS = (short)3;

	private List<CurrentListener> listenerCurrent = new CopyOnWriteArrayList<CurrentListener>();
	private List<CurrentReachedListener> listenerCurrentReached = new CopyOnWriteArrayList<CurrentReachedListener>();

	public class CurrentCallbackThreshold {
		public short sensor;
		public char option;
		public int min;
		public int max;

		public String toString() {
			return "[" + "sensor = " + sensor + ", " + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletIndustrialDual020mA#setCurrentCallbackPeriod(short, long)}. The parameter is the current of the
	 * sensor.
	 * 
	 * {@link BrickletIndustrialDual020mA.CurrentListener} is only triggered if the current has changed since the
	 * last triggering.
	 */
	public interface CurrentListener {
		public void current(short sensor, int current);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletIndustrialDual020mA#setCurrentCallbackThreshold(short, char, int, int)} is reached.
	 * The parameter is the current of the sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletIndustrialDual020mA#setDebouncePeriod(long)}.
	 */
	public interface CurrentReachedListener {
		public void currentReached(short sensor, int current);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletIndustrialDual020mA(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CURRENT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_CURRENT_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CURRENT_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_CURRENT_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CURRENT_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_SAMPLE_RATE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_SAMPLE_RATE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_CURRENT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_CURRENT_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_CURRENT] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short sensor = IPConnection.unsignedByte(bb.get());
				int current = (bb.getInt());

				for(CurrentListener listener: listenerCurrent) {
					listener.current(sensor, current);
				}
			}
		};

		callbacks[CALLBACK_CURRENT_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short sensor = IPConnection.unsignedByte(bb.get());
				int current = (bb.getInt());

				for(CurrentReachedListener listener: listenerCurrentReached) {
					listener.currentReached(sensor, current);
				}
			}
		};
	}

	/**
	 * Returns the current of the specified sensor (0 or 1). The value is in nA
	 * and between 0nA and 22505322nA (22.5mA).
	 * 
	 * It is possible to detect if an IEC 60381-1 compatible sensor is connected
	 * and if it works probably.
	 * 
	 * If the returned current is below 4mA, there is likely no sensor connected
	 * or the sensor may be defect. If the returned current is over 20mA, there might
	 * be a short circuit or the sensor may be defect.
	 * 
	 * If you want to get the current periodically, it is recommended to use the
	 * listener {@link BrickletIndustrialDual020mA.CurrentListener} and set the period with 
	 * {@link BrickletIndustrialDual020mA#setCurrentCallbackPeriod(short, long)}.
	 */
	public int getCurrent(short sensor) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_GET_CURRENT, this);
		bb.put((byte)sensor);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int current = (bb.getInt());

		return current;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletIndustrialDual020mA.CurrentListener} listener is triggered
	 * periodically for the given sensor. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletIndustrialDual020mA.CurrentListener} is only triggered if the current has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setCurrentCallbackPeriod(short sensor, long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)13, FUNCTION_SET_CURRENT_CALLBACK_PERIOD, this);
		bb.put((byte)sensor);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletIndustrialDual020mA#setCurrentCallbackPeriod(short, long)}.
	 */
	public long getCurrentCallbackPeriod(short sensor) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_GET_CURRENT_CALLBACK_PERIOD, this);
		bb.put((byte)sensor);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the thresholds for the {@link BrickletIndustrialDual020mA.CurrentReachedListener} listener for the given
	 * sensor.
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the current is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the current is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the current is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the current is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setCurrentCallbackThreshold(short sensor, char option, int min, int max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)18, FUNCTION_SET_CURRENT_CALLBACK_THRESHOLD, this);
		bb.put((byte)sensor);
		bb.put((byte)option);
		bb.putInt(min);
		bb.putInt(max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletIndustrialDual020mA#setCurrentCallbackThreshold(short, char, int, int)}.
	 */
	public CurrentCallbackThreshold getCurrentCallbackThreshold(short sensor) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_GET_CURRENT_CALLBACK_THRESHOLD, this);
		bb.put((byte)sensor);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		CurrentCallbackThreshold obj = new CurrentCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getInt());
		obj.max = (bb.getInt());

		return obj;
	}

	/**
	 * Sets the period in ms with which the threshold listener
	 * 
	 * * {@link BrickletIndustrialDual020mA.CurrentReachedListener}
	 * 
	 * is triggered, if the threshold
	 * 
	 * * {@link BrickletIndustrialDual020mA#setCurrentCallbackThreshold(short, char, int, int)}
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
	 * Returns the debounce period as set by {@link BrickletIndustrialDual020mA#setDebouncePeriod(long)}.
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
	 * Sets the sample rate to either 240, 60, 15 or 4 samples per second.
	 * The resolution for the rates is 12, 14, 16 and 18 bit respectively.
	 * 
	 * \verbatim
	 *  "Value", "Description"
	 * 
	 *  "0",    "240 samples per second, 12 bit resolution"
	 *  "1",    "60 samples per second, 14 bit resolution"
	 *  "2",    "15 samples per second, 16 bit resolution"
	 *  "3",    "4 samples per second, 18 bit resolution"
	 * \endverbatim
	 * 
	 * The default value is 3: 4 samples per second with 18 bit resolution.
	 */
	public void setSampleRate(short rate) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_SET_SAMPLE_RATE, this);
		bb.put((byte)rate);

		sendRequest(bb.array());
	}

	/**
	 * Returns the sample rate as set by {@link BrickletIndustrialDual020mA#setSampleRate(short)}.
	 */
	public short getSampleRate() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_SAMPLE_RATE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short rate = IPConnection.unsignedByte(bb.get());

		return rate;
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
	 * Adds a Current listener.
	 */
	public void addCurrentListener(CurrentListener listener) {
		listenerCurrent.add(listener);
	}

	/**
	 * Removes a Current listener.
	 */
	public void removeCurrentListener(CurrentListener listener) {
		listenerCurrent.remove(listener);
	}

	/**
	 * Adds a CurrentReached listener.
	 */
	public void addCurrentReachedListener(CurrentReachedListener listener) {
		listenerCurrentReached.add(listener);
	}

	/**
	 * Removes a CurrentReached listener.
	 */
	public void removeCurrentReachedListener(CurrentReachedListener listener) {
		listenerCurrentReached.remove(listener);
	}
}