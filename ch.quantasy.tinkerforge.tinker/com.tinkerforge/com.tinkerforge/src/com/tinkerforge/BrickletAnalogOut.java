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
 * Device for output of voltage between 0 and 5V
 */
public class BrickletAnalogOut extends Device {
	public final static int DEVICE_IDENTIFIER = 220;

	public final static byte FUNCTION_SET_VOLTAGE = (byte)1;
	public final static byte FUNCTION_GET_VOLTAGE = (byte)2;
	public final static byte FUNCTION_SET_MODE = (byte)3;
	public final static byte FUNCTION_GET_MODE = (byte)4;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static short MODE_ANALOG_VALUE = (short)0;
	public final static short MODE_1K_TO_GROUND = (short)1;
	public final static short MODE_100K_TO_GROUND = (short)2;
	public final static short MODE_500K_TO_GROUND = (short)3;


	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletAnalogOut(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_VOLTAGE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_VOLTAGE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_MODE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MODE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
	}

	/**
	 * Sets the voltage in mV. The possible range is 0V to 5V (0-5000).
	 * Calling this function will set the mode to 0 (see {@link BrickletAnalogOut#setMode(short)}).
	 * 
	 * The default value is 0 (with mode 1).
	 */
	public void setVoltage(int voltage) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_VOLTAGE, this);
		bb.putShort((short)voltage);

		sendRequest(bb.array());
	}

	/**
	 * Returns the voltage as set by {@link BrickletAnalogOut#setVoltage(int)}.
	 */
	public int getVoltage() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_VOLTAGE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int voltage = IPConnection.unsignedShort(bb.getShort());

		return voltage;
	}

	/**
	 * Sets the mode of the analog value. Possible modes:
	 * 
	 * * 0: Normal Mode (Analog value as set by {@link BrickletAnalogOut#setVoltage(int)} is applied)
	 * * 1: 1k Ohm resistor to ground
	 * * 2: 100k Ohm resistor to ground
	 * * 3: 500k Ohm resistor to ground
	 * 
	 * Setting the mode to 0 will result in an output voltage of 0. You can jump
	 * to a higher output voltage directly by calling {@link BrickletAnalogOut#setVoltage(int)}.
	 * 
	 * The default mode is 1.
	 */
	public void setMode(short mode) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_SET_MODE, this);
		bb.put((byte)mode);

		sendRequest(bb.array());
	}

	/**
	 * Returns the mode as set by {@link BrickletAnalogOut#setMode(short)}.
	 */
	public short getMode() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_MODE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short mode = IPConnection.unsignedByte(bb.get());

		return mode;
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
}