/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLightV2;

import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLightV2.event.IlluminanceEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLightV2.event.IlluminanceReachedEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLightV2.intent.CallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLightV2.intent.CallbackThresholdIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLightV2.intent.ConfigIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLightV2.intent.DebouncePeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLightV2.status.CallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLightV2.status.CallbackThresholdStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLightV2.status.ConfigStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLightV2.status.DebounceStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletAmbientLightV2;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AmbientLightV2 extends ADeviceHandler<BrickletAmbientLightV2> implements BrickletAmbientLightV2.IlluminanceListener, BrickletAmbientLightV2.IlluminanceReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";
    public static final String ENABLED = "enabled";
    public static final String AVERAGE = "average";
    public static final String ILLUMINANCE_RANGE = "illuminanceRange";
    public static final String INTEGRATION_TIME = "integrationTime";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "AmbientLightV2";
    }

    public AmbientLightV2(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(CallbackPeriodStatus.class, CallbackThresholdStatus.class, DebounceStatus.class, ConfigStatus.class);
	super.addEventClass(IlluminanceEvent.class, IlluminanceReachedEvent.class);
	super.addIntentClass(CallbackPeriodIntent.class, CallbackThresholdIntent.class, DebouncePeriodIntent.class, ConfigIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addIlluminanceListener(this);
	getDevice().addIlluminanceReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeIlluminanceListener(this);
	getDevice().removeIlluminanceReachedListener(this);

    }

    /**
     * This method allows to describe the strategy of the DeviceHandler for any incoming intent. In this specific case
     * it simply dispatches every intent to the concrete execution.
     *
     * @param intent
     */
    @Override
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
	if (intent instanceof ConfigIntent) {
	    executeIntent((ConfigIntent) intent);
	}

    }

    public void executeIntent(ConfigIntent intent) throws TimeoutException, NotConnectedException {
	short illuminanceRange = intent.getValue(AmbientLightV2.ILLUMINANCE_RANGE, Short.class);
	short integrationTime = intent.getValue(AmbientLightV2.INTEGRATION_TIME, Short.class);
	getDevice().setConfiguration(illuminanceRange, integrationTime);
	getStatus(ConfigStatus.class).update(intent);
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(AmbientLightV2.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(AmbientLightV2.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(CallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(AmbientLightV2.PERIOD, Long.class);
	getDevice().setIlluminanceCallbackPeriod(period);
	getStatus(CallbackPeriodStatus.class).update(PERIOD, getDevice().getIlluminanceCallbackPeriod());
    }

    public void executeIntent(CallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(AmbientLightV2.THRESHOLD_OPTION, Character.class);
	short min = intent.getValue(AmbientLightV2.THRESHOLD_MIN, Short.class);
	short max = intent.getValue(AmbientLightV2.THRESHOLD_MAX, Short.class);
	getDevice().setIlluminanceCallbackThreshold(option, min, max);
	getStatus(CallbackThresholdStatus.class).update(intent);
    }

    @Override
    public void illuminance(long l) {
	getEvent(IlluminanceEvent.class).update(VALUE, l);
    }

    @Override
    public void illuminanceReached(long l) {
	getEvent(IlluminanceReachedEvent.class).update(VALUE, l);
    }

}
