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
 * Device that detects presence of magnetic field via hall effect
 */
public class BrickletHallEffect extends Device {
	public final static int DEVICE_IDENTIFIER = 240;

	public final static byte FUNCTION_GET_VALUE = (byte)1;
	public final static byte FUNCTION_GET_EDGE_COUNT = (byte)2;
	public final static byte FUNCTION_SET_EDGE_COUNT_CONFIG = (byte)3;
	public final static byte FUNCTION_GET_EDGE_COUNT_CONFIG = (byte)4;
	public final static byte FUNCTION_SET_EDGE_INTERRUPT = (byte)5;
	public final static byte FUNCTION_GET_EDGE_INTERRUPT = (byte)6;
	public final static byte FUNCTION_SET_EDGE_COUNT_CALLBACK_PERIOD = (byte)7;
	public final static byte FUNCTION_GET_EDGE_COUNT_CALLBACK_PERIOD = (byte)8;
	public final static byte FUNCTION_EDGE_INTERRUPT = (byte)9;
	public final static byte CALLBACK_EDGE_COUNT = (byte)10;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static short EDGE_TYPE_RISING = (short)0;
	public final static short EDGE_TYPE_FALLING = (short)1;
	public final static short EDGE_TYPE_BOTH = (short)2;

	private List<EdgeCountListener> listenerEdgeCount = new CopyOnWriteArrayList<EdgeCountListener>();

	public class EdgeCountConfig {
		public short edgeType;
		public short debounce;

		public String toString() {
			return "[" + "edgeType = " + edgeType + ", " + "debounce = " + debounce + "]";
		}
	}

	public class EdgeInterrupt {
		public long count;
		public boolean value;

		public String toString() {
			return "[" + "count = " + count + ", " + "value = " + value + "]";
		}
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletHallEffect#setEdgeCountCallbackPeriod(long)}. The parameters are the
	 * current count and the current value (see {@link BrickletHallEffect#getValue()} and {@link BrickletHallEffect#getEdgeCount(boolean)}).
	 * 
	 * {@link BrickletHallEffect.EdgeCountListener} is only triggered if the count or value changed since the
	 * last triggering.
	 */
	public interface EdgeCountListener {
		public void edgeCount(long count, boolean value);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletHallEffect(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_VALUE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_EDGE_COUNT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_EDGE_COUNT_CONFIG)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_EDGE_COUNT_CONFIG)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_EDGE_INTERRUPT)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_EDGE_INTERRUPT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_EDGE_COUNT_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_EDGE_COUNT_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_EDGE_INTERRUPT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_EDGE_COUNT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_EDGE_COUNT] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				long count = IPConnection.unsignedInt(bb.getInt());
				boolean value = (bb.get()) != 0;

				for(EdgeCountListener listener: listenerEdgeCount) {
					listener.edgeCount(count, value);
				}
			}
		};
	}

	/**
	 * Returns *true* if a magnetic field of 35 Gauss (3.5mT) or greater is detected.
	 */
	public boolean getValue() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_VALUE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean value = (bb.get()) != 0;

		return value;
	}

	/**
	 * Returns the current value of the edge counter. You can configure
	 * edge type (rising, falling, both) that is counted with
	 * {@link BrickletHallEffect#setEdgeCountConfig(short, short)}.
	 * 
	 * If you set the reset counter to *true*, the count is set back to 0
	 * directly after it is read.
	 */
	public long getEdgeCount(boolean resetCounter) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_GET_EDGE_COUNT, this);
		bb.put((byte)(resetCounter ? 1 : 0));

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long count = IPConnection.unsignedInt(bb.getInt());

		return count;
	}

	/**
	 * The edge type parameter configures if rising edges, falling edges or 
	 * both are counted. Possible edge types are:
	 * 
	 * * 0 = rising (default)
	 * * 1 = falling
	 * * 2 = both
	 * 
	 * A magnetic field of 35 Gauss (3.5mT) or greater causes a falling edge and a
	 * magnetic field of 25 Gauss (2.5mT) or smaller causes a rising edge.
	 * 
	 * If a magnet comes near the Bricklet the signal goes low (falling edge), if
	 * a magnet is removed from the vicinity the signal goes high (rising edge).
	 * 
	 * The debounce time is given in ms.
	 * 
	 * If you don't know what any of this means, just leave it at default. The
	 * default configuration is very likely OK for you.
	 * 
	 * Default values: 0 (edge type) and 100ms (debounce time)
	 */
	public void setEdgeCountConfig(short edgeType, short debounce) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_EDGE_COUNT_CONFIG, this);
		bb.put((byte)edgeType);
		bb.put((byte)debounce);

		sendRequest(bb.array());
	}

	/**
	 * Returns the edge type and debounce time as set by {@link BrickletHallEffect#setEdgeCountConfig(short, short)}.
	 */
	public EdgeCountConfig getEdgeCountConfig() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_EDGE_COUNT_CONFIG, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		EdgeCountConfig obj = new EdgeCountConfig();
		obj.edgeType = IPConnection.unsignedByte(bb.get());
		obj.debounce = IPConnection.unsignedByte(bb.get());

		return obj;
	}

	/**
	 * Sets the number of edges until an interrupt is invoked.
	 * 
	 * If *edges* is set to n, an interrupt is invoked for every n-th detected edge.
	 * 
	 * If *edges* is set to 0, the interrupt is disabled.
	 * 
	 * Default value is 0.
	 */
	public void setEdgeInterrupt(long edges) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_EDGE_INTERRUPT, this);
		bb.putInt((int)edges);

		sendRequest(bb.array());
	}

	/**
	 * Returns the edges as set by {@link BrickletHallEffect#setEdgeInterrupt(long)}.
	 */
	public long getEdgeInterrupt() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_EDGE_INTERRUPT, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long edges = IPConnection.unsignedInt(bb.getInt());

		return edges;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletHallEffect.EdgeCountListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletHallEffect.EdgeCountListener} is only triggered if the edge count has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setEdgeCountCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_EDGE_COUNT_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletHallEffect#setEdgeCountCallbackPeriod(long)}.
	 */
	public long getEdgeCountCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_EDGE_COUNT_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * This listener is triggered every n-th count, as configured with
	 * {@link BrickletHallEffect#setEdgeInterrupt(long)}. The parameters are the
	 * current count and the current value (see {@link BrickletHallEffect#getValue()} and {@link BrickletHallEffect#getEdgeCount(boolean)}).
	 */
	public EdgeInterrupt edgeInterrupt() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_EDGE_INTERRUPT, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		EdgeInterrupt obj = new EdgeInterrupt();
		obj.count = IPConnection.unsignedInt(bb.getInt());
		obj.value = (bb.get()) != 0;

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
	 * Adds a EdgeCount listener.
	 */
	public void addEdgeCountListener(EdgeCountListener listener) {
		listenerEdgeCount.add(listener);
	}

	/**
	 * Removes a EdgeCount listener.
	 */
	public void removeEdgeCountListener(EdgeCountListener listener) {
		listenerEdgeCount.remove(listener);
	}
}