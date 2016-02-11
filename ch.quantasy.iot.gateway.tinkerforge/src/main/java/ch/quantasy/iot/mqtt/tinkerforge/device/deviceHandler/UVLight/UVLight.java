/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.UVLight;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.UVLight.event.UVLightReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.UVLight.event.UVLightValueEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.UVLight.intent.DebouncePeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.UVLight.intent.UVLightCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.UVLight.intent.UVLightCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.UVLight.status.DebounceStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.UVLight.status.UVLightCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.UVLight.status.UVLightCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletUVLight;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class UVLight extends ADeviceHandler<BrickletUVLight> implements BrickletUVLight.UVLightListener, BrickletUVLight.UVLightReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "UVLight";
    }

    public UVLight(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(UVLightCallbackPeriodStatus.class, UVLightCallbackThresholdStatus.class, DebounceStatus.class);
	super.addEventClass(UVLightValueEvent.class, UVLightReachedEvent.class);
	super.addIntentClass(UVLightCallbackPeriodIntent.class, UVLightCallbackThresholdIntent.class, DebouncePeriodIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addUVLightListener(this);
	getDevice().addUVLightReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeUVLightListener(this);
	getDevice().removeUVLightReachedListener(this);
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(UVLight.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(UVLight.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(UVLightCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(UVLight.PERIOD, Long.class);
	getDevice().setUVLightCallbackPeriod(period);
	getStatus(UVLightCallbackPeriodStatus.class).update(PERIOD, getDevice().getUVLightCallbackPeriod());
    }

    public void executeIntent(UVLightCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(UVLight.THRESHOLD_OPTION, Character.class);
	if (option == 's') {
	    option = '<';
	}
	if (option == 'g') {
	    option = '>';
	}
	int min = intent.getValue(UVLight.THRESHOLD_MIN, Integer.class);
	int max = intent.getValue(UVLight.THRESHOLD_MAX, Integer.class);
	getDevice().setUVLightCallbackThreshold(option, min, max);
	getStatus(UVLightCallbackThresholdStatus.class).update(intent);
    }

    @Override
    public void uvLight(long l) {
	getEvent(UVLightValueEvent.class).update(VALUE, l);
    }

    @Override
    public void uvLightReached(long l) {
	getEvent(UVLightReachedEvent.class).update(VALUE, l);
    }

}
