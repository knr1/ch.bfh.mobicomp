/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer;

import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.event.AirPressureReachedEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.event.AirPressureValueEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.event.AltitudeEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.event.AltitudeReachedEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.intent.AirPressureCallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.intent.AirPressureCallbackThresholdIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.intent.AltitudeCallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.intent.AltitudeCallbackThresholdIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.intent.AveragingIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.intent.DebouncePeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.status.AirPressureCallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.status.AirPressureCallbackThresholdStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.status.AltitudeCallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.status.AltitudeCallbackThresholdStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.status.AveragingStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.status.DebounceStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.ADeviceHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class Barometer extends ADeviceHandler<BrickletBarometer> implements BrickletBarometer.AirPressureListener, BrickletBarometer.AirPressureReachedListener, BrickletBarometer.AltitudeListener, BrickletBarometer.AltitudeReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";
    public static final String ENABLED = "enabled";
    public static final String MOVING_AVERAGE_PRESSURE = "movingAveragePressure";
    public static final String AVERAGE_PRESSURE = "averagePressure";
    public static final String AVERAGE_TEMPERATURE = "averageTemperature";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "Barometer";
    }

    public Barometer(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(AirPressureCallbackPeriodStatus.class, AirPressureCallbackThresholdStatus.class, AltitudeCallbackPeriodStatus.class, AltitudeCallbackThresholdStatus.class, DebounceStatus.class, AveragingStatus.class);
	super.addEventClass(AirPressureValueEvent.class, AltitudeEvent.class, AirPressureReachedEvent.class, AltitudeReachedEvent.class);
	super.addIntentClass(AirPressureCallbackPeriodIntent.class, AirPressureCallbackThresholdIntent.class, AltitudeCallbackPeriodIntent.class, AltitudeCallbackThresholdIntent.class, DebouncePeriodIntent.class, AveragingIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addAirPressureListener(this);
	getDevice().addAirPressureReachedListener(this);
	getDevice().addAltitudeListener(this);
	getDevice().addAltitudeReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeAirPressureListener(this);
	getDevice().removeAirPressureReachedListener(this);
	getDevice().removeAltitudeListener(this);
	getDevice().removeAltitudeReachedListener(this);
    }

    /**
     * This method allows to describe the strategy of the DeviceHandler for any incoming intent. In this specific case
     * it simply dispatches every intent to the concrete execution.
     *
     * @param intent
     */
    public void executeIntent(AnIntent intent) throws Throwable {
	if (intent instanceof DebouncePeriodIntent) {
	    executeIntent((DebouncePeriodIntent) intent);
	}
	if (intent instanceof AirPressureCallbackPeriodIntent) {
	    executeIntent((AirPressureCallbackPeriodIntent) intent);
	}
	if (intent instanceof AltitudeCallbackPeriodIntent) {
	    executeIntent((AltitudeCallbackPeriodIntent) intent);
	}
	if (intent instanceof AltitudeCallbackThresholdIntent) {
	    executeIntent((AltitudeCallbackThresholdIntent) intent);
	}
	if (intent instanceof AirPressureCallbackThresholdIntent) {
	    executeIntent((AirPressureCallbackThresholdIntent) intent);
	}
	if (intent instanceof AveragingIntent) {
	    executeIntent((AveragingIntent) intent);
	}
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Barometer.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(Barometer.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(AirPressureCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Barometer.PERIOD, Long.class);
	getDevice().setAirPressureCallbackPeriod(period);
	getStatus(AirPressureCallbackPeriodStatus.class).update(PERIOD, getDevice().getAirPressureCallbackPeriod());
    }

    public void executeIntent(AltitudeCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Barometer.PERIOD, Long.class);
	getDevice().setAltitudeCallbackPeriod(period);
	getStatus(AltitudeCallbackPeriodStatus.class).update(PERIOD, getDevice().getAltitudeCallbackPeriod());
    }

    public void executeIntent(AirPressureCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(Barometer.THRESHOLD_OPTION, Character.class);
	int min = intent.getValue(Barometer.THRESHOLD_MIN, Integer.class);
	int max = intent.getValue(Barometer.THRESHOLD_MAX, Integer.class);
	getDevice().setAirPressureCallbackThreshold(option, min, max);
	getStatus(AirPressureCallbackThresholdStatus.class).updateIntent(intent);
    }

    public void executeIntent(AltitudeCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(Barometer.THRESHOLD_OPTION, Character.class);
	int min = intent.getValue(Barometer.THRESHOLD_MIN, Integer.class);
	int max = intent.getValue(Barometer.THRESHOLD_MAX, Integer.class);
	getDevice().setAltitudeCallbackThreshold(option, min, max);
	getStatus(AltitudeCallbackThresholdStatus.class).updateIntent(intent);
    }

    public void executeIntent(AveragingIntent intent) throws TimeoutException, NotConnectedException {
	short averagePressure = intent.getContent(Barometer.AVERAGE_PRESSURE).getValue(Short.class);
	short averageTemperature = intent.getContent(Barometer.AVERAGE_TEMPERATURE).getValue(Short.class);
	short movingAveragePressure = intent.getContent(Barometer.MOVING_AVERAGE_PRESSURE).getValue(Short.class);

	getDevice().setAveraging(movingAveragePressure, averagePressure, averageTemperature);
	BrickletBarometer.Averaging averaging = getDevice().getAveraging();
	getStatus(AveragingStatus.class).update(Barometer.AVERAGE_PRESSURE, averaging.averagePressure);
	getStatus(AveragingStatus.class).update(Barometer.AVERAGE_TEMPERATURE, averaging.averageTemperature);
	getStatus(AveragingStatus.class).update(Barometer.MOVING_AVERAGE_PRESSURE, averaging.movingAveragePressure);
    }

    @Override
    public void airPressure(int i) {
	getEvent(AirPressureValueEvent.class).update(VALUE, i);
    }

    @Override
    public void airPressureReached(int i) {
	getEvent(AirPressureReachedEvent.class).update(VALUE, i);
    }

    @Override
    public void altitude(int i) {
	getEvent(AltitudeEvent.class).update(VALUE, i);
    }

    @Override
    public void altitudeReached(int i) {
	getEvent(AltitudeReachedEvent.class).update(VALUE, i);
    }

}
