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
 * Dual-Axis Joystick with Button
 */
public class BrickletJoystick extends Device {
	public final static int DEVICE_IDENTIFIER = 210;

	public final static byte FUNCTION_GET_POSITION = (byte)1;
	public final static byte FUNCTION_IS_PRESSED = (byte)2;
	public final static byte FUNCTION_GET_ANALOG_VALUE = (byte)3;
	public final static byte FUNCTION_CALIBRATE = (byte)4;
	public final static byte FUNCTION_SET_POSITION_CALLBACK_PERIOD = (byte)5;
	public final static byte FUNCTION_GET_POSITION_CALLBACK_PERIOD = (byte)6;
	public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)7;
	public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)8;
	public final static byte FUNCTION_SET_POSITION_CALLBACK_THRESHOLD = (byte)9;
	public final static byte FUNCTION_GET_POSITION_CALLBACK_THRESHOLD = (byte)10;
	public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)11;
	public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)12;
	public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)13;
	public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)14;
	public final static byte CALLBACK_POSITION = (byte)15;
	public final static byte CALLBACK_ANALOG_VALUE = (byte)16;
	public final static byte CALLBACK_POSITION_REACHED = (byte)17;
	public final static byte CALLBACK_ANALOG_VALUE_REACHED = (byte)18;
	public final static byte CALLBACK_PRESSED = (byte)19;
	public final static byte CALLBACK_RELEASED = (byte)20;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static char THRESHOLD_OPTION_OFF = 'x';
	public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
	public final static char THRESHOLD_OPTION_INSIDE = 'i';
	public final static char THRESHOLD_OPTION_SMALLER = '<';
	public final static char THRESHOLD_OPTION_GREATER = '>';

	private List<PositionListener> listenerPosition = new CopyOnWriteArrayList<PositionListener>();
	private List<AnalogValueListener> listenerAnalogValue = new CopyOnWriteArrayList<AnalogValueListener>();
	private List<PositionReachedListener> listenerPositionReached = new CopyOnWriteArrayList<PositionReachedListener>();
	private List<AnalogValueReachedListener> listenerAnalogValueReached = new CopyOnWriteArrayList<AnalogValueReachedListener>();
	private List<PressedListener> listenerPressed = new CopyOnWriteArrayList<PressedListener>();
	private List<ReleasedListener> listenerReleased = new CopyOnWriteArrayList<ReleasedListener>();

	public class Position {
		public short x;
		public short y;

		public String toString() {
			return "[" + "x = " + x + ", " + "y = " + y + "]";
		}
	}

	public class AnalogValue {
		public int x;
		public int y;

		public String toString() {
			return "[" + "x = " + x + ", " + "y = " + y + "]";
		}
	}

	public class PositionCallbackThreshold {
		public char option;
		public short minX;
		public short maxX;
		public short minY;
		public short maxY;

		public String toString() {
			return "[" + "option = " + option + ", " + "minX = " + minX + ", " + "maxX = " + maxX + ", " + "minY = " + minY + ", " + "maxY = " + maxY + "]";
		}
	}

	public class AnalogValueCallbackThreshold {
		public char option;
		public int minX;
		public int maxX;
		public int minY;
		public int maxY;

		public String toString() {
			return "[" + "option = " + option + ", " + "minX = " + minX + ", " + "maxX = " + maxX + ", " + "minY = " + minY + ", " + "maxY = " + maxY + "]";
		}
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletJoystick#setPositionCallbackPeriod(long)}. The parameter is the position of the
	 * Joystick.
	 * 
	 * {@link BrickletJoystick.PositionListener} is only triggered if the position has changed since the
	 * last triggering.
	 */
	public interface PositionListener {
		public void position(short x, short y);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletJoystick#setAnalogValueCallbackPeriod(long)}. The parameters are the analog values
	 * of the Joystick.
	 * 
	 * {@link BrickletJoystick.AnalogValueListener} is only triggered if the values have changed since the
	 * last triggering.
	 */
	public interface AnalogValueListener {
		public void analogValue(int x, int y);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletJoystick#setPositionCallbackThreshold(char, short, short, short, short)} is reached.
	 * The parameters are the position of the Joystick.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletJoystick#setDebouncePeriod(long)}.
	 */
	public interface PositionReachedListener {
		public void positionReached(short x, short y);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletJoystick#setAnalogValueCallbackThreshold(char, int, int, int, int)} is reached.
	 * The parameters are the analog values of the Joystick.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletJoystick#setDebouncePeriod(long)}.
	 */
	public interface AnalogValueReachedListener {
		public void analogValueReached(int x, int y);
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
	public BrickletJoystick(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_POSITION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_IS_PRESSED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ANALOG_VALUE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_CALIBRATE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_POSITION_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_POSITION_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_POSITION_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_POSITION_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_POSITION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_ANALOG_VALUE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_POSITION_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_ANALOG_VALUE_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_PRESSED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_RELEASED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_POSITION] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short x = (bb.getShort());
				short y = (bb.getShort());

				for(PositionListener listener: listenerPosition) {
					listener.position(x, y);
				}
			}
		};

		callbacks[CALLBACK_ANALOG_VALUE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int x = IPConnection.unsignedShort(bb.getShort());
				int y = IPConnection.unsignedShort(bb.getShort());

				for(AnalogValueListener listener: listenerAnalogValue) {
					listener.analogValue(x, y);
				}
			}
		};

		callbacks[CALLBACK_POSITION_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short x = (bb.getShort());
				short y = (bb.getShort());

				for(PositionReachedListener listener: listenerPositionReached) {
					listener.positionReached(x, y);
				}
			}
		};

		callbacks[CALLBACK_ANALOG_VALUE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int x = IPConnection.unsignedShort(bb.getShort());
				int y = IPConnection.unsignedShort(bb.getShort());

				for(AnalogValueReachedListener listener: listenerAnalogValueReached) {
					listener.analogValueReached(x, y);
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
	 * Returns the position of the Joystick. The value ranges between -100 and
	 * 100 for both axis. The middle position of the joystick is x=0, y=0. The
	 * returned values are averaged and calibrated (see {@link BrickletJoystick#calibrate()}).
	 * 
	 * If you want to get the position periodically, it is recommended to use the
	 * listener {@link BrickletJoystick.PositionListener} and set the period with 
	 * {@link BrickletJoystick#setPositionCallbackPeriod(long)}.
	 */
	public Position getPosition() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_POSITION, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Position obj = new Position();
		obj.x = (bb.getShort());
		obj.y = (bb.getShort());

		return obj;
	}

	/**
	 * Returns *true* if the button is pressed and *false* otherwise.
	 * 
	 * It is recommended to use the {@link BrickletJoystick.PressedListener} and {@link BrickletJoystick.ReleasedListener} listeners
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
	 * Returns the values as read by a 12-bit analog-to-digital converter.
	 * The values are between 0 and 4095 for both axis.
	 * 
	 * \note
	 *  The values returned by {@link BrickletJoystick#getPosition()} are averaged over several samples
	 *  to yield less noise, while {@link BrickletJoystick#getAnalogValue()} gives back raw
	 *  unfiltered analog values. The only reason to use {@link BrickletJoystick#getAnalogValue()} is,
	 *  if you need the full resolution of the analog-to-digital converter.
	 * 
	 * If you want the analog values periodically, it is recommended to use the 
	 * listener {@link BrickletJoystick.AnalogValueListener} and set the period with 
	 * {@link BrickletJoystick#setAnalogValueCallbackPeriod(long)}.
	 */
	public AnalogValue getAnalogValue() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ANALOG_VALUE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AnalogValue obj = new AnalogValue();
		obj.x = IPConnection.unsignedShort(bb.getShort());
		obj.y = IPConnection.unsignedShort(bb.getShort());

		return obj;
	}

	/**
	 * Calibrates the middle position of the Joystick. If your Joystick Bricklet
	 * does not return x=0 and y=0 in the middle position, call this function
	 * while the Joystick is standing still in the middle position.
	 * 
	 * The resulting calibration will be saved on the EEPROM of the Joystick
	 * Bricklet, thus you only have to calibrate it once.
	 */
	public void calibrate() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_CALIBRATE, this);

		sendRequest(bb.array());
	}

	/**
	 * Sets the period in ms with which the {@link BrickletJoystick.PositionListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletJoystick.PositionListener} is only triggered if the position has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setPositionCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_POSITION_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletJoystick#setPositionCallbackPeriod(long)}.
	 */
	public long getPositionCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_POSITION_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletJoystick.AnalogValueListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletJoystick.AnalogValueListener} is only triggered if the analog values have changed since the
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
	 * Returns the period as set by {@link BrickletJoystick#setAnalogValueCallbackPeriod(long)}.
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
	 * Sets the thresholds for the {@link BrickletJoystick.PositionReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the position is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the position is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the position is smaller than the min values (max is ignored)"
	 *  "'>'",    "Listener is triggered when the position is greater than the min values (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0, 0, 0).
	 */
	public void setPositionCallbackThreshold(char option, short minX, short maxX, short minY, short maxY) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)17, FUNCTION_SET_POSITION_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putShort(minX);
		bb.putShort(maxX);
		bb.putShort(minY);
		bb.putShort(maxY);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletJoystick#setPositionCallbackThreshold(char, short, short, short, short)}.
	 */
	public PositionCallbackThreshold getPositionCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_POSITION_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		PositionCallbackThreshold obj = new PositionCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.minX = (bb.getShort());
		obj.maxX = (bb.getShort());
		obj.minY = (bb.getShort());
		obj.maxY = (bb.getShort());

		return obj;
	}

	/**
	 * Sets the thresholds for the {@link BrickletJoystick.AnalogValueReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the analog values are *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the analog values are *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the analog values are smaller than the min values (max is ignored)"
	 *  "'>'",    "Listener is triggered when the analog values are greater than the min values (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0, 0, 0).
	 */
	public void setAnalogValueCallbackThreshold(char option, int minX, int maxX, int minY, int maxY) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)17, FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putShort((short)minX);
		bb.putShort((short)maxX);
		bb.putShort((short)minY);
		bb.putShort((short)maxY);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletJoystick#setAnalogValueCallbackThreshold(char, int, int, int, int)}.
	 */
	public AnalogValueCallbackThreshold getAnalogValueCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AnalogValueCallbackThreshold obj = new AnalogValueCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.minX = IPConnection.unsignedShort(bb.getShort());
		obj.maxX = IPConnection.unsignedShort(bb.getShort());
		obj.minY = IPConnection.unsignedShort(bb.getShort());
		obj.maxY = IPConnection.unsignedShort(bb.getShort());

		return obj;
	}

	/**
	 * Sets the period in ms with which the threshold listeners
	 * 
	 * * {@link BrickletJoystick.PositionReachedListener},
	 * * {@link BrickletJoystick.AnalogValueReachedListener}
	 * 
	 * are triggered, if the thresholds
	 * 
	 * * {@link BrickletJoystick#setPositionCallbackThreshold(char, short, short, short, short)},
	 * * {@link BrickletJoystick#setAnalogValueCallbackThreshold(char, int, int, int, int)}
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
	 * Returns the debounce period as set by {@link BrickletJoystick#setDebouncePeriod(long)}.
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
	 * Adds a Position listener.
	 */
	public void addPositionListener(PositionListener listener) {
		listenerPosition.add(listener);
	}

	/**
	 * Removes a Position listener.
	 */
	public void removePositionListener(PositionListener listener) {
		listenerPosition.remove(listener);
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
	 * Adds a PositionReached listener.
	 */
	public void addPositionReachedListener(PositionReachedListener listener) {
		listenerPositionReached.add(listener);
	}

	/**
	 * Removes a PositionReached listener.
	 */
	public void removePositionReachedListener(PositionReachedListener listener) {
		listenerPositionReached.remove(listener);
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