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
 * Device for controling four 7-segment displays
 */
public class BrickletSegmentDisplay4x7 extends Device {
	public final static int DEVICE_IDENTIFIER = 237;

	public final static byte FUNCTION_SET_SEGMENTS = (byte)1;
	public final static byte FUNCTION_GET_SEGMENTS = (byte)2;
	public final static byte FUNCTION_START_COUNTER = (byte)3;
	public final static byte FUNCTION_GET_COUNTER_VALUE = (byte)4;
	public final static byte CALLBACK_COUNTER_FINISHED = (byte)5;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;


	private List<CounterFinishedListener> listenerCounterFinished = new CopyOnWriteArrayList<CounterFinishedListener>();

	public class Segments {
		public short[] segments = new short[4];
		public short brightness;
		public boolean colon;

		public String toString() {
			return "[" + "segments = " + Arrays.toString(segments) + ", " + "brightness = " + brightness + ", " + "colon = " + colon + "]";
		}
	}

	/**
	 * This listener is triggered when the counter (see {@link BrickletSegmentDisplay4x7#startCounter(short, short, short, long)}) is
	 * finished.
	 */
	public interface CounterFinishedListener {
		public void counterFinished();
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletSegmentDisplay4x7(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_SEGMENTS)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_SEGMENTS)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_START_COUNTER)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_COUNTER_VALUE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_COUNTER_FINISHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_COUNTER_FINISHED] = new CallbackListener() {
			public void callback(byte[] data) {
				for(CounterFinishedListener listener: listenerCounterFinished) {
					listener.counterFinished();
				}
			}
		};
	}

	/**
	 * The 7-segment display can be set with bitmaps. Every bit controls one
	 * segment:
	 * 
	 * .. image:: /Images/Bricklets/bricklet_segment_display_4x7_bit_order.png
	 *    :scale: 100 %
	 *    :alt: Bit order of one segment
	 *    :align: center
	 * 
	 * For example to set a "5" you would want to activate segments 0, 2, 3, 5 and 6.
	 * This is represented by the number 0b00110101 = 0x35 = 53.
	 * 
	 * The brightness can be set between 0 (dark) and 7 (bright). The colon
	 * parameter turns the colon of the display on or off.
	 */
	public void setSegments(short[] segments, short brightness, boolean colon) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)14, FUNCTION_SET_SEGMENTS, this);
		for(int i = 0; i < 4; i++) {
			bb.put((byte)segments[i]);
		}

		bb.put((byte)brightness);
		bb.put((byte)(colon ? 1 : 0));

		sendRequest(bb.array());
	}

	/**
	 * Returns the segment, brightness and color data as set by 
	 * {@link BrickletSegmentDisplay4x7#setSegments(short[], short, boolean)}.
	 */
	public Segments getSegments() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_SEGMENTS, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Segments obj = new Segments();
		for(int i = 0; i < 4; i++) {
			obj.segments[i] = IPConnection.unsignedByte(bb.get());
		}

		obj.brightness = IPConnection.unsignedByte(bb.get());
		obj.colon = (bb.get()) != 0;

		return obj;
	}

	/**
	 * Starts a counter with the *from* value that counts to the *to*
	 * value with the each step incremented by *increment*.
	 * The *length* of the increment is given in ms.
	 * 
	 * Example: If you set *from* to 0, *to* to 100, *increment* to 1 and
	 * *length* to 1000, a counter that goes from 0 to 100 with 1 second
	 * pause between each increment will be started.
	 * 
	 * The maximum values for *from*, *to* and *increment* is 9999, 
	 * the minimum value is -999.
	 * 
	 * You can stop the counter at every time by calling {@link BrickletSegmentDisplay4x7#setSegments(short[], short, boolean)}.
	 */
	public void startCounter(short valueFrom, short valueTo, short increment, long length) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)18, FUNCTION_START_COUNTER, this);
		bb.putShort(valueFrom);
		bb.putShort(valueTo);
		bb.putShort(increment);
		bb.putInt((int)length);

		sendRequest(bb.array());
	}

	/**
	 * Returns the counter value that is currently shown on the display.
	 * 
	 * If there is no counter running a 0 will be returned.
	 */
	public int getCounterValue() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_COUNTER_VALUE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int value = IPConnection.unsignedShort(bb.getShort());

		return value;
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
	 * Adds a CounterFinished listener.
	 */
	public void addCounterFinishedListener(CounterFinishedListener listener) {
		listenerCounterFinished.add(listener);
	}

	/**
	 * Removes a CounterFinished listener.
	 */
	public void removeCounterFinishedListener(CounterFinishedListener listener) {
		listenerCounterFinished.remove(listener);
	}
}