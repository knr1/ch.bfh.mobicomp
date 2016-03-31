package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.event.AltitudeEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.event.CoordinatesEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.event.DateTimeEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.event.MotionEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.intent.AltitudeCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.intent.CoordinatesCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.intent.DateTimeCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.intent.MotionCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.intent.RestartIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.intent.StatusCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.status.AltitudeCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.status.CoordinatesCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.status.DateTimeCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.status.GPSStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.status.MotionCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.status.StatusCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletGPS;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class GPS extends ADeviceHandler<BrickletGPS> implements BrickletGPS.AltitudeListener, BrickletGPS.CoordinatesListener, BrickletGPS.DateTimeListener, BrickletGPS.MotionListener, BrickletGPS.StatusListener {

    public static final String RESTART_TYPE = "type";
    public static final String STATUS = "status";
    public static final String FIX = "fix";
    public static final String SATELLITES_IN_VIEW = "satellitesInView";
    public static final String SATELLITES_IN_USE = "satellitesInUse";
    public static final String PERIOD = "period";
    public static final String ALTITUDE = "altitude";
    public static final String GEOIDAL_SEPARATION = "geoidalSeparation";
    public static final String COURSE = "course";
    public static final String SPEED = "speed";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String LATITUDE = "latitude";
    public static final String NS = "ns";
    public static final String LONGITUDE = "longitude";
    public static final String EW = "ew";
    public static final String PDOP = "pdop";
    public static final String HDOP = "hdop";
    public static final String VDOP = "vdop";
    public static final String EPE = "epe";

    public String getApplicationName() {
	return "GPS";
    }

    public GPS(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addIntentClass(RestartIntent.class, StatusCallbackPeriodIntent.class, AltitudeCallbackPeriodIntent.class, MotionCallbackPeriodIntent.class, DateTimeCallbackPeriodIntent.class, CoordinatesCallbackPeriodIntent.class);
	super.addEventClass(AltitudeEvent.class, CoordinatesEvent.class, DateTimeEvent.class, MotionEvent.class, DateTimeEvent.class);
	super.addStatusClass(GPSStatus.class, StatusCallbackPeriodStatus.class, AltitudeCallbackPeriodStatus.class, MotionCallbackPeriodStatus.class, CoordinatesCallbackPeriodStatus.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addAltitudeListener(this);
	getDevice().addCoordinatesListener(this);
	getDevice().addDateTimeListener(this);
	getDevice().addMotionListener(this);
	getDevice().addStatusListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeAltitudeListener(this);
	getDevice().removeCoordinatesListener(this);
	getDevice().removeDateTimeListener(this);
	getDevice().removeMotionListener(this);
	getDevice().removeStatusListener(this);
    }

    public void executeIntent(RestartIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().restart(intent.getValue(RESTART_TYPE, Short.class));
    }

    public void executeIntent(StatusCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().setStatusCallbackPeriod(intent.getValue(PERIOD, Long.class));
	getStatus(StatusCallbackPeriodStatus.class).update(PERIOD, getDevice().getStatusCallbackPeriod());
    }

    public void executeIntent(AltitudeCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().setAltitudeCallbackPeriod(intent.getValue(PERIOD, Long.class));
	getStatus(AltitudeCallbackPeriodStatus.class).update(PERIOD, getDevice().getAltitudeCallbackPeriod());
    }

    public void executeIntent(MotionCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().setMotionCallbackPeriod(intent.getValue(PERIOD, Long.class));
	getStatus(MotionCallbackPeriodStatus.class).update(PERIOD, getDevice().getMotionCallbackPeriod());
    }

    public void executeIntent(DateTimeCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().setDateTimeCallbackPeriod(intent.getValue(PERIOD, Long.class));
	getStatus(DateTimeCallbackPeriodStatus.class).update(PERIOD, getDevice().getDateTimeCallbackPeriod());
    }

    public void executeIntent(CoordinatesCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().setCoordinatesCallbackPeriod(intent.getValue(PERIOD, Long.class));
	getStatus(CoordinatesCallbackPeriodStatus.class).update(PERIOD, getDevice().getCoordinatesCallbackPeriod());
    }

    @Override
    public void altitude(int l, int l1) {
	getEvent(AltitudeEvent.class).update(ALTITUDE, l);
	getEvent(AltitudeEvent.class).update(GEOIDAL_SEPARATION, l1);

    }

    @Override
    public void coordinates(long l, char c, long l1, char c1, int i, int i1, int i2, int i3) {
	getEvent(CoordinatesEvent.class).update(LATITUDE, l);
	getEvent(CoordinatesEvent.class).update(NS, c);
	getEvent(CoordinatesEvent.class).update(LONGITUDE, l1);
	getEvent(CoordinatesEvent.class).update(EW, c1);
	getEvent(CoordinatesEvent.class).update(PDOP, i);
	getEvent(CoordinatesEvent.class).update(HDOP, i1);
	getEvent(CoordinatesEvent.class).update(VDOP, i2);
	getEvent(CoordinatesEvent.class).update(EPE, i3);
    }

    @Override
    public void dateTime(long l, long l1) {
	getEvent(DateTimeEvent.class).update(DATE, l);
	getEvent(DateTimeEvent.class).update(TIME, l1);
    }

    @Override
    public void motion(long l, long l1) {
	getEvent(MotionEvent.class).update(COURSE, l);
	getEvent(MotionEvent.class).update(SPEED, l1);
    }

    @Override
    public void status(short s, short s1, short s2) {
	getStatus(GPSStatus.class).update(FIX, s);
	getStatus(GPSStatus.class).update(SATELLITES_IN_VIEW, s1);
	getStatus(GPSStatus.class).update(SATELLITES_IN_USE, s2);

    }
}
