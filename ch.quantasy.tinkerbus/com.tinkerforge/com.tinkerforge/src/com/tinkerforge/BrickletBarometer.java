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
 * Device for sensing air pressure and altitude changes
 */
public class BrickletBarometer extends Device {
	public final static int DEVICE_IDENTIFIER = 221;

	public final static byte FUNCTION_GET_AIR_PRESSURE = (byte)1;
	public final static byte FUNCTION_GET_ALTITUDE = (byte)2;
	public final static byte FUNCTION_SET_AIR_PRESSURE_CALLBACK_PERIOD = (byte)3;
	public final static byte FUNCTION_GET_AIR_PRESSURE_CALLBACK_PERIOD = (byte)4;
	public final static byte FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD = (byte)5;
	public final static byte FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD = (byte)6;
	public final static byte FUNCTION_SET_AIR_PRESSURE_CALLBACK_THRESHOLD = (byte)7;
	public final static byte FUNCTION_GET_AIR_PRESSURE_CALLBACK_THRESHOLD = (byte)8;
	public final static byte FUNCTION_SET_ALTITUDE_CALLBACK_THRESHOLD = (byte)9;
	public final static byte FUNCTION_GET_ALTITUDE_CALLBACK_THRESHOLD = (byte)10;
	public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)11;
	public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)12;
	public final static byte FUNCTION_SET_REFERENCE_AIR_PRESSURE = (byte)13;
	public final static byte FUNCTION_GET_CHIP_TEMPERATURE = (byte)14;
	public final static byte CALLBACK_AIR_PRESSURE = (byte)15;
	public final static byte CALLBACK_ALTITUDE = (byte)16;
	public final static byte CALLBACK_AIR_PRESSURE_REACHED = (byte)17;
	public final static byte CALLBACK_ALTITUDE_REACHED = (byte)18;
	public final static byte FUNCTION_GET_REFERENCE_AIR_PRESSURE = (byte)19;
	public final static byte FUNCTION_SET_AVERAGING = (byte)20;
	public final static byte FUNCTION_GET_AVERAGING = (byte)21;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static char THRESHOLD_OPTION_OFF = 'x';
	public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
	public final static char THRESHOLD_OPTION_INSIDE = 'i';
	public final static char THRESHOLD_OPTION_SMALLER = '<';
	public final static char THRESHOLD_OPTION_GREATER = '>';

	private List<AirPressureListener> listenerAirPressure = new CopyOnWriteArrayList<AirPressureListener>();
	private List<AltitudeListener> listenerAltitude = new CopyOnWriteArrayList<AltitudeListener>();
	private List<AirPressureReachedListener> listenerAirPressureReached = new CopyOnWriteArrayList<AirPressureReachedListener>();
	private List<AltitudeReachedListener> listenerAltitudeReached = new CopyOnWriteArrayList<AltitudeReachedListener>();

	public class AirPressureCallbackThreshold {
		public char option;
		public int min;
		public int max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	public class AltitudeCallbackThreshold {
		public char option;
		public int min;
		public int max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	public class Averaging {
		public short movingAveragePressure;
		public short averagePressure;
		public short averageTemperature;

		public String toString() {
			return "[" + "movingAveragePressure = " + movingAveragePressure + ", " + "averagePressure = " + averagePressure + ", " + "averageTemperature = " + averageTemperature + "]";
		}
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletBarometer#setAirPressureCallbackPeriod(long)}. The parameter is the air pressure of the
	 * air pressure sensor.
	 * 
	 * {@link BrickletBarometer.AirPressureListener} is only triggered if the air pressure has changed since the
	 * last triggering.
	 */
	public interface AirPressureListener {
		public void airPressure(int airPressure);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletBarometer#setAltitudeCallbackPeriod(long)}. The parameter is the altitude of the
	 * air pressure sensor.
	 * 
	 * {@link BrickletBarometer.AltitudeListener} is only triggered if the altitude has changed since the
	 * last triggering.
	 */
	public interface AltitudeListener {
		public void altitude(int altitude);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletBarometer#setAirPressureCallbackThreshold(char, int, int)} is reached.
	 * The parameter is the air pressure of the air pressure sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletBarometer#setDebouncePeriod(long)}.
	 */
	public interface AirPressureReachedListener {
		public void airPressureReached(int airPressure);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletBarometer#setAltitudeCallbackThreshold(char, int, int)} is reached.
	 * The parameter is the altitude of the air pressure sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletBarometer#setDebouncePeriod(long)}.
	 */
	public interface AltitudeReachedListener {
		public void altitudeReached(int altitude);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletBarometer(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 1;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_AIR_PRESSURE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ALTITUDE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_AIR_PRESSURE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_AIR_PRESSURE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_AIR_PRESSURE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_AIR_PRESSURE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ALTITUDE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ALTITUDE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_REFERENCE_AIR_PRESSURE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CHIP_TEMPERATURE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_REFERENCE_AIR_PRESSURE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_AVERAGING)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_AVERAGING)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_AIR_PRESSURE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_ALTITUDE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_AIR_PRESSURE_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_ALTITUDE_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_AIR_PRESSURE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int airPressure = (bb.getInt());

				for(AirPressureListener listener: listenerAirPressure) {
					listener.airPressure(airPressure);
				}
			}
		};

		callbacks[CALLBACK_ALTITUDE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int altitude = (bb.getInt());

				for(AltitudeListener listener: listenerAltitude) {
					listener.altitude(altitude);
				}
			}
		};

		callbacks[CALLBACK_AIR_PRESSURE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int airPressure = (bb.getInt());

				for(AirPressureReachedListener listener: listenerAirPressureReached) {
					listener.airPressureReached(airPressure);
				}
			}
		};

		callbacks[CALLBACK_ALTITUDE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int altitude = (bb.getInt());

				for(AltitudeReachedListener listener: listenerAltitudeReached) {
					listener.altitudeReached(altitude);
				}
			}
		};
	}

	/**
	 * Returns the air pressure of the air pressure sensor. The value
	 * has a range of 10000 to 1200000 and is given in mbar/1000, i.e. a value
	 * of 1001092 means that an air pressure of 1001.092 mbar is measured.
	 * 
	 * If you want to get the air pressure periodically, it is recommended to use the
	 * listener {@link BrickletBarometer.AirPressureListener} and set the period with
	 * {@link BrickletBarometer#setAirPressureCallbackPeriod(long)}.
	 */
	public int getAirPressure() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_AIR_PRESSURE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int airPressure = (bb.getInt());

		return airPressure;
	}

	/**
	 * Returns the relative altitude of the air pressure sensor. The value is given in
	 * cm and is calculated based on the difference between the current air pressure
	 * and the reference air pressure that can be set with {@link BrickletBarometer#setReferenceAirPressure(int)}.
	 * 
	 * If you want to get the altitude periodically, it is recommended to use the
	 * listener {@link BrickletBarometer.AltitudeListener} and set the period with
	 * {@link BrickletBarometer#setAltitudeCallbackPeriod(long)}.
	 */
	public int getAltitude() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ALTITUDE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int altitude = (bb.getInt());

		return altitude;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletBarometer.AirPressureListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletBarometer.AirPressureListener} is only triggered if the air pressure has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setAirPressureCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_AIR_PRESSURE_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletBarometer#setAirPressureCallbackPeriod(long)}.
	 */
	public long getAirPressureCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_AIR_PRESSURE_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletBarometer.AltitudeListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletBarometer.AltitudeListener} is only triggered if the altitude has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setAltitudeCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletBarometer#setAltitudeCallbackPeriod(long)}.
	 */
	public long getAltitudeCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the thresholds for the {@link BrickletBarometer.AirPressureReachedListener} listener.
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the air pressure is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the air pressure is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the air pressure is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the air pressure is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setAirPressureCallbackThreshold(char option, int min, int max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)17, FUNCTION_SET_AIR_PRESSURE_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putInt(min);
		bb.putInt(max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletBarometer#setAirPressureCallbackThreshold(char, int, int)}.
	 */
	public AirPressureCallbackThreshold getAirPressureCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_AIR_PRESSURE_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AirPressureCallbackThreshold obj = new AirPressureCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getInt());
		obj.max = (bb.getInt());

		return obj;
	}

	/**
	 * Sets the thresholds for the {@link BrickletBarometer.AltitudeReachedListener} listener.
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the altitude is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the altitude is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the altitude is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the altitude is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setAltitudeCallbackThreshold(char option, int min, int max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)17, FUNCTION_SET_ALTITUDE_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putInt(min);
		bb.putInt(max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletBarometer#setAltitudeCallbackThreshold(char, int, int)}.
	 */
	public AltitudeCallbackThreshold getAltitudeCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ALTITUDE_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AltitudeCallbackThreshold obj = new AltitudeCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getInt());
		obj.max = (bb.getInt());

		return obj;
	}

	/**
	 * Sets the period in ms with which the threshold listeners
	 * 
	 * * {@link BrickletBarometer.AirPressureReachedListener},
	 * * {@link BrickletBarometer.AltitudeReachedListener}
	 * 
	 * are triggered, if the thresholds
	 * 
	 * * {@link BrickletBarometer#setAirPressureCallbackThreshold(char, int, int)},
	 * * {@link BrickletBarometer#setAltitudeCallbackThreshold(char, int, int)}
	 * 
	 * keep being reached.
	 * 
	 * The default value is 100.
	 */
	public void setDebouncePeriod(long debounce) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_DEBOUNCE_PERIOD, this);
		bb.putInt((int)debounce);

		sendRequest(bb.array());
	}

	/**
	 * Returns the debounce period as set by {@link BrickletBarometer#setDebouncePeriod(long)}.
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
	 * Sets the reference air pressure in mbar/1000 for the altitude calculation.
	 * Setting the reference to the current air pressure results in a calculated
	 * altitude of 0cm. Passing 0 is a shortcut for passing the current air pressure as
	 * reference.
	 * 
	 * Well known reference values are the Q codes
	 * `QNH <http://en.wikipedia.org/wiki/QNH>`__ and
	 * `QFE <http://en.wikipedia.org/wiki/Mean_sea_level_pressure#Mean_sea_level_pressure>`__
	 * used in aviation.
	 * 
	 * The default value is 1013.25mbar.
	 * 
	 * .. versionadded:: 1.1.0~(Plugin)
	 */
	public void setReferenceAirPressure(int airPressure) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_REFERENCE_AIR_PRESSURE, this);
		bb.putInt(airPressure);

		sendRequest(bb.array());
	}

	/**
	 * Returns the temperature of the air pressure sensor. The value
	 * has a range of -4000 to 8500 and is given in °C/100, i.e. a value
	 * of 2007 means that a temperature of 20.07 °C is measured.
	 * 
	 * This temperature is used internally for temperature compensation of the air
	 * pressure measurement. It is not as accurate as the temperature measured by the
	 * :ref:`temperature_bricklet` or the :ref:`temperature_ir_bricklet`.
	 */
	public short getChipTemperature() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_CHIP_TEMPERATURE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short temperature = (bb.getShort());

		return temperature;
	}

	/**
	 * Returns the reference air pressure as set by {@link BrickletBarometer#setReferenceAirPressure(int)}.
	 * 
	 * .. versionadded:: 1.1.0~(Plugin)
	 */
	public int getReferenceAirPressure() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_REFERENCE_AIR_PRESSURE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int airPressure = (bb.getInt());

		return airPressure;
	}

	/**
	 * Sets the different averaging parameters. It is possible to set
	 * the length of a normal averaging for the temperature and pressure,
	 * as well as an additional length of a 
	 * `moving average <http://en.wikipedia.org/wiki/Moving_average>`__ 
	 * for the pressure. The moving average is calculated from the normal 
	 * averages.  There is no moving average for the temperature.
	 * 
	 * The maximum length for the pressure average is 10, for the
	 * temperature average is 255 and for the moving average is 25.
	 * 
	 * Setting the all three parameters to 0 will turn the averaging
	 * completely off. If the averaging is off, there is lots of noise
	 * on the data, but the data is without delay. Thus we recommend
	 * to turn the averaging off if the Barometer Bricklet data is
	 * to be used for sensor fusion with other sensors.
	 * 
	 * The default values are 10 for the normal averages and 25 for the
	 * moving average.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public void setAveraging(short movingAveragePressure, short averagePressure, short averageTemperature) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)11, FUNCTION_SET_AVERAGING, this);
		bb.put((byte)movingAveragePressure);
		bb.put((byte)averagePressure);
		bb.put((byte)averageTemperature);

		sendRequest(bb.array());
	}

	/**
	 * Returns the averaging configuration as set by {@link BrickletBarometer#setAveraging(short, short, short)}.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public Averaging getAveraging() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_AVERAGING, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Averaging obj = new Averaging();
		obj.movingAveragePressure = IPConnection.unsignedByte(bb.get());
		obj.averagePressure = IPConnection.unsignedByte(bb.get());
		obj.averageTemperature = IPConnection.unsignedByte(bb.get());

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
	 * Adds a AirPressure listener.
	 */
	public void addAirPressureListener(AirPressureListener listener) {
		listenerAirPressure.add(listener);
	}

	/**
	 * Removes a AirPressure listener.
	 */
	public void removeAirPressureListener(AirPressureListener listener) {
		listenerAirPressure.remove(listener);
	}

	/**
	 * Adds a Altitude listener.
	 */
	public void addAltitudeListener(AltitudeListener listener) {
		listenerAltitude.add(listener);
	}

	/**
	 * Removes a Altitude listener.
	 */
	public void removeAltitudeListener(AltitudeListener listener) {
		listenerAltitude.remove(listener);
	}

	/**
	 * Adds a AirPressureReached listener.
	 */
	public void addAirPressureReachedListener(AirPressureReachedListener listener) {
		listenerAirPressureReached.add(listener);
	}

	/**
	 * Removes a AirPressureReached listener.
	 */
	public void removeAirPressureReachedListener(AirPressureReachedListener listener) {
		listenerAirPressureReached.remove(listener);
	}

	/**
	 * Adds a AltitudeReached listener.
	 */
	public void addAltitudeReachedListener(AltitudeReachedListener listener) {
		listenerAltitudeReached.add(listener);
	}

	/**
	 * Removes a AltitudeReached listener.
	 */
	public void removeAltitudeReachedListener(AltitudeReachedListener listener) {
		listenerAltitudeReached.remove(listener);
	}
}