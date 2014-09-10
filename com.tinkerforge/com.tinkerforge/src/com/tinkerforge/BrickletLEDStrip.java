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
 * Device to control up to 320 RGB LEDs
 */
public class BrickletLEDStrip extends Device {
	public final static int DEVICE_IDENTIFIER = 231;

	public final static byte FUNCTION_SET_RGB_VALUES = (byte)1;
	public final static byte FUNCTION_GET_RGB_VALUES = (byte)2;
	public final static byte FUNCTION_SET_FRAME_DURATION = (byte)3;
	public final static byte FUNCTION_GET_FRAME_DURATION = (byte)4;
	public final static byte FUNCTION_GET_SUPPLY_VOLTAGE = (byte)5;
	public final static byte CALLBACK_FRAME_RENDERED = (byte)6;
	public final static byte FUNCTION_SET_CLOCK_FREQUENCY = (byte)7;
	public final static byte FUNCTION_GET_CLOCK_FREQUENCY = (byte)8;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;


	private List<FrameRenderedListener> listenerFrameRendered = new CopyOnWriteArrayList<FrameRenderedListener>();

	public class RGBValues {
		public int index;
		public short length;
		public short[] r = new short[16];
		public short[] g = new short[16];
		public short[] b = new short[16];

		public String toString() {
			return "[" + "index = " + index + ", " + "length = " + length + ", " + "r = " + Arrays.toString(r) + ", " + "g = " + Arrays.toString(g) + ", " + "b = " + Arrays.toString(b) + "]";
		}
	}

