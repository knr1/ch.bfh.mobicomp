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
 * Device with 12 touch sensors
 */
public class BrickletMultiTouch extends Device {
	public final static int DEVICE_IDENTIFIER = 234;

	public final static byte FUNCTION_GET_TOUCH_STATE = (byte)1;
	public final static byte FUNCTION_RECALIBRATE = (byte)2;
	public final static byte FUNCTION_SET_ELECTRODE_CONFIG = (byte)3;
	public final static byte FUNCTION_GET_ELECTRODE_CONFIG = (byte)4;
	public final static byte CALLBACK_TOUCH_STATE = (byte)5;
	public final static byte FUNCTION_SET_ELECTRODE_SENSITIVITY = (byte)6;
	public final static byte FUNCTION_GET_ELECTRODE_SENSITIVITY = (byte)7;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;


	private List<TouchStateListener> listenerTouchState = new CopyOnWriteArrayList<TouchStateListener>();

	/**
	 * Returns the current touch state, see {@link BrickletMultiTouch#getTouchState()} for
	 * information about the state.
	 * 
	 * This listener is triggered every time the touch state changes.
	 */
	public interface TouchStateListener {
		public void touchState(int state);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletMultiTouch(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_TOUCH_STATE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_RECALIBRATE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ELECTRODE_CONFIG)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ELECTRODE_CONFIG)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ELECTRODE_SENSITIVITY)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ELECTRODE_SENSITIVITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_TOUCH_STATE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_TOUCH_STATE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int state = IPConnection.unsignedShort(bb.getShort());

				for(TouchStateListener listener: listenerTouchState) {
					listener.touchState(state);
				}
			}
		};
	}

	/**
	 * Returns the current touch state. The state is given as a bitfield.
	 * 
	 * Bits 0 to 11 represent the 12 electrodes and bit 12 represents
	 * the proximity.
	 * 
	 * If an electrode is touched, the corresponding bit is true. If
	 * a hand or similar is in proximity to the electrodes, bit 12 is
	 * *true*.
	 * 
	 * Example: The state 4103 = 0x1007 = 0b1000000000111 means that
	 * electrodes 0, 1 and 2 are touched and that something is in the
	 * proximity of the electrodes.
	 * 
	 * The proximity is activated with a distance of 1-2cm. An electrode
	 * is already counted as touched if a finger is nearly touching the
	 * electrode. This means that you can put a piece of paper or foil
	 * or similar on top of a electrode to build a touch panel with
	 * a professional look.
	 */
	public int getTouchState() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_TOUCH_STATE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int state = IPConnection.unsignedShort(bb.getShort());

		return state;
	}

	/**
	 * Recalibrates the electrodes. Call this function whenever you changed
	 * or moved you electrodes.
	 */
	public void recalibrate() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_RECALIBRATE, this);

		sendRequest(bb.array());
	}

	/**
	 * Enables/disables electrodes with a bitfield (see {@link BrickletMultiTouch#getTouchState()}).
	 * 
	 * *True* enables the electrode, *false* disables the electrode. A
	 * disabled electrode will always return *false* as its state. If you
	 * don't need all electrodes you can disable the electrodes that are
	 * not needed.
	 * 
	 * It is recommended that you disable the proximity bit (bit 12) if
	 * the proximity feature is not needed. This will reduce the amount of
	 * traffic that is produced by the {@link BrickletMultiTouch.TouchStateListener} listener.
	 * 
	 * Disabling electrodes will also reduce power consumption.
	 * 
	 * Default: 8191 = 0x1FFF = 0b1111111111111 (all electrodes enabled)
	 */
	public void setElectrodeConfig(int enabledElectrodes) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_ELECTRODE_CONFIG, this);
		bb.putShort((short)enabledElectrodes);

		sendRequest(bb.array());
	}

	/**
	 * Returns the electrode configuration, as set by {@link BrickletMultiTouch#setElectrodeConfig(int)}.
	 */
	public int getElectrodeConfig() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ELECTRODE_CONFIG, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int enabledElectrodes = IPConnection.unsignedShort(bb.getShort());

		return enabledElectrodes;
	}

	/**
	 * Sets the sensitivity of the electrodes. An electrode with a high sensitivity
	 * will register a touch earlier then an electrode with a low sensitivity.
	 * 
	 * If you build a big electrode you might need to decrease the sensitivity, since
	 * the area that can be charged will get bigger. If you want to be able to
	 * activate an electrode from further away you need to increase the sensitivity.
	 * 
	 * After a new sensitivity is set, you likely want to call {@link BrickletMultiTouch#recalibrate()}
	 * to calibrate the electrodes with the newly defined sensitivity.
	 * 
	 * The valid sensitivity value range is 5-201.
	 * 
	 * The default sensitivity value is 181.
	 */
	public void setElectrodeSensitivity(short sensitivity) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_SET_ELECTRODE_SENSITIVITY, this);
		bb.put((byte)sensitivity);

		sendRequest(bb.array());
	}

	/**
	 * Returns the current sensitivity, as set by {@link BrickletMultiTouch#setElectrodeSensitivity(short)}.
	 */
	public short getElectrodeSensitivity() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ELECTRODE_SENSITIVITY, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short sensitivity = IPConnection.unsignedByte(bb.get());

		return sensitivity;
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
	 * Adds a TouchState listener.
	 */
	public void addTouchStateListener(TouchStateListener listener) {
		listenerTouchState.add(listener);
	}

	/**
	 * Removes a TouchState listener.
	 */
	public void removeTouchStateListener(TouchStateListener listener) {
		listenerTouchState.remove(listener);
	}
}