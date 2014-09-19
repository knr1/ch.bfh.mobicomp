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
 * Device for sensing Rotary Encoder input
 */
public class BrickletRotaryEncoder extends Device {
	public final static int DEVICE_IDENTIFIER = 236;

	public final static byte FUNCTION_GET_COUNT = (byte)1;
	public final static byte FUNCTION_SET_COUNT_CALLBACK_PERIOD = (byte)2;
	public final static byte FUNCTION_GET_COUNT_CALLBACK_PERIOD = (byte)3;
	public final static byte FUNCTION_SET_COUNT_CALLBACK_THRESHOLD = (byte)4;
	public final static byte FUNCTION_GET_COUNT_CALLBACK_THRESHOLD = (byte)5;
	public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
	public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
	public final static byte CALLBACK_COUNT = (byte)8;
	public final static byte CALLBACK_COUNT_REACHED = (byte)9;
	public final static byte FUNCTION_IS_PRESSED = (byte)10;
	public final static byte CALLBACK_PRESSED = (byte)11;
	public final static byte CALLBACK_RELEASED = (byte)12;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static char THRESHOLD_OPTION_OFF = 'x';
	public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
	public final static char THRESHOLD_OPTION_INSIDE = 'i';
	public final static char THRESHOLD_OPTION_SMALLER = '<';
	public final static char THRESHOLD_OPTION_GREATER = '>';

	private List<CountListener> listenerCount = new CopyOnWriteArrayList<CountListener>();
	private List<CountReachedListener> listenerCountReached = new CopyOnWriteArrayList<CountReachedListener>();
	private List<PressedListener> listenerPressed = new CopyOnWriteArrayList<PressedListener>();
	private List<ReleasedListener> listenerReleased = new CopyOnWriteArrayList<ReleasedListener>();

	public class CountCallbackThreshold {
		public char option;
		public int min;
		public int max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletRotaryEncoder#setCountCallbackPeriod(long)}. The parameter is the count of
	 * the encoder.
	 * 
	 * {@link BrickletRotaryEncoder.CountListener} is only triggered if the count has changed since the
	 * last triggering.
	 */
	public interface CountListener {
		public void count(int count);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletRotaryEncoder#setCountCallbackThreshold(char, int, int)} is reached.
	 * The parameter is the count of the encoder.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletRotaryEncoder#setDebouncePeriod(long)}.
	 */
	public interface CountReachedListener {
		public void countReached(int count);
	}

	/**
	 * This listener is triggered when the button is pressed.
	 */
	public interface PressedListener {
		public void pressed();
	}

	/**
	 * This listener is triggered when the button is released.
	 */
	public interface ReleasedListener {
		public void released();
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletRotaryEncoder(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_COUNT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_COUNT_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_COUNT_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_COUNT_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_COUNT_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_IS_PRESSED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_COUNT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_COUNT_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_PRESSED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_RELEASED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_COUNT] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int count = (bb.getInt());

				for(CountListener listener: listenerCount) {
					listener.count(count);
				}
			}
		};

		callbacks[CALLBACK_COUNT_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int count = (bb.getInt());

				for(CountReachedListener listener: listenerCountReached) {
					listener.countReached(count);
				}
			}
		};

		callbacks[CALLBACK_PRESSED] = new CallbackListener() {
			public void callback(byte[] data) {
				for(PressedListener listener: listenerPressed) {
					listener.pressed();
				}
			}
		};

		callbacks[CALLBACK_RELEASED] = new CallbackListener() {
			public void callback(byte[] data) {
				for(ReleasedListener listener: listenerReleased) {
					listener.released();
				}
			}
		};
	}

	/**
	 * Returns the current count of the encoder. If you set reset
	 * to true, the count is set back to 0 directly after the
	 * current count is read.
	 * 
	 * The encoder has 24 steps per rotation
	 * 
	 * Turning the encoder to the left decrements the counter,
	 * so a negative count is possible.
	 */
	public int getCount(boolean reset) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_GET_COUNT, this);
		bb.put((byte)(reset ? 1 : 0));

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int count = (bb.getInt());

		return count;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletRotaryEncoder.CountListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletRotaryEncoder.CountListener} is only triggered if the count has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setCountCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_COUNT_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletRotaryEncoder#setCountCallbackPeriod(long)}.
	 */
	public long getCountCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_COUNT_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the thresholds for the {@link BrickletRotaryEncoder.CountReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the count is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the count is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the count is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the count is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setCountCallbackThreshold(char option, int min, int max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)17, FUNCTION_SET_COUNT_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putInt(min);
		bb.putInt(max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletRotaryEncoder#setCountCallbackThreshold(char, int, int)}.
	 */
	public CountCallbackThreshold getCountCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_COUNT_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		CountCallbackThreshold obj = new CountCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getInt());
		obj.max = (bb.getInt());

		return obj;
	}

	/**
	 * Sets the period in ms with which the threshold listener
	 * 
	 * * {@link BrickletRotaryEncoder.CountReachedListener}
	 * 
	 * is triggered, if the thresholds
	 * 
	 * * {@link BrickletRotaryEncoder#setCountCallbackThreshold(char, int, int)}
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
	 * Returns the debounce period as set by {@link BrickletRotaryEncoder#setDebouncePeriod(long)}.
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
	 * Returns *true* if the button is pressed and *false* otherwise.
	 * 
	 * It is recommended to use the {@link BrickletRotaryEncoder.PressedListener} and {@link BrickletRotaryEncoder.ReleasedListener} listeners
	 * to handle the button.
	 */
	public boolean isPressed() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_IS_PRESSED, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean pressed = (bb.get()) != 0;

		return pressed;
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
	 * Adds a Count listener.
	 */
	public void addCountListener(CountListener listener) {
		listenerCount.add(listener);
	}

	/**
	 * Removes a Count listener.
	 */
	public void removeCountListener(CountListener listener) {
		listenerCount.remove(listener);
	}

	/**
	 * Adds a CountReached listener.
	 */
	public void addCountReachedListener(CountReachedListener listener) {
		listenerCountReached.add(listener);
	}

	/**
	 * Removes a CountReached listener.
	 */
	public void removeCountReachedListener(CountReachedListener listener) {
		listenerCountReached.remove(listener);
	}

	/**
	 * Adds a Pressed listener.
	 */
	public void addPressedListener(PressedListener listener) {
		listenerPressed.add(listener);
	}

	/**
	 * Removes a Pressed listener.
	 */
	public void removePressedListener(PressedListener listener) {
		listenerPressed.remove(listener);
	}

	/**
	 * Adds a Released listener.
	 */
	public void addReleasedListener(ReleasedListener listener) {
		listenerReleased.add(listener);
	}

	/**
	 * Removes a Released listener.
	 */
	public void removeReleasedListener(ReleasedListener listener) {
		listenerReleased.remove(listener);
	}
}