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
 * Device for sensing distance via infrared
 */
public class BrickletDistanceIR extends Device {
	public final static int DEVICE_IDENTIFIER = 25;

	public final static byte FUNCTION_GET_DISTANCE = (byte)1;
	public final static byte FUNCTION_GET_ANALOG_VALUE = (byte)2;
	public final static byte FUNCTION_SET_SAMPLING_POINT = (byte)3;
	public final static byte FUNCTION_GET_SAMPLING_POINT = (byte)4;
	public final static byte FUNCTION_SET_DISTANCE_CALLBACK_PERIOD = (byte)5;
	public final static byte FUNCTION_GET_DISTANCE_CALLBACK_PERIOD = (byte)6;
	public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)7;
	public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD = (byte)8;
	public final static byte FUNCTION_SET_DISTANCE_CALLBACK_THRESHOLD = (byte)9;
	public final static byte FUNCTION_GET_DISTANCE_CALLBACK_THRESHOLD = (byte)10;
	public final static byte FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)11;
	public final static byte FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD = (byte)12;
	public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)13;
	public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)14;
	public final static byte CALLBACK_DISTANCE = (byte)15;
	public final static byte CALLBACK_ANALOG_VALUE = (byte)16;
	public final static byte CALLBACK_DISTANCE_REACHED = (byte)17;
	public final static byte CALLBACK_ANALOG_VALUE_REACHED = (byte)18;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static char THRESHOLD_OPTION_OFF = 'x';
	public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
	public final static char THRESHOLD_OPTION_INSIDE = 'i';
	public final static char THRESHOLD_OPTION_SMALLER = '<';
	public final static char THRESHOLD_OPTION_GREATER = '>';

	private List<DistanceListener> listenerDistance = new CopyOnWriteArrayList<DistanceListener>();
	private List<AnalogValueListener> listenerAnalogValue = new CopyOnWriteArrayList<AnalogValueListener>();
	private List<DistanceReachedListener> listenerDistanceReached = new CopyOnWriteArrayList<DistanceReachedListener>();
	private List<AnalogValueReachedListener> listenerAnalogValueReached = new CopyOnWriteArrayList<AnalogValueReachedListener>();

	public class DistanceCallbackThreshold {
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
	 * {@link BrickletDistanceIR#setDistanceCallbackPeriod(long)}. The parameter is the distance of the
	 * sensor.
	 * 
	 * {@link BrickletDistanceIR.DistanceListener} is only triggered if the distance has changed since the
	 * last triggering.
	 */
	public interface DistanceListener {
		public void distance(int distance);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletDistanceIR#setAnalogValueCallbackPeriod(long)}. The parameter is the analog value of the
	 * sensor.
	 * 
	 * {@link BrickletDistanceIR.AnalogValueListener} is only triggered if the analog value has changed since the
	 * last triggering.
	 */
	public interface AnalogValueListener {
		public void analogValue(int value);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletDistanceIR#setDistanceCallbackThreshold(char, short, short)} is reached.
	 * The parameter is the distance of the sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletDistanceIR#setDebouncePeriod(long)}.
	 */
	public interface DistanceReachedListener {
		public void distanceReached(int distance);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletDistanceIR#setAnalogValueCallbackThreshold(char, int, int)} is reached.
	 * The parameter is the analog value of the sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletDistanceIR#setDebouncePeriod(long)}.
	 */
	public interface AnalogValueReachedListener {
		public void analogValueReached(int value);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletDistanceIR(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DISTANCE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ANALOG_VALUE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_SAMPLING_POINT)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_SAMPLING_POINT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DISTANCE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DISTANCE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ANALOG_VALUE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ANALOG_VALUE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DISTANCE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DISTANCE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ANALOG_VALUE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ANALOG_VALUE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_DISTANCE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_ANALOG_VALUE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_DISTANCE_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_ANALOG_VALUE_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_DISTANCE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int distance = IPConnection.unsignedShort(bb.getShort());

				for(DistanceListener listener: listenerDistance) {
					listener.distance(distance);
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

		callbacks[CALLBACK_DISTANCE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int distance = IPConnection.unsignedShort(bb.getShort());

				for(DistanceReachedListener listener: listenerDistanceReached) {
					listener.distanceReached(distance);
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
	 * Returns the distance measured by the sensor. The value is in mm and possible
	 * distance ranges are 40 to 300, 100 to 800 and 200 to 1500, depending on the
	 * selected IR sensor.
	 * 
	 * If you want to get the distance periodically, it is recommended to use the
	 * listener {@link BrickletDistanceIR.DistanceListener} and set the period with 
	 * {@link BrickletDistanceIR#setDistanceCallbackPeriod(long)}.
	 */
	public int getDistance() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_DISTANCE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int distance = IPConnection.unsignedShort(bb.getShort());

		return distance;
	}

	/**
	 * Returns the value as read by a 12-bit analog-to-digital converter.
	 * The value is between 0 and 4095.
	 * 
	 * \note
	 *  The value returned by {@link BrickletDistanceIR#getDistance()} is averaged over several samples
	 *  to yield less noise, while {@link BrickletDistanceIR#getAnalogValue()} gives back raw
	 *  unfiltered analog values. The only reason to use {@link BrickletDistanceIR#getAnalogValue()} is,
	 *  if you need the full resolution of the analog-to-digital converter.
	 * 
	 * If you want the analog value periodically, it is recommended to use the 
	 * listener {@link BrickletDistanceIR.AnalogValueListener} and set the period with 
	 * {@link BrickletDistanceIR#setAnalogValueCallbackPeriod(long)}.
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
	 * Sets a sampling point value to a specific position of the lookup table.
	 * The lookup table comprises 128 equidistant analog values with
	 * corresponding distances.
	 * 
	 * If you measure a distance of 50cm at the analog value 2048, you
	 * should call this function with (64, 5000). The utilized analog-to-digital
	 * converter has a resolution of 12 bit. With 128 sampling points on the
	 * whole range, this means that every sampling point has a size of 32
	 * analog values. Thus the analog value 2048 has the corresponding sampling
	 * point 64 = 2048/32.
	 * 
	 * Sampling points are saved on the EEPROM of the Distance IR Bricklet and
	 * loaded again on startup.
	 * 
	 * \note
	 *  An easy way to calibrate the sampling points of the Distance IR Bricklet is
	 *  implemented in the Brick Viewer. If you want to calibrate your Bricklet it is
	 *  highly recommended to use this implementation.
	 */
	public void setSamplingPoint(short position, int distance) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)11, FUNCTION_SET_SAMPLING_POINT, this);
		bb.put((byte)position);
		bb.putShort((short)distance);

		sendRequest(bb.array());
	}

	/**
	 * Returns the distance to a sampling point position as set by
	 * {@link BrickletDistanceIR#setSamplingPoint(short, int)}.
	 */
	public int getSamplingPoint(short position) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_GET_SAMPLING_POINT, this);
		bb.put((byte)position);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int distance = IPConnection.unsignedShort(bb.getShort());

		return distance;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletDistanceIR.DistanceListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletDistanceIR.DistanceListener} is only triggered if the distance has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setDistanceCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_DISTANCE_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletDistanceIR#setDistanceCallbackPeriod(long)}.
	 */
	public long getDistanceCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_DISTANCE_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletDistanceIR.AnalogValueListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletDistanceIR.AnalogValueListener} is only triggered if the analog value has changed since the
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
	 * Returns the period as set by {@link BrickletDistanceIR#setAnalogValueCallbackPeriod(long)}.
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
	 * Sets the thresholds for the {@link BrickletDistanceIR.DistanceReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the distance is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the distance is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the distance is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the distance is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setDistanceCallbackThreshold(char option, short min, short max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)13, FUNCTION_SET_DISTANCE_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putShort(min);
		bb.putShort(max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletDistanceIR#setDistanceCallbackThreshold(char, short, short)}.
	 */
	public DistanceCallbackThreshold getDistanceCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_DISTANCE_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		DistanceCallbackThreshold obj = new DistanceCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getShort());
		obj.max = (bb.getShort());

		return obj;
	}

	/**
	 * Sets the thresholds for the {@link BrickletDistanceIR.AnalogValueReachedListener} listener. 
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
	 * Returns the threshold as set by {@link BrickletDistanceIR#setAnalogValueCallbackThreshold(char, int, int)}.
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
	 * * {@link BrickletDistanceIR.DistanceReachedListener},
	 * * {@link BrickletDistanceIR.AnalogValueReachedListener}
	 * 
	 * are triggered, if the thresholds
	 * 
	 * * {@link BrickletDistanceIR#setDistanceCallbackThreshold(char, short, short)},
	 * * {@link BrickletDistanceIR#setAnalogValueCallbackThreshold(char, int, int)}
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
	 * Returns the debounce period as set by {@link BrickletDistanceIR#setDebouncePeriod(long)}.
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
	 * Adds a Distance listener.
	 */
	public void addDistanceListener(DistanceListener listener) {
		listenerDistance.add(listener);
	}

	/**
	 * Removes a Distance listener.
	 */
	public void removeDistanceListener(DistanceListener listener) {
		listenerDistance.remove(listener);
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
	 * Adds a DistanceReached listener.
	 */
	public void addDistanceReachedListener(DistanceReachedListener listener) {
		listenerDistanceReached.add(listener);
	}

	/**
	 * Removes a DistanceReached listener.
	 */
	public void removeDistanceReachedListener(DistanceReachedListener listener) {
		listenerDistanceReached.remove(listener);
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