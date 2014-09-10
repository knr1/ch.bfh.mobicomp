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
 * Device for receiving GPS position
 */
public class BrickletGPS extends Device {
	public final static int DEVICE_IDENTIFIER = 222;

	public final static byte FUNCTION_GET_COORDINATES = (byte)1;
	public final static byte FUNCTION_GET_STATUS = (byte)2;
	public final static byte FUNCTION_GET_ALTITUDE = (byte)3;
	public final static byte FUNCTION_GET_MOTION = (byte)4;
	public final static byte FUNCTION_GET_DATE_TIME = (byte)5;
	public final static byte FUNCTION_RESTART = (byte)6;
	public final static byte FUNCTION_SET_COORDINATES_CALLBACK_PERIOD = (byte)7;
	public final static byte FUNCTION_GET_COORDINATES_CALLBACK_PERIOD = (byte)8;
	public final static byte FUNCTION_SET_STATUS_CALLBACK_PERIOD = (byte)9;
	public final static byte FUNCTION_GET_STATUS_CALLBACK_PERIOD = (byte)10;
	public final static byte FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD = (byte)11;
	public final static byte FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD = (byte)12;
	public final static byte FUNCTION_SET_MOTION_CALLBACK_PERIOD = (byte)13;
	public final static byte FUNCTION_GET_MOTION_CALLBACK_PERIOD = (byte)14;
	public final static byte FUNCTION_SET_DATE_TIME_CALLBACK_PERIOD = (byte)15;
	public final static byte FUNCTION_GET_DATE_TIME_CALLBACK_PERIOD = (byte)16;
	public final static byte CALLBACK_COORDINATES = (byte)17;
	public final static byte CALLBACK_STATUS = (byte)18;
	public final static byte CALLBACK_ALTITUDE = (byte)19;
	public final static byte CALLBACK_MOTION = (byte)20;
	public final static byte CALLBACK_DATE_TIME = (byte)21;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static short FIX_NO_FIX = (short)1;
	public final static short FIX_2D_FIX = (short)2;
	public final static short FIX_3D_FIX = (short)3;
	public final static short RESTART_TYPE_HOT_START = (short)0;
	public final static short RESTART_TYPE_WARM_START = (short)1;
	public final static short RESTART_TYPE_COLD_START = (short)2;
	public final static short RESTART_TYPE_FACTORY_RESET = (short)3;

	private List<CoordinatesListener> listenerCoordinates = new CopyOnWriteArrayList<CoordinatesListener>();
	private List<StatusListener> listenerStatus = new CopyOnWriteArrayList<StatusListener>();
	private List<AltitudeListener> listenerAltitude = new CopyOnWriteArrayList<AltitudeListener>();
	private List<MotionListener> listenerMotion = new CopyOnWriteArrayList<MotionListener>();
	private List<DateTimeListener> listenerDateTime = new CopyOnWriteArrayList<DateTimeListener>();

	public class Coordinates {
		public long latitude;
		public char ns;
		public long longitude;
		public char ew;
		public int pdop;
		public int hdop;
		public int vdop;
		public int epe;

		public String toString() {
			return "[" + "latitude = " + latitude + ", " + "ns = " + ns + ", " + "longitude = " + longitude + ", " + "ew = " + ew + ", " + "pdop = " + pdop + ", " + "hdop = " + hdop + ", " + "vdop = " + vdop + ", " + "epe = " + epe + "]";
		}
	}

	public class Status {
		public short fix;
		public short satellitesView;
		public short satellitesUsed;

		public String toString() {
			return "[" + "fix = " + fix + ", " + "satellitesView = " + satellitesView + ", " + "satellitesUsed = " + satellitesUsed + "]";
		}
	}

	public class Altitude {
		public long altitude;
		public long geoidalSeparation;

		public String toString() {
			return "[" + "altitude = " + altitude + ", " + "geoidalSeparation = " + geoidalSeparation + "]";
		}
	}

	public class Motion {
		public long course;
		public long speed;

		public String toString() {
			return "[" + "course = " + course + ", " + "speed = " + speed + "]";
		}
	}

	public class DateTime {
		public long date;
		public long time;

