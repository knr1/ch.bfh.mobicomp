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
 * Device for controlling up to 4 optically coupled digital outputs
 */
public class BrickletIndustrialDigitalOut4 extends Device {
	public final static int DEVICE_IDENTIFIER = 224;

	public final static byte FUNCTION_SET_VALUE = (byte)1;
	public final static byte FUNCTION_GET_VALUE = (byte)2;
	public final static byte FUNCTION_SET_MONOFLOP = (byte)3;
	public final static byte FUNCTION_GET_MONOFLOP = (byte)4;
	public final static byte FUNCTION_SET_GROUP = (byte)5;
	public final static byte FUNCTION_GET_GROUP = (byte)6;
	public final static byte FUNCTION_GET_AVAILABLE_FOR_GROUP = (byte)7;
	public final static byte CALLBACK_MONOFLOP_DONE = (byte)8;
	public final static byte FUNCTION_SET_SELECTED_VALUES = (byte)9;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;


	private List<MonoflopDoneListener> listenerMonoflopDone = new CopyOnWriteArrayList<MonoflopDoneListener>();

	public class Monoflop {
		public short pin;
		public int value;
		public long time;
		public long timeRemaining;

		public String toString() {
			return "[" + "pin = " + pin + ", " + "value = " + value + ", " + "time = " + time + ", " + "timeRemaining = " + timeRemaining + "]";
		}
	}

