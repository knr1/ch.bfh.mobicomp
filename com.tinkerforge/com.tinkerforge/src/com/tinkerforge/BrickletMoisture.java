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
 * Device for sensing Moisture
 */
public class BrickletMoisture extends Device {
	public final static int DEVICE_IDENTIFIER = 232;

	public final static byte FUNCTION_GET_MOISTURE_VALUE = (byte)1;
	public final static byte FUNCTION_SET_MOISTURE_CALLBACK_PERIOD = (byte)2;
	public final static byte FUNCTION_GET_MOISTURE_CALLBACK_PERIOD = (byte)3;
	public final static byte FUNCTION_SET_MOISTURE_CALLBACK_THRESHOLD = (byte)4;
	public final static byte FUNCTION_GET_MOISTURE_CALLBACK_THRESHOLD = (byte)5;
	public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
	public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
	public final static byte CALLBACK_MOISTURE = (byte)8;
	public final static byte CALLBACK_MOISTURE_REACHED = (byte)9;
	public final static byte FUNCTION_SET_MOVING_AVERAGE = (byte)10;
	public final static byte FUNCTION_GET_MOVING_AVERAGE = (byte)11;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static char THRESHOLD_OPTION_OFF = 'x';
	public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
	public final static char THRESHOLD_OPTION_INSIDE = 'i';
	public final static char THRESHOLD_OPTION_SMALLER = '<';
	public final static char THRESHOLD_OPTION_GREATER = '>';

	private List<MoistureListener> listenerMoisture = new CopyOnWriteArrayList<MoistureListener>();
	private List<MoistureReachedListener> listenerMoistureReached = new CopyOnWriteArrayList<MoistureReachedListener>();

	public class MoistureCallbackThreshold {
		public char option;
		public int min;
		public int max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletMoisture#setMoistureCallbackPeriod(long)}. The parameter is the moisture value
	 * of the sensor.
	 * 
	 * {@link BrickletMoisture.MoistureListener} is only triggered if the moisture value has changed since the
	 * last triggering.
	 */
	public interface MoistureListener {
		public void moisture(int moisture);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletMoisture#setMoistureCallbackThreshold(char, int, int)} is reached.
	 * The parameter is the moisture value of the sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletMoisture#setDebouncePeriod(long)}.
	 */
	public interface MoistureReachedListener {
		public void moistureReached(int moisture);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletMoisture(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MOISTURE_VALUE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_MOISTURE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MOISTURE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_MOISTURE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MOISTURE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_MOVING_AVERAGE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MOVING_AVERAGE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_MOISTURE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_MOISTURE_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_MOISTURE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int moisture = IPConnection.unsignedShort(bb.getShort());

				for(MoistureListener listener: listenerMoisture) {
					listener.moisture(moisture);
				}
			}
		};

		callbacks[CALLBACK_MOISTURE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int moisture = IPConnection.unsignedShort(bb.getShort());

				for(MoistureReachedListener listener: listenerMoistureReached) {
					listener.moistureReached(moisture);
				}
			}
		};
	}

	/**
	 * Returns the current moisture value. The value has a range of
	 * 0 to 4095. A small value corresponds to little moisture, a big
	 * value corresponds to much moisture.
	 * 
	 * If you want to get the moisture value periodically, it is recommended 
	 * to use the listener {@link BrickletMoisture.MoistureListener} and set the period with 
	 * {@link BrickletMoisture#setMoistureCallbackPeriod(long)}.
	 */
	public int getMoistureValue() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_MOISTURE_VALUE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int moisture = IPConnection.unsignedShort(bb.getShort());

		return moisture;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletMoisture.MoistureListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletMoisture.MoistureListener} is only triggered if the moisture value has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setMoistureCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_MOISTURE_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletMoisture#setMoistureCallbackPeriod(long)}.
	 */
	public long getMoistureCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_MOISTURE_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the thresholds for the {@link BrickletMoisture.MoistureReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the moisture value is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the moisture value is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the moisture value is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the moisture value is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setMoistureCallbackThreshold(char option, int min, int max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)13, FUNCTION_SET_MOISTURE_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putShort((short)min);
		bb.putShort((short)max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletMoisture#setMoistureCallbackThreshold(char, int, int)}.
	 */
	public MoistureCallbackThreshold getMoistureCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_MOISTURE_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		MoistureCallbackThreshold obj = new MoistureCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = IPConnection.unsignedShort(bb.getShort());
		obj.max = IPConnection.unsignedShort(bb.getShort());

		return obj;
	}

	/**
	 * Sets the period in ms with which the threshold listener
	 * 
	 * * {@link BrickletMoisture.MoistureReachedListener}
	 * 
	 * is triggered, if the threshold
	 * 
	 * * {@link BrickletMoisture#setMoistureCallbackThreshold(char, int, int)}
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
	 * Returns the debounce period as set by {@link BrickletMoisture#setDebouncePeriod(long)}.
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
	 * Sets the length of a `moving averaging <http://en.wikipedia.org/wiki/Moving_average>`__ 
	 * for the moisture value.
	 * 
	 * Setting the length to 0 will turn the averaging completely off. With less
	 * averaging, there is more noise on the data.
	 * 
	 * The range for the averaging is 0-100.
	 * 
	 * The default value is 100.
	 */
	public void setMovingAverage(short average) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_SET_MOVING_AVERAGE, this);
		bb.put((byte)average);

		sendRequest(bb.array());
	}

	/**
	 * Returns the length moving average as set by {@link BrickletMoisture#setMovingAverage(short)}.
	 */
	public short getMovingAverage() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_MOVING_AVERAGE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short average = IPConnection.unsignedByte(bb.get());

		return average;
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
	 * Adds a Moisture listener.
	 */
	public void addMoistureListener(MoistureListener listener) {
		listenerMoisture.add(listener);
	}

	/**
	 * Removes a Moisture listener.
	 */
	public void removeMoistureListener(MoistureListener listener) {
		listenerMoisture.remove(listener);
	}

	/**
	 * Adds a MoistureReached listener.
	 */
	public void addMoistureReachedListener(MoistureReachedListener listener) {
		listenerMoistureReached.add(listener);
	}

	/**
	 * Removes a MoistureReached listener.
	 */
	public void removeMoistureReachedListener(MoistureReachedListener listener) {
		listenerMoistureReached.remove(listener);
	}
}