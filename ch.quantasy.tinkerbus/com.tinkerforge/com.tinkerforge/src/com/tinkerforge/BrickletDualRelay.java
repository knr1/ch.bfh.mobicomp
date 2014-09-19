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
 * Device for controlling two relays
 */
public class BrickletDualRelay extends Device {
	public final static int DEVICE_IDENTIFIER = 26;

	public final static byte FUNCTION_SET_STATE = (byte)1;
	public final static byte FUNCTION_GET_STATE = (byte)2;
	public final static byte FUNCTION_SET_MONOFLOP = (byte)3;
	public final static byte FUNCTION_GET_MONOFLOP = (byte)4;
	public final static byte CALLBACK_MONOFLOP_DONE = (byte)5;
	public final static byte FUNCTION_SET_SELECTED_STATE = (byte)6;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;


	private List<MonoflopDoneListener> listenerMonoflopDone = new CopyOnWriteArrayList<MonoflopDoneListener>();

	public class State {
		public boolean relay1;
		public boolean relay2;

		public String toString() {
			return "[" + "relay1 = " + relay1 + ", " + "relay2 = " + relay2 + "]";
		}
	}

	public class Monoflop {
		public short relay;
		public boolean state;
		public long time;
		public long timeRemaining;

		public String toString() {
			return "[" + "relay = " + relay + ", " + "state = " + state + ", " + "time = " + time + ", " + "timeRemaining = " + timeRemaining + "]";
		}
	}

	/**
	 * This listener is triggered whenever a monoflop timer reaches 0. The 
	 * parameter contain the relay (1 or 2) and the current state of the relay 
	 * (the state after the monoflop).
	 * 
	 * .. versionadded:: 1.1.1~(Plugin)
	 */
	public interface MonoflopDoneListener {
		public void monoflopDone(short relay, boolean state);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletDualRelay(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_STATE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_STATE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_MONOFLOP)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MONOFLOP)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_SELECTED_STATE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_MONOFLOP_DONE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_MONOFLOP_DONE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short relay = IPConnection.unsignedByte(bb.get());
				boolean state = (bb.get()) != 0;

				for(MonoflopDoneListener listener: listenerMonoflopDone) {
					listener.monoflopDone(relay, state);
				}
			}
		};
	}

	/**
	 * Sets the state of the relays, *true* means on and *false* means off. 
	 * For example: (true, false) turns relay 1 on and relay 2 off.
	 * 
	 * If you just want to set one of the relays and don't know the current state
	 * of the other relay, you can get the state with {@link BrickletDualRelay#getState()} or you
	 * can use {@link BrickletDualRelay#setSelectedState(short, boolean)}.
	 * 
	 * Running monoflop timers will be overwritten if this function is called.
	 * 
	 * The default value is (*false*, *false*).
	 */
	public void setState(boolean relay1, boolean relay2) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_STATE, this);
		bb.put((byte)(relay1 ? 1 : 0));
		bb.put((byte)(relay2 ? 1 : 0));

		sendRequest(bb.array());
	}

	/**
	 * Returns the state of the relays, *true* means on and *false* means off.
	 */
	public State getState() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_STATE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		State obj = new State();
		obj.relay1 = (bb.get()) != 0;
		obj.relay2 = (bb.get()) != 0;

		return obj;
	}

	/**
	 * The first parameter can be 1 or 2 (relay 1 or relay 2). The second parameter 
	 * is the desired state of the relay (*true* means on and *false* means off).
	 * The third parameter indicates the time (in ms) that the relay should hold 
	 * the state.
	 * 
	 * If this function is called with the parameters (1, true, 1500):
	 * Relay 1 will turn on and in 1.5s it will turn off again.
	 * 
	 * A monoflop can be used as a failsafe mechanism. For example: Lets assume you 
	 * have a RS485 bus and a Dual Relay Bricklet connected to one of the slave 
	 * stacks. You can now call this function every second, with a time parameter
	 * of two seconds. The relay will be on all the time. If now the RS485 
	 * connection is lost, the relay will turn off in at most two seconds.
	 * 
	 * .. versionadded:: 1.1.1~(Plugin)
	 */
	public void setMonoflop(short relay, boolean state, long time) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)14, FUNCTION_SET_MONOFLOP, this);
		bb.put((byte)relay);
		bb.put((byte)(state ? 1 : 0));
		bb.putInt((int)time);

		sendRequest(bb.array());
	}

	/**
	 * Returns (for the given relay) the current state and the time as set by 
	 * {@link BrickletDualRelay#setMonoflop(short, boolean, long)} as well as the remaining time until the state flips.
	 * 
	 * If the timer is not running currently, the remaining time will be returned
	 * as 0.
	 * 
	 * .. versionadded:: 1.1.1~(Plugin)
	 */
	public Monoflop getMonoflop(short relay) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_GET_MONOFLOP, this);
		bb.put((byte)relay);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Monoflop obj = new Monoflop();
		obj.state = (bb.get()) != 0;
		obj.time = IPConnection.unsignedInt(bb.getInt());
		obj.timeRemaining = IPConnection.unsignedInt(bb.getInt());

		return obj;
	}

	/**
	 * Sets the state of the selected relay (1 or 2), *true* means on and *false* means off. 
	 * 
	 * The other relay remains untouched.
	 * 
	 * .. versionadded:: 2.0.0~(Plugin)
	 */
	public void setSelectedState(short relay, boolean state) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_SELECTED_STATE, this);
		bb.put((byte)relay);
		bb.put((byte)(state ? 1 : 0));

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
	 * Adds a MonoflopDone listener.
	 */
	public void addMonoflopDoneListener(MonoflopDoneListener listener) {
		listenerMonoflopDone.add(listener);
	}

	/**
	 * Removes a MonoflopDone listener.
	 */
	public void removeMonoflopDoneListener(MonoflopDoneListener listener) {
		listenerMonoflopDone.remove(listener);
	}
}