	/**
	 * This listener is triggered directly after a new frame is rendered.
	 * 
	 * You should send the data for the next frame directly after this listener
	 * was triggered.
	 * 
	 * For an explanation of the general approach see {@link BrickletLEDStrip#setRGBValues(int, short, short[], short[], short[])}.
	 */
	public interface FrameRenderedListener {
		public void frameRendered(int length);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletLEDStrip(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 1;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_RGB_VALUES)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_RGB_VALUES)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_FRAME_DURATION)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_FRAME_DURATION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_SUPPLY_VOLTAGE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_CLOCK_FREQUENCY)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CLOCK_FREQUENCY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_FRAME_RENDERED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_FRAME_RENDERED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int length = IPConnection.unsignedShort(bb.getShort());

				for(FrameRenderedListener listener: listenerFrameRendered) {
					listener.frameRendered(length);
				}
			}
		};
	}

	/**
	 * Sets the *rgb* values for the LEDs with the given *length* starting 
	 * from *index*.
	 * 
	 * The maximum length is 16, the index goes from 0 to 319 and the rgb values
	 * have 8 bits each.
	 * 
	 * Example: If you set
	 * 
	 * * index to 5,
	 * * length to 3,
	 * * r to [255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
	 * * g to [0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0] and
	 * * b to [0, 0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
	 * 
	 * the LED with index 5 will be red, 6 will be green and 7 will be blue.
	 * 
	 * The colors will be transfered to actual LEDs when the next
	 * frame duration ends, see {@link BrickletLEDStrip#setFrameDuration(int)}.
	 * 
	 * Generic approach: 
	 * 
	 * * Set the frame duration to a value that represents
	 *   the number of frames per second you want to achieve. 
	 * * Set all of the LED colors for one frame.
	 * * Wait for the {@link BrickletLEDStrip.FrameRenderedListener} listener.
	 * * Set all of the LED colors for next frame.
	 * * Wait for the {@link BrickletLEDStrip.FrameRenderedListener} listener.
	 * * and so on.
	 * 
	 * This approach ensures that you can change the LED colors with
	 * a fixed frame rate.
	 * 
	 * The actual number of controllable LEDs depends on the number of free
	 * Bricklet ports. See :ref:`here <led_strip_bricklet_ram_constraints>` for more
	 * information. A call of {@link BrickletLEDStrip#setRGBValues(int, short, short[], short[], short[])} with index + length above the
	 * bounds is ignored completely.
	 */
	public void setRGBValues(int index, short length, short[] r, short[] g, short[] b) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)59, FUNCTION_SET_RGB_VALUES, this);
		bb.putShort((short)index);
		bb.put((byte)length);
		for(int i = 0; i < 16; i++) {
			bb.put((byte)r[i]);
		}

		for(int i = 0; i < 16; i++) {
			bb.put((byte)g[i]);
		}

		for(int i = 0; i < 16; i++) {
			bb.put((byte)b[i]);
		}


		sendRequest(bb.array());
	}

	/**
	 * Returns the rgb with the given *length* starting from the
	 * given *index*.
	 * 
	 * The values are the last values that were set by {@link BrickletLEDStrip#setRGBValues(int, short, short[], short[], short[])}.
	 */
	public RGBValues getRGBValues(int index, short length) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)11, FUNCTION_GET_RGB_VALUES, this);
		bb.putShort((short)index);
		bb.put((byte)length);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		RGBValues obj = new RGBValues();
		for(int i = 0; i < 16; i++) {
			obj.r[i] = IPConnection.unsignedByte(bb.get());
		}

		for(int i = 0; i < 16; i++) {
			obj.g[i] = IPConnection.unsignedByte(bb.get());
		}

		for(int i = 0; i < 16; i++) {
			obj.b[i] = IPConnection.unsignedByte(bb.get());
		}


		return obj;
	}

	/**
	 * Sets the frame duration in ms.
	 * 
	 * Example: If you want to achieve 20 frames per second, you should
	 * set the frame duration to 50ms (50ms * 20 = 1 second). 
	 * 
	 * For an explanation of the general approach see {@link BrickletLEDStrip#setRGBValues(int, short, short[], short[], short[])}.
	 * 
	 * Default value: 100ms (10 frames per second).
	 */
	public void setFrameDuration(int duration) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_FRAME_DURATION, this);
		bb.putShort((short)duration);

		sendRequest(bb.array());
	}

	/**
	 * Returns the frame duration as set by {@link BrickletLEDStrip#setFrameDuration(int)}.
	 */
	public int getFrameDuration() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_FRAME_DURATION, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int duration = IPConnection.unsignedShort(bb.getShort());

		return duration;
	}

	/**
	 * Returns the current supply voltage of the LEDs. The voltage is given in mV.
	 */
	public int getSupplyVoltage() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_SUPPLY_VOLTAGE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int voltage = IPConnection.unsignedShort(bb.getShort());

		return voltage;
	}

	/**
	 * Sets the frequency of the clock in Hz. The range is 10000Hz (10kHz) up to
	 * 2000000Hz (2MHz).
	 * 
	 * The Bricklet will choose the nearest achievable frequency, which may
	 * be off by a few Hz. You can get the exact frequency that is used by
	 * calling {@link BrickletLEDStrip#getClockFrequency()}.
	 * 
	 * If you have problems with flickering LEDs, they may be bits flipping. You
	 * can fix this by either making the connection between the LEDs and the
	 * Bricklet shorter or by reducing the frequency.
	 * 
	 * With a decreasing frequency your maximum frames per second will decrease
	 * too.
	 * 
	 * The default value is 1.66MHz.
	 * 
	 * \note
	 *  The frequency in firmware version 2.0.0 is fixed at 2MHz.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public void setClockFrequency(long frequency) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_CLOCK_FREQUENCY, this);
		bb.putInt((int)frequency);

		sendRequest(bb.array());
	}

	/**
	 * Returns the currently used clock frequency.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public long getClockFrequency() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_CLOCK_FREQUENCY, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long frequency = IPConnection.unsignedInt(bb.getInt());

		return frequency;
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
	 * Adds a FrameRendered listener.
	 */
	public void addFrameRenderedListener(FrameRenderedListener listener) {
		listenerFrameRendered.add(listener);
	}

	/**
	 * Removes a FrameRendered listener.
	 */
	public void removeFrameRenderedListener(FrameRenderedListener listener) {
		listenerFrameRendered.remove(listener);
	}
}