/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight;

import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.event.AnalogValueEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.event.AnalogValueReachedEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.event.IlluminanceEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.event.IlluminanceReachedEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.intent.AnalogCallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.intent.AnalogCallbackThresholdIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.intent.CallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.intent.CallbackThresholdIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.intent.DebouncePeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.status.AnalogCallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.status.AnalogCallbackThresholdStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.status.CallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.status.CallbackThresholdStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.status.DebounceStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AmbientLight extends ADeviceHandler<BrickletAmbientLight> implements BrickletAmbientLight.IlluminanceListener, BrickletAmbientLight.IlluminanceReachedListener, BrickletAmbientLight.AnalogValueListener, BrickletAmbientLight.AnalogValueReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";
    public static final String ENABLED = "enabled";
    public static final String AVERAGE = "average";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "AmbientLight";
    }

    public AmbientLight(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(CallbackPeriodStatus.class, CallbackThresholdStatus.class, DebounceStatus.class);
	super.addEventClass(IlluminanceEvent.class, IlluminanceReachedEvent.class);
	super.addIntentClass(CallbackPeriodIntent.class, CallbackThresholdIntent.class, DebouncePeriodIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addIlluminanceListener(this);
	getDevice().addIlluminanceReachedListener(this);
	getDevice().addAnalogValueListener(this);
	getDevice().addAnalogValueReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeIlluminanceListener(this);
	getDevice().removeIlluminanceReachedListener(this);
	getDevice().removeAnalogValueListener(this);
	getDevice().removeAnalogValueReachedListener(this);

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
	if (intent instanceof AnalogCallbackPeriodIntent) {
	    executeIntent((AnalogCallbackPeriodIntent) intent);
	}
	if (intent instanceof AnalogCallbackThresholdIntent) {
	    executeIntent((AnalogCallbackThresholdIntent) intent);
	}

    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(AmbientLight.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(AmbientLight.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(CallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(AmbientLight.PERIOD, Long.class);
	getDevice().setIlluminanceCallbackPeriod(period);
	getStatus(CallbackPeriodStatus.class).update(PERIOD, getDevice().getIlluminanceCallbackPeriod());
    }

    public void executeIntent(CallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(AmbientLight.THRESHOLD_OPTION, Character.class);
	short min = intent.getValue(AmbientLight.THRESHOLD_MIN, Short.class);
	short max = intent.getValue(AmbientLight.THRESHOLD_MAX, Short.class);
	getDevice().setIlluminanceCallbackThreshold(option, min, max);
	getStatus(CallbackThresholdStatus.class).updateIntent(intent);
    }

    public void executeIntent(AnalogCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(AmbientLight.PERIOD, Long.class);
	getDevice().setAnalogValueCallbackPeriod(period);
	getStatus(AnalogCallbackPeriodStatus.class).update(PERIOD, getDevice().getAnalogValueCallbackPeriod());
    }

    public void executeIntent(AnalogCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(AmbientLight.THRESHOLD_OPTION, Character.class);
	int min = intent.getValue(AmbientLight.THRESHOLD_MIN, Integer.class);
	int max = intent.getValue(AmbientLight.THRESHOLD_MAX, Integer.class);
	getDevice().setAnalogValueCallbackThreshold(option, min, max);
	getStatus(AnalogCallbackThresholdStatus.class).updateIntent(intent);
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
    public void illuminance(int i) {
	getEvent(IlluminanceEvent.class).update(VALUE, i);
    }

    @Override
    public void illuminanceReached(int i) {
	getEvent(IlluminanceReachedEvent.class).update(VALUE, i);
    }

}
