/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture;

import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.event.MoistureEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.event.MoistureReachedEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.intent.CallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.intent.CallbackThresholdIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.intent.DebouncePeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.intent.MovingAverageIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.status.CallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.status.CallbackThresholdStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.status.DebounceStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.status.MovingAverageStatus;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletMoisture;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class Moisture extends ADeviceHandler<BrickletMoisture> implements BrickletMoisture.MoistureListener, BrickletMoisture.MoistureReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";
    public static final String ENABLED = "enabled";
    public static final String AVERAGE = "average";

    public static final String MOISTURE = "moisture";

    public String getApplicationName() {
	return "Moisture";
    }

    public Moisture(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(CallbackPeriodStatus.class, CallbackThresholdStatus.class, DebounceStatus.class, MovingAverageStatus.class);
	super.addEventClass(MoistureEvent.class, MoistureReachedEvent.class);
	super.addIntentClass(CallbackPeriodIntent.class, CallbackThresholdIntent.class, DebouncePeriodIntent.class, MovingAverageIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addMoistureListener(this);
	getDevice().addMoistureReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeMoistureListener(this);
	getDevice().removeMoistureReachedListener(this);
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
	if (intent instanceof CallbackPeriodIntent) {
	    executeIntent((CallbackPeriodIntent) intent);
	}
	if (intent instanceof CallbackThresholdIntent) {
	    executeIntent((CallbackThresholdIntent) intent);
	}
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Moisture.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(Moisture.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(CallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Moisture.PERIOD, Long.class);
	getDevice().setMoistureCallbackPeriod(period);
	getStatus(CallbackPeriodStatus.class).update(PERIOD, getDevice().getMoistureCallbackPeriod());
    }

    public void executeIntent(CallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(Moisture.THRESHOLD_OPTION, Character.class);
	int min = intent.getValue(Moisture.THRESHOLD_MIN, Integer.class);
	int max = intent.getValue(Moisture.THRESHOLD_MAX, Integer.class);
	getDevice().setMoistureCallbackThreshold(option, min, max);
	getStatus(CallbackThresholdStatus.class).updateIntent(intent);
    }

    public void executeIntent(MovingAverageIntent intent) throws TimeoutException, NotConnectedException {
	short average = intent.getContent(Moisture.AVERAGE).getValue(Short.class);
	getDevice().setMovingAverage(average);
	getStatus(MovingAverageStatus.class).update(Moisture.AVERAGE, getDevice().getMovingAverage());
    }

    @Override
    public void moisture(int i) {
	getEvent(MoistureEvent.class).update(MOISTURE, i);
    }

    @Override
    public void moistureReached(int i) {
	getEvent(MoistureReachedEvent.class).update(MOISTURE, i);

    }

}