		public String toString() {
			return "[" + "date = " + date + ", " + "time = " + time + "]";
		}
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletGPS#setCoordinatesCallbackPeriod(long)}. The parameters are the same
	 * as for {@link BrickletGPS#getCoordinates()}.
	 * 
	 * {@link BrickletGPS.CoordinatesListener} is only triggered if the coordinates changed since the
	 * last triggering and if there is currently a fix as indicated by
	 * {@link BrickletGPS#getStatus()}.
	 */
	public interface CoordinatesListener {
		public void coordinates(long latitude, char ns, long longitude, char ew, int pdop, int hdop, int vdop, int epe);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletGPS#setStatusCallbackPeriod(long)}. The parameters are the same
	 * as for {@link BrickletGPS#getStatus()}.
	 * 
	 * {@link BrickletGPS.StatusListener} is only triggered if the status changed since the
	 * last triggering.
	 */
	public interface StatusListener {
		public void status(short fix, short satellitesView, short satellitesUsed);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletGPS#setAltitudeCallbackPeriod(long)}. The parameters are the same
	 * as for {@link BrickletGPS#getAltitude()}.
	 * 
	 * {@link BrickletGPS.AltitudeListener} is only triggered if the altitude changed since the
	 * last triggering and if there is currently a fix as indicated by
	 * {@link BrickletGPS#getStatus()}.
	 */
	public interface AltitudeListener {
		public void altitude(long altitude, long geoidalSeparation);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletGPS#setMotionCallbackPeriod(long)}. The parameters are the same
	 * as for {@link BrickletGPS#getMotion()}.
	 * 
	 * {@link BrickletGPS.MotionListener} is only triggered if the motion changed since the
	 * last triggering and if there is currently a fix as indicated by
	 * {@link BrickletGPS#getStatus()}.
	 */
	public interface MotionListener {
		public void motion(long course, long speed);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletGPS#setDateTimeCallbackPeriod(long)}. The parameters are the same
	 * as for {@link BrickletGPS#getDateTime()}.
	 * 
	 * {@link BrickletGPS.DateTimeListener} is only triggered if the date or time changed since the
	 * last triggering.
	 */
	public interface DateTimeListener {
		public void dateTime(long date, long time);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletGPS(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_COORDINATES)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_STATUS)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ALTITUDE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MOTION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DATE_TIME)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_RESTART)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_COORDINATES_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_COORDINATES_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_STATUS_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_STATUS_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ALTITUDE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ALTITUDE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_MOTION_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MOTION_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DATE_TIME_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DATE_TIME_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_COORDINATES)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_STATUS)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_ALTITUDE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_MOTION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_DATE_TIME)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_COORDINATES] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				long latitude = IPConnection.unsignedInt(bb.getInt());
				char ns = (char)(bb.get());
				long longitude = IPConnection.unsignedInt(bb.getInt());
				char ew = (char)(bb.get());
				int pdop = IPConnection.unsignedShort(bb.getShort());
				int hdop = IPConnection.unsignedShort(bb.getShort());
				int vdop = IPConnection.unsignedShort(bb.getShort());
				int epe = IPConnection.unsignedShort(bb.getShort());

				for(CoordinatesListener listener: listenerCoordinates) {
					listener.coordinates(latitude, ns, longitude, ew, pdop, hdop, vdop, epe);
				}
			}
		};

		callbacks[CALLBACK_STATUS] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short fix = IPConnection.unsignedByte(bb.get());
				short satellitesView = IPConnection.unsignedByte(bb.get());
				short satellitesUsed = IPConnection.unsignedByte(bb.get());

				for(StatusListener listener: listenerStatus) {
					listener.status(fix, satellitesView, satellitesUsed);
				}
			}
		};

		callbacks[CALLBACK_ALTITUDE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				long altitude = IPConnection.unsignedInt(bb.getInt());
				long geoidalSeparation = IPConnection.unsignedInt(bb.getInt());

				for(AltitudeListener listener: listenerAltitude) {
					listener.altitude(altitude, geoidalSeparation);
				}
			}
		};

		callbacks[CALLBACK_MOTION] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				long course = IPConnection.unsignedInt(bb.getInt());
				long speed = IPConnection.unsignedInt(bb.getInt());

				for(MotionListener listener: listenerMotion) {
					listener.motion(course, speed);
				}
			}
		};

