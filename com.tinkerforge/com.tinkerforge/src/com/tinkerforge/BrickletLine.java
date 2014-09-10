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
 * Device that measures reflectivity to allow following a line
 */
public class BrickletLine extends Device {
	public final static int DEVICE_IDENTIFIER = 241;

	public final static byte FUNCTION_GET_REFLECTIVITY = (byte)1;
	public final static byte FUNCTION_SET_REFLECTIVITY_CALLBACK_PERIOD = (byte)2;
	public final static byte FUNCTION_GET_REFLECTIVITY_CALLBACK_PERIOD = (byte)3;
	public final static byte FUNCTION_SET_REFLECTIVITY_CALLBACK_THRESHOLD = (byte)4;
	public final static byte FUNCTION_GET_REFLECTIVITY_CALLBACK_THRESHOLD = (byte)5;
	public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
	public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
	public final static byte CALLBACK_REFLECTIVITY = (byte)8;
	public final static byte CALLBACK_REFLECTIVITY_REACHED = (byte)9;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static char THRESHOLD_OPTION_OFF = 'x';
	public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
	public final static char THRESHOLD_OPTION_INSIDE = 'i';
	public final static char THRESHOLD_OPTION_SMALLER = '<';
	public final static char THRESHOLD_OPTION_GREATER = '>';

	private List<ReflectivityListener> listenerReflectivity = new CopyOnWriteArrayList<ReflectivityListener>();
	private List<ReflectivityReachedListener> listenerReflectivityReached = new CopyOnWriteArrayList<ReflectivityReachedListener>();

	public class ReflectivityCallbackThreshold {
		public char option;
		public int min;
		public int max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletLine#setReflectivityCallbackPeriod(long)}. The parameter is the reflectivity
	 * of the sensor.
	 * 
	 * {@link BrickletLine.ReflectivityListener} is only triggered if the reflectivity has changed since the
	 * last triggering.
	 */
	public interface ReflectivityListener {
		public void reflectivity(int reflectivity);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletLine#setReflectivityCallbackThreshold(char, int, int)} is reached.
	 * The parameter is the reflectivity of the sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletLine#setDebouncePeriod(long)}.
	 */
	public interface ReflectivityReachedListener {
		public void reflectivityReached(int reflectivity);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletLine(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_REFLECTIVITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_REFLECTIVITY_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_REFLECTIVITY_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_REFLECTIVITY_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_REFLECTIVITY_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_REFLECTIVITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_REFLECTIVITY_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_REFLECTIVITY] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int reflectivity = IPConnection.unsignedShort(bb.getShort());

				for(ReflectivityListener listener: listenerReflectivity) {
					listener.reflectivity(reflectivity);
				}
			}
		};

		callbacks[CALLBACK_REFLECTIVITY_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int reflectivity = IPConnection.unsignedShort(bb.getShort());

				for(ReflectivityReachedListener listener: listenerReflectivityReached) {
					listener.reflectivityReached(reflectivity);
				}
			}
		};
	}

	/**
	 * Returns the currently measured reflectivity. The reflectivity is
	 * a value between 0 (not reflective) and 4095 (very reflective).
	 * 
	 * Usually black has a low reflectivity while white has a high
	 * reflectivity.
	 * 
	 * If you want to get the reflectivity periodically, it is recommended 
	 * to use the listener {@link BrickletLine.ReflectivityListener} and set the period with 
	 * {@link BrickletLine#setReflectivityCallbackPeriod(long)}.
	 */
	public int getReflectivity() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_REFLECTIVITY, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int reflectivity = IPConnection.unsignedShort(bb.getShort());

		return reflectivity;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletLine.ReflectivityListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletLine.ReflectivityListener} is only triggered if the reflectivity has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setReflectivityCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_REFLECTIVITY_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletLine#setReflectivityCallbackPeriod(long)}.
	 */
	public long getReflectivityCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_REFLECTIVITY_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the thresholds for the {@link BrickletLine.ReflectivityReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the reflectivity is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the reflectivity is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the reflectivity is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the reflectivity is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setReflectivityCallbackThreshold(char option, int min, int max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)13, FUNCTION_SET_REFLECTIVITY_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putShort((short)min);
		bb.putShort((short)max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletLine#setReflectivityCallbackThreshold(char, int, int)}.
	 */
	public ReflectivityCallbackThreshold getReflectivityCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_REFLECTIVITY_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		ReflectivityCallbackThreshold obj = new ReflectivityCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = IPConnection.unsignedShort(bb.getShort());
		obj.max = IPConnection.unsignedShort(bb.getShort());

		return obj;
	}

	/**
	 * Sets the period in ms with which the threshold listener
	 * 
	 * * {@link BrickletLine.ReflectivityReachedListener}
	 * 
	 * is triggered, if the threshold
	 * 
	 * * {@link BrickletLine#setReflectivityCallbackThreshold(char, int, int)}
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
	 * Returns the debounce period as set by {@link BrickletLine#setDebouncePeriod(long)}.
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
	 * Adds a Reflectivity listener.
	 */
	public void addReflectivityListener(ReflectivityListener listener) {
		listenerReflectivity.add(listener);
	}

	/**
	 * Removes a Reflectivity listener.
	 */
	public void removeReflectivityListener(ReflectivityListener listener) {
		listenerReflectivity.remove(listener);
	}

	/**
	 * Adds a ReflectivityReached listener.
	 */
	public void addReflectivityReachedListener(ReflectivityReachedListener listener) {
		listenerReflectivityReached.add(listener);
	}

	/**
	 * Removes a ReflectivityReached listener.
	 */
	public void removeReflectivityReachedListener(ReflectivityReachedListener listener) {
		listenerReflectivityReached.remove(listener);
	}
}