/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS;

import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS.event.DistanceEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS.event.DistanceReachedEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS.intent.CallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS.intent.CallbackThresholdIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS.intent.DebouncePeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS.intent.MovingAverageIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS.status.CallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS.status.CallbackThresholdStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS.status.DebounceStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS.status.MovingAverageStatus;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletDistanceUS;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DistanceUS extends ADeviceHandler<BrickletDistanceUS> implements BrickletDistanceUS.DistanceListener, BrickletDistanceUS.DistanceReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";
    public static final String ENABLED = "enabled";
    public static final String AVERAGE = "average";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "DistanceUS";
    }

    public DistanceUS(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(CallbackPeriodStatus.class, CallbackThresholdStatus.class, DebounceStatus.class, MovingAverageStatus.class);
	super.addEventClass(DistanceEvent.class, DistanceReachedEvent.class);
	super.addIntentClass(CallbackPeriodIntent.class, CallbackThresholdIntent.class, DebouncePeriodIntent.class, MovingAverageIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addDistanceListener(this);
	getDevice().addDistanceReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeDistanceListener(this);
	getDevice().removeDistanceReachedListener(this);
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
	if (intent instanceof MovingAverageIntent) {
	    executeIntent((MovingAverageIntent) intent);
	}
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(DistanceUS.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(DistanceUS.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(CallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(DistanceUS.PERIOD, Long.class);
	getDevice().setDistanceCallbackPeriod(period);
	getStatus(CallbackPeriodStatus.class).update(PERIOD, getDevice().getDistanceCallbackPeriod());
    }

    public void executeIntent(CallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(DistanceUS.THRESHOLD_OPTION, Character.class);
	short min = intent.getValue(DistanceUS.THRESHOLD_MIN, Short.class);
	short max = intent.getValue(DistanceUS.THRESHOLD_MAX, Short.class);
	getDevice().setDistanceCallbackThreshold(option, min, max);
	getStatus(CallbackThresholdStatus.class).updateIntent(intent);
    }

    public void executeIntent(MovingAverageIntent intent) throws TimeoutException, NotConnectedException {
	short average = intent.getContent(DistanceUS.AVERAGE).getValue(Short.class);
	getDevice().setMovingAverage(average);
	getStatus(MovingAverageStatus.class).update(DistanceUS.AVERAGE, getDevice().getMovingAverage());
    }

    @Override
    public void distance(int i) {
	getEvent(DistanceEvent.class).update(VALUE, i);
    }

    @Override
    public void distanceReached(int i) {
	getEvent(DistanceReachedEvent.class).update(VALUE, i);
    }

}
