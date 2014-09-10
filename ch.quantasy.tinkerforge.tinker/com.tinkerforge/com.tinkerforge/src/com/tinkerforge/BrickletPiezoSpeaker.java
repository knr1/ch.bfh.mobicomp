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
 * Device for controlling a piezo buzzer with configurable frequencies
 */
public class BrickletPiezoSpeaker extends Device {
	public final static int DEVICE_IDENTIFIER = 242;

	public final static byte FUNCTION_BEEP = (byte)1;
	public final static byte FUNCTION_MORSE_CODE = (byte)2;
	public final static byte FUNCTION_CALIBRATE = (byte)3;
	public final static byte CALLBACK_BEEP_FINISHED = (byte)4;
	public final static byte CALLBACK_MORSE_CODE_FINISHED = (byte)5;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;


	private List<BeepFinishedListener> listenerBeepFinished = new CopyOnWriteArrayList<BeepFinishedListener>();
	private List<MorseCodeFinishedListener> listenerMorseCodeFinished = new CopyOnWriteArrayList<MorseCodeFinishedListener>();

	/**
	 * This listener is triggered if a beep set by {@link BrickletPiezoSpeaker#beep(long, int)} is finished
	 */
	public interface BeepFinishedListener {
		public void beepFinished();
	}

	/**
	 * This listener is triggered if the playback of the morse code set by
	 * {@link BrickletPiezoSpeaker#morseCode(String, int)} is finished.
	 */
	public interface MorseCodeFinishedListener {
		public void morseCodeFinished();
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletPiezoSpeaker(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_BEEP)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_MORSE_CODE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_CALIBRATE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_BEEP_FINISHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_MORSE_CODE_FINISHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_BEEP_FINISHED] = new CallbackListener() {
			public void callback(byte[] data) {
				for(BeepFinishedListener listener: listenerBeepFinished) {
					listener.beepFinished();
				}
			}
		};

		callbacks[CALLBACK_MORSE_CODE_FINISHED] = new CallbackListener() {
			public void callback(byte[] data) {
				for(MorseCodeFinishedListener listener: listenerMorseCodeFinished) {
					listener.morseCodeFinished();
				}
			}
		};
	}

	/**
	 * Beeps with the given frequency for the duration in ms. For example: 
	 * If you set a duration of 1000, with a frequency value of 2000
	 * the piezo buzzer will beep for one second with a frequency of
	 * approximately 2 kHz.
	 * 
	 * *frequency* can be set between 585 and 7100.
	 * 
	 * The Piezo Speaker Bricklet can only approximate the frequency, it will play
	 * the best possible match by applying the calibration (see {@link BrickletPiezoSpeaker#calibrate()}).
	 */
	public void beep(long duration, int frequency) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)14, FUNCTION_BEEP, this);
		bb.putInt((int)duration);
		bb.putShort((short)frequency);

		sendRequest(bb.array());
	}

	/**
	 * Sets morse code that will be played by the piezo buzzer. The morse code
	 * is given as a string consisting of "." (dot), "-" (minus) and " " (space)
	 * for *dits*, *dahs* and *pauses*. Every other character is ignored.
	 * The second parameter is the frequency (see {@link BrickletPiezoSpeaker#beep(long, int)}).
	 * 
	 * For example: If you set the string "...---...", the piezo buzzer will beep
	 * nine times with the durations "short short short long long long short 
	 * short short".
	 * 
	 * The maximum string size is 60.
	 */
	public void morseCode(String morse, int frequency) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)70, FUNCTION_MORSE_CODE, this);
		for(int i = 0; i < 60; i++) {
			try {
				bb.put((byte)morse.charAt(i));
			} catch(Exception e) {
				bb.put((byte)0);
			}
		}

		bb.putShort((short)frequency);

		sendRequest(bb.array());
	}

	/**
	 * The Piezo Speaker Bricklet can play 512 different tones. This function
	 * plays each tone and measures the exact frequency back. The result is a
	 * mapping between setting value and frequency. This mapping is stored
	 * in the EEPROM and loaded on startup.
	 * 
	 * The Bricklet should come calibrated, you only need to call this
	 * function (once) every time you reflash the Bricklet plugin.
	 * 
	 * Returns *true* after the calibration finishes.
	 */
	public boolean calibrate() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_CALIBRATE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean calibration = (bb.get()) != 0;

		return calibration;
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
	 * Adds a BeepFinished listener.
	 */
	public void addBeepFinishedListener(BeepFinishedListener listener) {
		listenerBeepFinished.add(listener);
	}

	/**
	 * Removes a BeepFinished listener.
	 */
	public void removeBeepFinishedListener(BeepFinishedListener listener) {
		listenerBeepFinished.remove(listener);
	}

	/**
	 * Adds a MorseCodeFinished listener.
	 */
	public void addMorseCodeFinishedListener(MorseCodeFinishedListener listener) {
		listenerMorseCodeFinished.add(listener);
	}

	/**
	 * Removes a MorseCodeFinished listener.
	 */
	public void removeMorseCodeFinishedListener(MorseCodeFinishedListener listener) {
		listenerMorseCodeFinished.remove(listener);
	}
}