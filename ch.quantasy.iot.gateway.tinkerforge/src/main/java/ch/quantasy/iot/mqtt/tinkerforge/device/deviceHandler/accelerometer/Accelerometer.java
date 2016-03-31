/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.event.AccelerationEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.event.AccelerationReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.intent.CallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.intent.CallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.intent.ConfigIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.intent.DebouncePeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.intent.LEDIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.status.CallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.status.CallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.status.ConfigStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.status.DebounceStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.status.LEDStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletAccelerometer;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class Accelerometer extends ADeviceHandler<BrickletAccelerometer> implements BrickletAccelerometer.AccelerationListener, BrickletAccelerometer.AccelerationReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN_X = "minX";
    public static final String THRESHOLD_MAX_X = "maxX";
    public static final String THRESHOLD_MIN_Y = "minY";
    public static final String THRESHOLD_MAX_Y = "maxY";
    public static final String THRESHOLD_MIN_Z = "minZ";
    public static final String THRESHOLD_MAX_Z = "maxZ";
    public static final String ENABLED = "enabled";
    public static final String AVERAGE = "average";
    public static final String DATA_RATE = "dataRate";
    public static final String FULL_SCALE = "fullScale";
    public static final String FILTER_BANDWIDTH = "filterBandwidth";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String Z = "z";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "Accelerometer";
    }

    public Accelerometer(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(CallbackPeriodStatus.class, CallbackThresholdStatus.class, DebounceStatus.class, ConfigStatus.class, LEDStatus.class);
	super.addEventClass(AccelerationReachedEvent.class, AccelerationReachedEvent.class);
	super.addIntentClass(CallbackPeriodIntent.class, CallbackThresholdIntent.class, DebouncePeriodIntent.class, ConfigIntent.class, LEDIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addAccelerationListener(this);
	getDevice().addAccelerationReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeAccelerationListener(this);
	getDevice().removeAccelerationReachedListener(this);

    }

    public void executeIntent(LEDIntent intent) throws TimeoutException, NotConnectedException {

	boolean isLightOn = intent.getValue(VALUE, Boolean.class);
	if (isLightOn) {
	    getDevice().ledOn();
	} else {
	    getDevice().ledOff();
	}
	getStatus(LEDStatus.class).update(VALUE, getDevice().isLEDOn());
    }

    public void executeIntent(ConfigIntent intent) throws TimeoutException, NotConnectedException {
	short dataRate = intent.getValue(Accelerometer.DATA_RATE, Short.class);
	short fullScale = intent.getValue(Accelerometer.FULL_SCALE, Short.class);
	short filterBandwidth = intent.getValue(Accelerometer.FILTER_BANDWIDTH, Short.class);
	getDevice().setConfiguration(dataRate, fullScale, filterBandwidth);
	getStatus(ConfigStatus.class).update(intent);
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Accelerometer.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(Accelerometer.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(CallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Accelerometer.PERIOD, Long.class);
	getDevice().setAccelerationCallbackPeriod(period);
	getStatus(CallbackPeriodStatus.class).update(PERIOD, getDevice().getAccelerationCallbackPeriod());
    }

    public void executeIntent(CallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(Accelerometer.THRESHOLD_OPTION, Character.class);
	if (option == 's') {
	    option = '<';
	}
	if (option == 'g') {
	    option = '>';
	}
	short minX = intent.getValue(Accelerometer.THRESHOLD_MIN_X, Short.class);
	short maxX = intent.getValue(Accelerometer.THRESHOLD_MAX_X, Short.class);
	short minY = intent.getValue(Accelerometer.THRESHOLD_MIN_Y, Short.class);
	short maxY = intent.getValue(Accelerometer.THRESHOLD_MAX_Y, Short.class);
	short minZ = intent.getValue(Accelerometer.THRESHOLD_MIN_Z, Short.class);
	short maxZ = intent.getValue(Accelerometer.THRESHOLD_MAX_Z, Short.class);
	getDevice().setAccelerationCallbackThreshold(option, minX, maxX, minY, maxY, minZ, maxZ);
	getStatus(CallbackThresholdStatus.class).update(intent);
    }

    @Override
    public void acceleration(short s, short s1, short s2) {
	getEvent(AccelerationEvent.class).update(VALUE, new Short[]{s, s1, s2});
    }

    @Override
    public void accelerationReached(short s, short s1, short s2) {
	getEvent(AccelerationReachedEvent.class).update(VALUE, new Short[]{s, s1, s2});
    }

}
