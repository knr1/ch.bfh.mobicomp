/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity;

import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.event.AnalogValueEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.event.AnalogValueReachedEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.event.HumidityValueEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.event.HumidityValueReachedEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.intent.AnalogCallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.intent.AnalogCallbackThresholdIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.intent.DebouncePeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.intent.PositionCallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.intent.HumidityCallbackThresholdIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.status.AnalogCallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.status.AnalogCallbackThresholdStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.status.DebounceStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.status.HumidityCallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.status.HumidityCallbackThresholdStatus;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class Humidity extends ADeviceHandler<BrickletHumidity> implements BrickletHumidity.AnalogValueListener, BrickletHumidity.AnalogValueReachedListener, BrickletHumidity.HumidityListener, BrickletHumidity.HumidityReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";
    public static final String ENABLED = "enabled";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "Humidity";
    }

    public Humidity(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(AnalogCallbackPeriodStatus.class, AnalogCallbackThresholdStatus.class, HumidityCallbackPeriodStatus.class, HumidityCallbackThresholdStatus.class, DebounceStatus.class);
	super.addEventClass(AnalogValueEvent.class, HumidityValueEvent.class, AnalogValueReachedEvent.class, HumidityValueReachedEvent.class);
	super.addIntentClass(AnalogCallbackPeriodIntent.class, AnalogCallbackThresholdIntent.class, PositionCallbackPeriodIntent.class, HumidityCallbackThresholdIntent.class, DebouncePeriodIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addAnalogValueListener(this);
	getDevice().addAnalogValueReachedListener(this);
	getDevice().addHumidityListener(this);
	getDevice().addHumidityReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeAnalogValueListener(this);
	getDevice().removeAnalogValueReachedListener(this);
	getDevice().removeHumidityListener(this);
	getDevice().removeHumidityReachedListener(this);
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
	if (intent instanceof AnalogCallbackPeriodIntent) {
	    executeIntent((AnalogCallbackPeriodIntent) intent);
	}
	if (intent instanceof PositionCallbackPeriodIntent) {
	    executeIntent((PositionCallbackPeriodIntent) intent);
	}
	if (intent instanceof HumidityCallbackThresholdIntent) {
	    executeIntent((HumidityCallbackThresholdIntent) intent);
	}
	if (intent instanceof AnalogCallbackThresholdIntent) {
	    executeIntent((AnalogCallbackThresholdIntent) intent);
	}
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Humidity.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(Humidity.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(AnalogCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Humidity.PERIOD, Long.class);
	getDevice().setAnalogValueCallbackPeriod(period);
	getStatus(AnalogCallbackPeriodStatus.class).update(PERIOD, getDevice().getAnalogValueCallbackPeriod());
    }

    public void executeIntent(PositionCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Humidity.PERIOD, Long.class);
	getDevice().setHumidityCallbackPeriod(period);
	getStatus(HumidityCallbackPeriodStatus.class).update(PERIOD, getDevice().getHumidityCallbackPeriod());
    }

    public void executeIntent(AnalogCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(Humidity.THRESHOLD_OPTION, Character.class);
	int min = intent.getValue(Humidity.THRESHOLD_MIN, Integer.class);
	int max = intent.getValue(Humidity.THRESHOLD_MAX, Integer.class);
	getDevice().setAnalogValueCallbackThreshold(option, min, max);
	getStatus(AnalogCallbackThresholdStatus.class).updateIntent(intent);
    }

    public void executeIntent(HumidityCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(Humidity.THRESHOLD_OPTION, Character.class);
	short min = intent.getValue(Humidity.THRESHOLD_MIN, Short.class);
	short max = intent.getValue(Humidity.THRESHOLD_MAX, Short.class);
	getDevice().setHumidityCallbackThreshold(option, min, max);
	getStatus(HumidityCallbackThresholdStatus.class).updateIntent(intent);
    }

    @Override
    public void analogValue(int i) {
	getEvent(AnalogValueEvent.class).update(VALUE, i);
    }

    @Override
    public void analogValueReached(int i) {
	getEvent(AnalogValueReachedEvent.class).update(VALUE, i);
    }

    @Override
    public void humidity(int i) {
	getEvent(HumidityValueEvent.class).update(VALUE, i);

    }

    @Override
    public void humidityReached(int i) {
	getEvent(HumidityValueReachedEvent.class).update(VALUE, i);
    }

}
