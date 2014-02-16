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
 * Device with two buttons and two LEDs
 */
public class BrickletDualButton extends Device {
	public final static int DEVICE_IDENTIFIER = 230;

	public final static byte FUNCTION_SET_LED_STATE = (byte)1;
	public final static byte FUNCTION_GET_LED_STATE = (byte)2;
	public final static byte FUNCTION_GET_BUTTON_STATE = (byte)3;
	public final static byte CALLBACK_STATE_CHANGED = (byte)4;
	public final static byte FUNCTION_SET_SELECTED_LED_STATE = (byte)5;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static short LED_STATE_AUTO_TOGGLE_ON = (short)0;
	public final static short LED_STATE_AUTO_TOGGLE_OFF = (short)1;
	public final static short LED_STATE_ON = (short)2;
	public final static short LED_STATE_OFF = (short)3;
	public final static short BUTTON_STATE_PRESSED = (short)0;
	public final static short BUTTON_STATE_RELEASED = (short)1;
	public final static short LED_LEFT = (short)0;
	public final static short LED_RIGHT = (short)1;

	private List<StateChangedListener> listenerStateChanged = new CopyOnWriteArrayList<StateChangedListener>();

	public class LEDState {
		public short ledL;
		public short ledR;

		public String toString() {
			return "[" + "ledL = " + ledL + ", " + "ledR = " + ledR + "]";
		}
	}

	public class ButtonState {
		public short buttonL;
		public short buttonR;

		public String toString() {
			return "[" + "buttonL = " + buttonL + ", " + "buttonR = " + buttonR + "]";
		}
	}

	/**
	 * This listener is called whenever a button is pressed. 
	 * 
	 * Possible states for buttons are:
	 * 
	 * * 0 = pressed
	 * * 1 = released
	 * 
	 * Possible states for LEDs are:
	 * 
	 * * 0 = AutoToggleOn: Auto toggle enabled and LED on.
	 * * 1 = AutoToggleOff: Auto toggle enabled and LED off.
	 * * 2 = On: LED on (auto toggle is disabled).
	 * * 3 = Off: LED off (auto toggle is disabled).
	 */
	public interface StateChangedListener {
		public void stateChanged(short buttonL, short buttonR, short ledL, short ledR);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletDualButton(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_LED_STATE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_LED_STATE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_BUTTON_STATE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_SELECTED_LED_STATE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_STATE_CHANGED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_STATE_CHANGED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short buttonL = IPConnection.unsignedByte(bb.get());
				short buttonR = IPConnection.unsignedByte(bb.get());
				short ledL = IPConnection.unsignedByte(bb.get());
				short ledR = IPConnection.unsignedByte(bb.get());

				for(StateChangedListener listener: listenerStateChanged) {
					listener.stateChanged(buttonL, buttonR, ledL, ledR);
				}
			}
		};
	}

	/**
	 * Sets the state of the LEDs. Possible states are:
	 * 
	 * * 0 = AutoToggleOn: Enables auto toggle with initially enabled LED.
	 * * 1 = AutoToggleOff: Activates auto toggle with initially disabled LED.
	 * * 2 = On: Enables LED (auto toggle is disabled).
	 * * 3 = Off: Disables LED (auto toggle is disabled).
	 * 
	 * In auto toggle mode the LED is toggled automatically at each press of a button.
	 * 
	 * If you just want to set one of the LEDs and don't know the current state
	 * of the other LED, you can get the state with {@link BrickletDualButton#getLEDState()} or you
	 * can use {@link BrickletDualButton#setSelectedLEDState(short, short)}.
	 * 
	 * The default value is (1, 1).
	 */
	public void setLEDState(short ledL, short ledR) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_LED_STATE, this);
		bb.put((byte)ledL);
		bb.put((byte)ledR);

		sendRequest(bb.array());
	}

	/**
	 * Returns the current state of the LEDs, as set by {@link BrickletDualButton#setLEDState(short, short)}.
	 */
	public LEDState getLEDState() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_LED_STATE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		LEDState obj = new LEDState();
		obj.ledL = IPConnection.unsignedByte(bb.get());
		obj.ledR = IPConnection.unsignedByte(bb.get());

		return obj;
	}

	/**
	 * Returns the current state for both buttons. Possible states are:
	 * 
	 * * 0 = pressed
	 * * 1 = released
	 */
	public ButtonState getButtonState() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_BUTTON_STATE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		ButtonState obj = new ButtonState();
		obj.buttonL = IPConnection.unsignedByte(bb.get());
		obj.buttonR = IPConnection.unsignedByte(bb.get());

		return obj;
	}

	/**
	 * Sets the state of the selected LED (0 or 1). 
	 * 
	 * The other LED remains untouched.
	 * 
	 * .. versionadded:: 2.0.0~(Plugin)
	 */
	public void setSelectedLEDState(short led, short state) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_SELECTED_LED_STATE, this);
		bb.put((byte)led);
		bb.put((byte)state);

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
	 * Adds a StateChanged listener.
	 */
	public void addStateChangedListener(StateChangedListener listener) {
		listenerStateChanged.add(listener);
	}

	/**
	 * Removes a StateChanged listener.
	 */
	public void removeStateChangedListener(StateChangedListener listener) {
		listenerStateChanged.remove(listener);
	}
}