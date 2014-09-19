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
 * Device for controlling up to 4 optically coupled digital inputs
 */
public class BrickletIndustrialDigitalIn4 extends Device {
	public final static int DEVICE_IDENTIFIER = 223;

	public final static byte FUNCTION_GET_VALUE = (byte)1;
	public final static byte FUNCTION_SET_GROUP = (byte)2;
	public final static byte FUNCTION_GET_GROUP = (byte)3;
	public final static byte FUNCTION_GET_AVAILABLE_FOR_GROUP = (byte)4;
	public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)5;
	public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)6;
	public final static byte FUNCTION_SET_INTERRUPT = (byte)7;
	public final static byte FUNCTION_GET_INTERRUPT = (byte)8;
	public final static byte CALLBACK_INTERRUPT = (byte)9;
	public final static byte FUNCTION_GET_EDGE_COUNT = (byte)10;
	public final static byte FUNCTION_SET_EDGE_COUNT_CONFIG = (byte)11;
	public final static byte FUNCTION_GET_EDGE_COUNT_CONFIG = (byte)12;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static short EDGE_TYPE_RISING = (short)0;
	public final static short EDGE_TYPE_FALLING = (short)1;
	public final static short EDGE_TYPE_BOTH = (short)2;

	private List<InterruptListener> listenerInterrupt = new CopyOnWriteArrayList<InterruptListener>();

	public class EdgeCountConfig {
		public short pin;
		public short edgeType;
		public short debounce;

		public String toString() {
			return "[" + "pin = " + pin + ", " + "edgeType = " + edgeType + ", " + "debounce = " + debounce + "]";
		}
	}

	/**
	 * This listener is triggered whenever a change of the voltage level is detected
	 * on pins where the interrupt was activated with {@link BrickletIndustrialDigitalIn4#setInterrupt(int)}.
	 * 
	 * The values are a bitmask that specifies which interrupts occurred
	 * and the current value bitmask.
	 * 
	 * For example:
	 * 
	 * * (1, 1) or (0b0001, 0b0001) means that an interrupt on pin 0 occurred and
	 *   currently pin 0 is high and pins 1-3 are low.
	 * * (9, 14) or (0b1001, 0b1110) means that interrupts on pins 0 and 3
	 *   occurred and currently pin 0 is low and pins 1-3 are high.
	 */
	public interface InterruptListener {
		public void interrupt(int interruptMask, int valueMask);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletIndustrialDigitalIn4(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 1;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_VALUE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_GROUP)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_GROUP)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_AVAILABLE_FOR_GROUP)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_INTERRUPT)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_INTERRUPT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_EDGE_COUNT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_EDGE_COUNT_CONFIG)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_EDGE_COUNT_CONFIG)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_INTERRUPT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_INTERRUPT] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int interruptMask = IPConnection.unsignedShort(bb.getShort());
				int valueMask = IPConnection.unsignedShort(bb.getShort());

				for(InterruptListener listener: listenerInterrupt) {
					listener.interrupt(interruptMask, valueMask);
				}
			}
		};
	}

	/**
	 * Returns the input value with a bitmask. The bitmask is 16bit long, *true*
	 * refers to high and *false* refers to low.
	 * 
	 * For example: The value 3 or 0b0011 means that pins 0-1 are high and the other
	 * pins are low.
	 * 
	 * If no groups are used (see {@link BrickletIndustrialDigitalIn4#setGroup(char[])}), the pins correspond to the
	 * markings on the Digital In 4 Bricklet.
	 * 
	 * If groups are used, the pins correspond to the element in the group.
	 * Element 1 in the group will get pins 0-3, element 2 pins 4-7, element 3
	 * pins 8-11 and element 4 pins 12-15.
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
	 * Sets a group of Digital In 4 Bricklets that should work together. You can
	 * find Bricklets that can be grouped together with {@link BrickletIndustrialDigitalIn4#getAvailableForGroup()}.
	 * 
	 * The group consists of 4 elements. Element 1 in the group will get pins 0-3,
	 * element 2 pins 4-7, element 3 pins 8-11 and element 4 pins 12-15.
	 * 
	 * Each element can either be one of the ports ('a' to 'd') or 'n' if it should
	 * not be used.
	 * 
	 * For example: If you have two Digital In 4 Bricklets connected to port A and
	 * port B respectively, you could call with ``['a', 'b', 'n', 'n']``.
	 * 
	 * Now the pins on the Digital In 4 on port A are assigned to 0-3 and the
	 * pins on the Digital In 4 on port B are assigned to 4-7. It is now possible
	 * to call {@link BrickletIndustrialDigitalIn4#getValue()} and read out two Bricklets at the same time.
	 * 
	 * Changing the group configuration resets all edge counter configurations
	 * and values.
	 */
	public void setGroup(char[] group) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_GROUP, this);
		for(int i = 0; i < 4; i++) {
			bb.put((byte)group[i]);
		}


		sendRequest(bb.array());
	}

	/**
	 * Returns the group as set by {@link BrickletIndustrialDigitalIn4#setGroup(char[])}
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
	 * Sets the debounce period of the {@link BrickletIndustrialDigitalIn4.InterruptListener} listener in ms.
	 * 
	 * For example: If you set this value to 100, you will get the interrupt
	 * maximal every 100ms. This is necessary if something that bounces is
	 * connected to the Digital In 4 Bricklet, such as a button.
	 * 
	 * The default value is 100.
	 */
	public void setDebouncePeriod(long debounce) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_DEBOUNCE_PERIOD, this);
		bb.putInt((int)debounce);

		sendRequest(bb.array());
	}

	/**
	 * Returns the debounce period as set by {@link BrickletIndustrialDigitalIn4#setDebouncePeriod(long)}.
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
	 * Sets the pins on which an interrupt is activated with a bitmask.
	 * Interrupts are triggered on changes of the voltage level of the pin,
	 * i.e. changes from high to low and low to high.
	 * 
	 * For example: An interrupt bitmask of 9 or 0b1001 will enable the interrupt for
	 * pins 0 and 3.
	 * 
	 * The interrupts use the grouping as set by {@link BrickletIndustrialDigitalIn4#setGroup(char[])}.
	 * 
	 * The interrupt is delivered with the listener {@link BrickletIndustrialDigitalIn4.InterruptListener}.
	 */
	public void setInterrupt(int interruptMask) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_INTERRUPT, this);
		bb.putShort((short)interruptMask);

		sendRequest(bb.array());
	}

	/**
	 * Returns the interrupt bitmask as set by {@link BrickletIndustrialDigitalIn4#setInterrupt(int)}.
	 */
	public int getInterrupt() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_INTERRUPT, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int interruptMask = IPConnection.unsignedShort(bb.getShort());

		return interruptMask;
	}

	/**
	 * Returns the current value of the edge counter for the selected pin. You can
	 * configure the edges that are counted with {@link BrickletIndustrialDigitalIn4#setEdgeCountConfig(int, short, short)}.
	 * 
	 * If you set the reset counter to *true*, the count is set back to 0
	 * directly after it is read.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public long getEdgeCount(short pin, boolean resetCounter) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_GET_EDGE_COUNT, this);
		bb.put((byte)pin);
		bb.put((byte)(resetCounter ? 1 : 0));

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long count = IPConnection.unsignedInt(bb.getInt());

		return count;
	}

	/**
	 * Configures the edge counter for the selected pins.
	 * 
	 * The edge type parameter configures if rising edges, falling edges or
	 * both are counted if the pin is configured for input. Possible edge types are:
	 * 
	 * * 0 = rising (default)
	 * * 1 = falling
	 * * 2 = both
	 * 
	 * The debounce time is given in ms.
	 * 
	 * If you don't know what any of this means, just leave it at default. The
	 * default configuration is very likely OK for you.
	 * 
	 * Default values: 0 (edge type) and 100ms (debounce time)
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public void setEdgeCountConfig(int selectionMask, short edgeType, short debounce) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_EDGE_COUNT_CONFIG, this);
		bb.putShort((short)selectionMask);
		bb.put((byte)edgeType);
		bb.put((byte)debounce);

		sendRequest(bb.array());
	}

	/**
	 * Returns the edge type and debounce time for the selected pin as set by
	 * {@link BrickletIndustrialDigitalIn4#setEdgeCountConfig(int, short, short)}.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public EdgeCountConfig getEdgeCountConfig(short pin) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_GET_EDGE_COUNT_CONFIG, this);
		bb.put((byte)pin);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		EdgeCountConfig obj = new EdgeCountConfig();
		obj.edgeType = IPConnection.unsignedByte(bb.get());
		obj.debounce = IPConnection.unsignedByte(bb.get());

		return obj;
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
	 * Adds a Interrupt listener.
	 */
	public void addInterruptListener(InterruptListener listener) {
		listenerInterrupt.add(listener);
	}

	/**
	 * Removes a Interrupt listener.
	 */
	public void removeInterruptListener(InterruptListener listener) {
		listenerInterrupt.remove(listener);
	}
}