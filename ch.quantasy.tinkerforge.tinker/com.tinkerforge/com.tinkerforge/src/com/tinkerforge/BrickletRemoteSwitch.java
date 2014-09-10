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
 * Device that controls mains switches remotely
 */
public class BrickletRemoteSwitch extends Device {
	public final static int DEVICE_IDENTIFIER = 235;

	public final static byte FUNCTION_SWITCH_SOCKET = (byte)1;
	public final static byte FUNCTION_GET_SWITCHING_STATE = (byte)2;
	public final static byte CALLBACK_SWITCHING_DONE = (byte)3;
	public final static byte FUNCTION_SET_REPEATS = (byte)4;
	public final static byte FUNCTION_GET_REPEATS = (byte)5;
	public final static byte FUNCTION_SWITCH_SOCKET_A = (byte)6;
	public final static byte FUNCTION_SWITCH_SOCKET_B = (byte)7;
	public final static byte FUNCTION_DIM_SOCKET_B = (byte)8;
	public final static byte FUNCTION_SWITCH_SOCKET_C = (byte)9;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static short SWITCH_TO_OFF = (short)0;
	public final static short SWITCH_TO_ON = (short)1;
	public final static short SWITCHING_STATE_READY = (short)0;
	public final static short SWITCHING_STATE_BUSY = (short)1;

	private List<SwitchingDoneListener> listenerSwitchingDone = new CopyOnWriteArrayList<SwitchingDoneListener>();

	/**
	 * This listener is called whenever the switching state changes
	 * from busy to ready, see {@link BrickletRemoteSwitch#getSwitchingState()}.
	 */
	public interface SwitchingDoneListener {
		public void switchingDone();
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletRemoteSwitch(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 1;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SWITCH_SOCKET)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_SWITCHING_STATE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_REPEATS)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_REPEATS)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SWITCH_SOCKET_A)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SWITCH_SOCKET_B)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_DIM_SOCKET_B)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SWITCH_SOCKET_C)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_SWITCHING_DONE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_SWITCHING_DONE] = new CallbackListener() {
			public void callback(byte[] data) {
				for(SwitchingDoneListener listener: listenerSwitchingDone) {
					listener.switchingDone();
				}
			}
		};
	}

	/**
	 * This function is deprecated, use {@link BrickletRemoteSwitch#switchSocketA(short, short, short)} instead.
	 */
	public void switchSocket(short houseCode, short receiverCode, short switchTo) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)11, FUNCTION_SWITCH_SOCKET, this);
		bb.put((byte)houseCode);
		bb.put((byte)receiverCode);
		bb.put((byte)switchTo);

		sendRequest(bb.array());
	}

	/**
	 * Returns the current switching state. If the current state is busy, the
	 * Bricklet is currently sending a code to switch a socket. It will not
	 * accept any calls of {@link BrickletRemoteSwitch#switchSocket(short, short, short)} until the state changes to ready.
	 * 
	 * How long the switching takes is dependent on the number of repeats, see
	 * {@link BrickletRemoteSwitch#setRepeats(short)}.
	 */
	public short getSwitchingState() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_SWITCHING_STATE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short state = IPConnection.unsignedByte(bb.get());

		return state;
	}

	/**
	 * Sets the number of times the code is send when of the {@link BrickletRemoteSwitch#switchSocket(short, short, short)}
	 * functions is called. The repeats basically correspond to the amount of time
	 * that a button of the remote is pressed.
	 * 
	 * Some dimmers are controlled by the length of a button pressed,
	 * this can be simulated by increasing the repeats.
	 * 
	 * The default value is 5.
	 */
	public void setRepeats(short repeats) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_SET_REPEATS, this);
		bb.put((byte)repeats);

		sendRequest(bb.array());
	}

	/**
	 * Returns the number of repeats as set by {@link BrickletRemoteSwitch#setRepeats(short)}.
	 */
	public short getRepeats() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_REPEATS, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short repeats = IPConnection.unsignedByte(bb.get());

		return repeats;
	}

	/**
	 * To switch a type A socket you have to give the house code, receiver code and the
	 * state (on or off) you want to switch to.
	 * 
	 * The house code and receiver code have a range of 0 to 31 (5bit).
	 * 
	 * A detailed description on how you can figure out the house and receiver code
	 * can be found :ref:`here <remote_switch_bricklet_type_a_house_and_receiver_code>`.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public void switchSocketA(short houseCode, short receiverCode, short switchTo) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)11, FUNCTION_SWITCH_SOCKET_A, this);
		bb.put((byte)houseCode);
		bb.put((byte)receiverCode);
		bb.put((byte)switchTo);

		sendRequest(bb.array());
	}

	/**
	 * To switch a type B socket you have to give the address, unit and the state
	 * (on or off) you want to switch to.
	 * 
	 * The address has a range of 0 to 67108863 (26bit) and the unit has a range
	 * of 0 to 15 (4bit). To switch all devices with the same address use 255 for
	 * the unit.
	 * 
	 * A detailed description on how you can teach a socket the address and unit can
	 * be found :ref:`here <remote_switch_bricklet_type_b_address_and_unit>`.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public void switchSocketB(long address, short unit, short switchTo) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)14, FUNCTION_SWITCH_SOCKET_B, this);
		bb.putInt((int)address);
		bb.put((byte)unit);
		bb.put((byte)switchTo);

		sendRequest(bb.array());
	}

	/**
	 * To control a type B dimmer you have to give the address, unit and the
	 * dim value you want to set the dimmer to.
	 * 
	 * The address has a range of 0 to 67108863 (26bit), the unit and the dim value
	 * has a range of 0 to 15 (4bit).
	 * 
	 * A detailed description on how you can teach a dimmer the address and unit can
	 * be found :ref:`here <remote_switch_bricklet_type_b_address_and_unit>`.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public void dimSocketB(long address, short unit, short dimValue) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)14, FUNCTION_DIM_SOCKET_B, this);
		bb.putInt((int)address);
		bb.put((byte)unit);
		bb.put((byte)dimValue);

		sendRequest(bb.array());
	}

	/**
	 * To switch a type C socket you have to give the system code, device code and the
	 * state (on or off) you want to switch to.
	 * 
	 * The system code has a range of 'A' to 'P' (4bit) and the device code has a
	 * range of 1 to 16 (4bit).
	 * 
	 * A detailed description on how you can figure out the system and device code
	 * can be found :ref:`here <remote_switch_bricklet_type_c_system_and_device_code>`.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public void switchSocketC(char systemCode, short deviceCode, short switchTo) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)11, FUNCTION_SWITCH_SOCKET_C, this);
		bb.put((byte)systemCode);
		bb.put((byte)deviceCode);
		bb.put((byte)switchTo);

		sendRequest(bb.array());
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
	 * Adds a SwitchingDone listener.
	 */
	public void addSwitchingDoneListener(SwitchingDoneListener listener) {
		listenerSwitchingDone.add(listener);
	}

	/**
	 * Removes a SwitchingDone listener.
	 */
	public void removeSwitchingDoneListener(SwitchingDoneListener listener) {
		listenerSwitchingDone.remove(listener);
	}
}