		callbacks[CALLBACK_DATE_TIME] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				long date = IPConnection.unsignedInt(bb.getInt());
				long time = IPConnection.unsignedInt(bb.getInt());

				for(DateTimeListener listener: listenerDateTime) {
					listener.dateTime(date, time);
				}
			}
		};
	}

	/**
	 * Returns the GPS coordinates. Latitude and longitude are given in the
	 * ``DD.dddddd°`` format, the value 57123468 means 57.123468°.
	 * The parameter ``ns`` and ``ew`` are the cardinal directions for
	 * latitude and longitude. Possible values for ``ns`` and ``ew`` are 'N', 'S', 'E'
	 * and 'W' (north, south, east and west).
	 * 
	 * PDOP, HDOP and VDOP are the dilution of precision (DOP) values. They specify
	 * the additional multiplicative effect of GPS satellite geometry on GPS 
	 * precision. See 
	 * `here <http://en.wikipedia.org/wiki/Dilution_of_precision_(GPS)>`__
	 * for more information. The values are give in hundredths.
	 * 
	 * EPE is the "Estimated Position Error". The EPE is given in cm. This is not the
	 * absolute maximum error, it is the error with a specific confidence. See
	 * `here <http://www.nps.gov/gis/gps/WhatisEPE.html>`__ for more information.
	 * 
	 * This data is only valid if there is currently a fix as indicated by
	 * {@link BrickletGPS#getStatus()}.
	 */
	public Coordinates getCoordinates() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_COORDINATES, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Coordinates obj = new Coordinates();
		obj.latitude = IPConnection.unsignedInt(bb.getInt());
		obj.ns = (char)(bb.get());
		obj.longitude = IPConnection.unsignedInt(bb.getInt());
		obj.ew = (char)(bb.get());
		obj.pdop = IPConnection.unsignedShort(bb.getShort());
		obj.hdop = IPConnection.unsignedShort(bb.getShort());
		obj.vdop = IPConnection.unsignedShort(bb.getShort());
		obj.epe = IPConnection.unsignedShort(bb.getShort());

		return obj;
	}

	/**
	 * Returns the current fix status, the number of satellites that are in view and
	 * the number of satellites that are currently used.
	 * 
	 * Possible fix status values can be:
	 * 
	 * \verbatim
	 *  "Value", "Description"
	 * 
	 *  "1", "No Fix, {@link BrickletGPS#getCoordinates()} and {@link BrickletGPS#getAltitude()} return invalid data"
	 *  "2", "2D Fix, only {@link BrickletGPS#getCoordinates()} returns valid data"
	 *  "3", "3D Fix, {@link BrickletGPS#getCoordinates()} and {@link BrickletGPS#getAltitude()} return valid data"
	 * \endverbatim
	 * 
	 * There is also a :ref:`blue LED <gps_bricklet_fix_led>` on the Bricklet that
	 * indicates the fix status.
	 */
	public Status getStatus() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_STATUS, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Status obj = new Status();
		obj.fix = IPConnection.unsignedByte(bb.get());
		obj.satellitesView = IPConnection.unsignedByte(bb.get());
		obj.satellitesUsed = IPConnection.unsignedByte(bb.get());

		return obj;
	}

	/**
	 * Returns the current altitude and corresponding geoidal separation.
	 * 
	 * Both values are given in cm.
	 * 
	 * This data is only valid if there is currently a fix as indicated by
	 * {@link BrickletGPS#getStatus()}.
	 */
	public Altitude getAltitude() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ALTITUDE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Altitude obj = new Altitude();
		obj.altitude = IPConnection.unsignedInt(bb.getInt());
		obj.geoidalSeparation = IPConnection.unsignedInt(bb.getInt());

		return obj;
	}

	/**
	 * Returns the current course and speed. Course is given in hundredths degree
	 * and speed is given in hundredths km/h. A course of 0° means the Bricklet is
	 * traveling north bound and 90° means it is traveling east bound.
	 * 
	 * Please note that this only returns useful values if an actual movement
	 * is present.
	 * 
	 * This data is only valid if there is currently a fix as indicated by
	 * {@link BrickletGPS#getStatus()}.
	 */
	public Motion getMotion() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_MOTION, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Motion obj = new Motion();
		obj.course = IPConnection.unsignedInt(bb.getInt());
		obj.speed = IPConnection.unsignedInt(bb.getInt());

		return obj;
	}

	/**
	 * Returns the current date and time. The date is
	 * given in the format ``ddmmyy`` and the time is given
	 * in the format ``hhmmss.sss``. For example, 140713 means
	 * 14.05.13 as date and 195923568 means 19:59:23.568 as time.
	 */
	public DateTime getDateTime() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_DATE_TIME, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		DateTime obj = new DateTime();
		obj.date = IPConnection.unsignedInt(bb.getInt());
		obj.time = IPConnection.unsignedInt(bb.getInt());

		return obj;
	}

	/**
	 * Restarts the GPS Bricklet, the following restart types are available:
	 * 
	 * \verbatim
	 *  "Value", "Description"
	 * 
	 *  "0", "Hot start (use all available data in the NV store)"
	 *  "1", "Warm start (don't use ephemeris at restart)"
	 *  "2", "Cold start (don't use time, position, almanacs and ephemeris at restart)"
	 *  "3", "Factory reset (clear all system/user configurations at restart)"
	 * \endverbatim
	 */
	public void restart(short restartType) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_RESTART, this);
		bb.put((byte)restartType);

		sendRequest(bb.array());
	}

	/**
	 * Sets the period in ms with which the {@link BrickletGPS.CoordinatesListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletGPS.CoordinatesListener} is only triggered if the coordinates changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setCoordinatesCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_COORDINATES_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletGPS#setCoordinatesCallbackPeriod(long)}.
	 */
	public long getCoordinatesCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_COORDINATES_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletGPS.StatusListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletGPS.StatusListener} is only triggered if the status changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setStatusCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_STATUS_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletGPS#setStatusCallbackPeriod(long)}.
	 */
	public long getStatusCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_STATUS_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletGPS.AltitudeListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletGPS.AltitudeListener} is only triggered if the altitude changed since the
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
	 * Returns the period as set by {@link BrickletGPS#setAltitudeCallbackPeriod(long)}.
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
	 * Sets the period in ms with which the {@link BrickletGPS.MotionListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletGPS.MotionListener} is only triggered if the motion changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setMotionCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_MOTION_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletGPS#setMotionCallbackPeriod(long)}.
	 */
	public long getMotionCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_MOTION_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletGPS.DateTimeListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletGPS.DateTimeListener} is only triggered if the date or time changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setDateTimeCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_DATE_TIME_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletGPS#setDateTimeCallbackPeriod(long)}.
	 */
	public long getDateTimeCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_DATE_TIME_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
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
	 * Adds a Coordinates listener.
	 */
	public void addCoordinatesListener(CoordinatesListener listener) {
		listenerCoordinates.add(listener);
	}

	/**
	 * Removes a Coordinates listener.
	 */
	public void removeCoordinatesListener(CoordinatesListener listener) {
		listenerCoordinates.remove(listener);
	}

	/**
	 * Adds a Status listener.
	 */
	public void addStatusListener(StatusListener listener) {
		listenerStatus.add(listener);
	}

	/**
	 * Removes a Status listener.
	 */
	public void removeStatusListener(StatusListener listener) {
		listenerStatus.remove(listener);
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
	 * Adds a Motion listener.
	 */
	public void addMotionListener(MotionListener listener) {
		listenerMotion.add(listener);
	}

	/**
	 * Removes a Motion listener.
	 */
	public void removeMotionListener(MotionListener listener) {
		listenerMotion.remove(listener);
	}

	/**
	 * Adds a DateTime listener.
	 */
	public void addDateTimeListener(DateTimeListener listener) {
		listenerDateTime.add(listener);
	}

	/**
	 * Removes a DateTime listener.
	 */
	public void removeDateTimeListener(DateTimeListener listener) {
		listenerDateTime.remove(listener);
	}
}