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
 * Device for sensing sound intensity
 */
public class BrickletSoundIntensity extends Device {
	public final static int DEVICE_IDENTIFIER = 238;

	public final static byte FUNCTION_GET_INTENSITY = (byte)1;
	public final static byte FUNCTION_SET_INTENSITY_CALLBACK_PERIOD = (byte)2;
	public final static byte FUNCTION_GET_INTENSITY_CALLBACK_PERIOD = (byte)3;
	public final static byte FUNCTION_SET_INTENSITY_CALLBACK_THRESHOLD = (byte)4;
	public final static byte FUNCTION_GET_INTENSITY_CALLBACK_THRESHOLD = (byte)5;
	public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)6;
	public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)7;
	public final static byte CALLBACK_INTENSITY = (byte)8;
	public final static byte CALLBACK_INTENSITY_REACHED = (byte)9;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static char THRESHOLD_OPTION_OFF = 'x';
	public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
	public final static char THRESHOLD_OPTION_INSIDE = 'i';
	public final static char THRESHOLD_OPTION_SMALLER = '<';
	public final static char THRESHOLD_OPTION_GREATER = '>';

	private List<IntensityListener> listenerIntensity = new CopyOnWriteArrayList<IntensityListener>();
	private List<IntensityReachedListener> listenerIntensityReached = new CopyOnWriteArrayList<IntensityReachedListener>();

	public class IntensityCallbackThreshold {
		public char option;
		public int min;
		public int max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletSoundIntensity#setIntensityCallbackPeriod(long)}. The parameter is the intensity of
	 * the encoder.
	 * 
	 * {@link BrickletSoundIntensity.IntensityListener} is only triggered if the intensity has changed since the
	 * last triggering.
	 */
	public interface IntensityListener {
		public void intensity(int intensity);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletSoundIntensity#setIntensityCallbackThreshold(char, int, int)} is reached.
	 * The parameter is the intensity of the encoder.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletSoundIntensity#setDebouncePeriod(long)}.
	 */
	public interface IntensityReachedListener {
		public void intensityReached(int intensity);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletSoundIntensity(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_INTENSITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_INTENSITY_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_INTENSITY_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_INTENSITY_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_INTENSITY_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_INTENSITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_INTENSITY_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_INTENSITY] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int intensity = IPConnection.unsignedShort(bb.getShort());

				for(IntensityListener listener: listenerIntensity) {
					listener.intensity(intensity);
				}
			}
		};

		callbacks[CALLBACK_INTENSITY_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int intensity = IPConnection.unsignedShort(bb.getShort());

				for(IntensityReachedListener listener: listenerIntensityReached) {
					listener.intensityReached(intensity);
				}
			}
		};
	}

	/**
	 * Returns the current sound intensity. The value has a range of
	 * 0 to 4095.
	 * 
	 * The value corresponds to the `upper envelop <http://en.wikipedia.org/wiki/Envelope_(waves)>`__
	 * of the signal of the microphone capsule.
	 * 
	 * If you want to get the intensity periodically, it is recommended to use the
	 * listener {@link BrickletSoundIntensity.IntensityListener} and set the period with 
	 * {@link BrickletSoundIntensity#setIntensityCallbackPeriod(long)}.
	 */
	public int getIntensity() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_INTENSITY, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int intensity = IPConnection.unsignedShort(bb.getShort());

		return intensity;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletSoundIntensity.IntensityListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletSoundIntensity.IntensityListener} is only triggered if the intensity has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setIntensityCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_INTENSITY_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletSoundIntensity#setIntensityCallbackPeriod(long)}.
	 */
	public long getIntensityCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_INTENSITY_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the thresholds for the {@link BrickletSoundIntensity.IntensityReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the intensity is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the intensity is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the intensity is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the intensity is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setIntensityCallbackThreshold(char option, int min, int max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)13, FUNCTION_SET_INTENSITY_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putShort((short)min);
		bb.putShort((short)max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletSoundIntensity#setIntensityCallbackThreshold(char, int, int)}.
	 */
	public IntensityCallbackThreshold getIntensityCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_INTENSITY_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		IntensityCallbackThreshold obj = new IntensityCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = IPConnection.unsignedShort(bb.getShort());
		obj.max = IPConnection.unsignedShort(bb.getShort());

		return obj;
	}

	/**
	 * Sets the period in ms with which the threshold listener
	 * 
	 * * {@link BrickletSoundIntensity.IntensityReachedListener}
	 * 
	 * is triggered, if the thresholds
	 * 
	 * * {@link BrickletSoundIntensity#setIntensityCallbackThreshold(char, int, int)}
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
	 * Returns the debounce period as set by {@link BrickletSoundIntensity#setDebouncePeriod(long)}.
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
	 * Adds a Intensity listener.
	 */
	public void addIntensityListener(IntensityListener listener) {
		listenerIntensity.add(listener);
	}

	/**
	 * Removes a Intensity listener.
	 */
	public void removeIntensityListener(IntensityListener listener) {
		listenerIntensity.remove(listener);
	}

	/**
	 * Adds a IntensityReached listener.
	 */
	public void addIntensityReachedListener(IntensityReachedListener listener) {
		listenerIntensityReached.add(listener);
	}

	/**
	 * Removes a IntensityReached listener.
	 */
	public void removeIntensityReachedListener(IntensityReachedListener listener) {
		listenerIntensityReached.remove(listener);
	}
}