	/**
	 * This listener is triggered whenever a monoflop timer reaches 0. The
	 * parameters contain the involved pins and the current value of the pins
	 * (the value after the monoflop).
	 */
	public interface MonoflopDoneListener {
		public void monoflopDone(int selectionMask, int valueMask);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletIndustrialDigitalOut4(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_VALUE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_VALUE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_MONOFLOP)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MONOFLOP)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_GROUP)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_GROUP)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_AVAILABLE_FOR_GROUP)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_SELECTED_VALUES)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_MONOFLOP_DONE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_MONOFLOP_DONE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int selectionMask = IPConnection.unsignedShort(bb.getShort());
				int valueMask = IPConnection.unsignedShort(bb.getShort());

				for(MonoflopDoneListener listener: listenerMonoflopDone) {
					listener.monoflopDone(selectionMask, valueMask);
				}
			}
		};
	}

	/**
	 * Sets the output value with a bitmask. The bitmask
	 * is 16 bit long, *true* refers to high and *false* refers to 
	 * low.
	 * 
	 * For example: The value 3 or 0b0011 will turn pins 0-1 high and the other pins
	 * low.
	 * 
	 * If no groups are used (see {@link BrickletIndustrialDigitalOut4#setGroup(char[])}), the pins correspond to the
	 * markings on the Digital Out 4 Bricklet.
	 * 
	 * If groups are used, the pins correspond to the element in the group.
	 * Element 1 in the group will get pins 0-3, element 2 pins 4-7, element 3
	 * pins 8-11 and element 4 pins 12-15.
	 */
	public void setValue(int valueMask) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_VALUE, this);
		bb.putShort((short)valueMask);

		sendRequest(bb.array());
	}

	/**
	 * Returns the bitmask as set by {@link BrickletIndustrialDigitalOut4#setValue(int)}.
	 */
	public int getValue() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_VALUE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int valueMask = IPConnection.unsignedShort(bb.getShort());

		return valueMask;
	}

	/**
	 * Configures a monoflop of the pins specified by the first parameter
	 * bitmask.
	 * 
	 * The second parameter is a bitmask with the desired value of the specified
	 * pins (*true* means high and *false* means low).
	 * 
	 * The third parameter indicates the time (in ms) that the pins should hold
	 * the value.
	 * 
	 * If this function is called with the parameters (9, 1, 1500) or
	 * (0b1001, 0b0001, 1500): Pin 0 will get high and pin 3 will get low. In 1.5s
	 * pin 0 will get low and pin 3 will get high again.
	 * 
	 * A monoflop can be used as a fail-safe mechanism. For example: Lets assume you
	 * have a RS485 bus and a Digital Out 4 Bricklet connected to one of the slave
	 * stacks. You can now call this function every second, with a time parameter
	 * of two seconds and pin 0 high. Pin 0 will be high all the time. If now
	 * the RS485 connection is lost, then pin 0 will turn low in at most two seconds.
	 */
	public void setMonoflop(int selectionMask, int valueMask, long time) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)16, FUNCTION_SET_MONOFLOP, this);
		bb.putShort((short)selectionMask);
		bb.putShort((short)valueMask);
		bb.putInt((int)time);

		sendRequest(bb.array());
	}

	/**
	 * Returns (for the given pin) the current value and the time as set by
	 * {@link BrickletIndustrialDigitalOut4#setMonoflop(int, int, long)} as well as the remaining time until the value flips.
	 * 
	 * If the timer is not running currently, the remaining time will be returned
	 * as 0.
	 */
	public Monoflop getMonoflop(short pin) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_GET_MONOFLOP, this);
		bb.put((byte)pin);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Monoflop obj = new Monoflop();
		obj.value = IPConnection.unsignedShort(bb.getShort());
		obj.time = IPConnection.unsignedInt(bb.getInt());
		obj.timeRemaining = IPConnection.unsignedInt(bb.getInt());

		return obj;
	}

	/**
	 * Sets a group of Digital Out 4 Bricklets that should work together. You can
	 * find Bricklets that can be grouped together with {@link BrickletIndustrialDigitalOut4#getAvailableForGroup()}.
	 * 
	 * The group consists of 4 elements. Element 1 in the group will get pins 0-3,
	 * element 2 pins 4-7, element 3 pins 8-11 and element 4 pins 12-15.
	 * 
	 * Each element can either be one of the ports ('a' to 'd') or 'n' if it should
	 * not be used.
	 * 
	 * For example: If you have two Digital Out 4 Bricklets connected to port A and
	 * port B respectively, you could call with ``['a', 'b', 'n', 'n']``.
	 * 
	 * Now the pins on the Digital Out 4 on port A are assigned to 0-3 and the
	 * pins on the Digital Out 4 on port B are assigned to 4-7. It is now possible
	 * to call {@link BrickletIndustrialDigitalOut4#setValue(int)} and control two Bricklets at the same time.
	 */
	public void setGroup(char[] group) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_GROUP, this);
		for(int i = 0; i < 4; i++) {
			bb.put((byte)group[i]);
		}


		sendRequest(bb.array());
	}

	/**
	 * Returns the group as set by {@link BrickletIndustrialDigitalOut4#setGroup(char[])}
	 */
	public char[] getGroup() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_GROUP, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		char[] group = new char[4];
		for(int i = 0; i < 4; i++) {
			group[i] = (char)(bb.get());
		}


		return group;
	}

	/**
	 * Returns a bitmask of ports that are available for grouping. For example the
	 * value 5 or 0b0101 means: Port A and port C are connected to Bricklets that
	 * can be grouped together.
	 */
	public short getAvailableForGroup() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_AVAILABLE_FOR_GROUP, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short available = IPConnection.unsignedByte(bb.get());

		return available;
	}

	/**
	 * Sets the output value with a bitmask, according to the selection mask.
	 * The bitmask is 16 bit long, *true* refers to high and *false* refers to 
	 * low.
	 * 
	 * For example: The values (3, 1) or (0b0011, 0b0001) will turn pin 0 high, pin 1
	 * low the other pins remain untouched.
	 * 
	 * If no groups are used (see {@link BrickletIndustrialDigitalOut4#setGroup(char[])}), the pins correspond to the
	 * markings on the Digital Out 4 Bricklet.
	 * 
	 * If groups are used, the pins correspond to the element in the group.
	 * Element 1 in the group will get pins 0-3, element 2 pins 4-7, element 3
	 * pins 8-11 and element 4 pins 12-15.
	 * 
	 * .. versionadded:: 2.0.0~(Plugin)
	 */
	public void setSelectedValues(int selectionMask, int valueMask) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_SELECTED_VALUES, this);
		bb.putShort((short)selectionMask);
		bb.putShort((short)valueMask);

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