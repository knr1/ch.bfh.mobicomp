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
 * Device for sensing tilt and vibration
 */
public class BrickletTilt extends Device {
	public final static int DEVICE_IDENTIFIER = 239;

	public final static byte FUNCTION_GET_TILT_STATE = (byte)1;
	public final static byte FUNCTION_ENABLE_TILT_STATE_CALLBACK = (byte)2;
	public final static byte FUNCTION_DISABLE_TILT_STATE_CALLBACK = (byte)3;
	public final static byte FUNCTION_IS_TILT_STATE_CALLBACK_ENABLED = (byte)4;
	public final static byte CALLBACK_TILT_STATE = (byte)5;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static short TILT_STATE_CLOSED = (short)0;
	public final static short TILT_STATE_OPEN = (short)1;
	public final static short TILT_STATE_CLOSED_VIBRATING = (short)2;

	private List<TiltStateListener> listenerTiltState = new CopyOnWriteArrayList<TiltStateListener>();

	/**
	 * This listener provides the current tilt state. It is called every time the
	 * state changes.
	 * 
	 * See {@link BrickletTilt#getTiltState()} for a description of the states.
	 */
	public interface TiltStateListener {
		public void tiltState(short state);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletTilt(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_TILT_STATE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_ENABLE_TILT_STATE_CALLBACK)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_DISABLE_TILT_STATE_CALLBACK)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_IS_TILT_STATE_CALLBACK_ENABLED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_TILT_STATE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_TILT_STATE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short state = IPConnection.unsignedByte(bb.get());

				for(TiltStateListener listener: listenerTiltState) {
					listener.tiltState(state);
				}
			}
		};
	}

	/**
	 * Returns the current tilt state. The state can either be
	 * 
	 * * 0 = Closed: The ball in the tilt switch closes the circuit.
	 * * 1 = Open: The ball in the tilt switch does not close the circuit.
	 * * 2 = Closed Vibrating: The tilt switch is in motion (rapid change between open and close).
	 * 
	 * .. image:: /Images/Bricklets/bricklet_tilt_mechanics.jpg
	 *    :scale: 100 %
	 *    :alt: Tilt states
	 *    :align: center
	 *    :target: ../../_images/Bricklets/bricklet_tilt_mechanics.jpg
	 */
	public short getTiltState() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_TILT_STATE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short state = IPConnection.unsignedByte(bb.get());

		return state;
	}

	/**
	 * Enables the {@link BrickletTilt.TiltStateListener} listener.
	 */
	public void enableTiltStateCallback() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_ENABLE_TILT_STATE_CALLBACK, this);

		sendRequest(bb.array());
	}

	/**
	 * Disables the {@link BrickletTilt.TiltStateListener} listener.
	 */
	public void disableTiltStateCallback() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_DISABLE_TILT_STATE_CALLBACK, this);

		sendRequest(bb.array());
	}

	/**
	 * Returns *true* if the {@link BrickletTilt.TiltStateListener} listener is enabled.
	 */
	public boolean isTiltStateCallbackEnabled() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_IS_TILT_STATE_CALLBACK_ENABLED, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean enabled = (bb.get()) != 0;

		return enabled;
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
	 * Adds a TiltState listener.
	 */
	public void addTiltStateListener(TiltStateListener listener) {
		listenerTiltState.add(listener);
	}

	/**
	 * Removes a TiltState listener.
	 */
	public void removeTiltStateListener(TiltStateListener listener) {
		listenerTiltState.remove(listener);
	}
}