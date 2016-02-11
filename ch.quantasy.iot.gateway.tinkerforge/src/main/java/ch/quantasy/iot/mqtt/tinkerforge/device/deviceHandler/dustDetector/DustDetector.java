/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector.event.DustDensityReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector.event.DustDensityValueEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector.intent.DebouncePeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector.intent.DustDensityCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector.intent.DustDensityCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector.status.DebounceStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector.status.DustDensityCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector.status.DustDensityCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletDustDetector;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DustDetector extends ADeviceHandler<BrickletDustDetector> implements BrickletDustDetector.DustDensityListener, BrickletDustDetector.DustDensityReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "DustDetector";
    }

    public DustDetector(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(DebounceStatus.class, DustDensityCallbackPeriodStatus.class, DustDensityCallbackThresholdStatus.class);
	super.addEventClass(DustDensityReachedEvent.class, DustDensityValueEvent.class);
	super.addIntentClass(DebouncePeriodIntent.class, DustDensityCallbackPeriodIntent.class, DustDensityCallbackThresholdIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addDustDensityListener(this);
	getDevice().addDustDensityReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeDustDensityListener(this);
	getDevice().removeDustDensityReachedListener(this);
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(DustDetector.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(DustDetector.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(DustDensityCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(DustDetector.PERIOD, Long.class);
	getDevice().setDustDensityCallbackPeriod(period);
	getStatus(DustDensityCallbackPeriodStatus.class).update(PERIOD, getDevice().getDustDensityCallbackPeriod());
    }

    public void executeIntent(DustDensityCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(DustDetector.THRESHOLD_OPTION, Character.class);
	if (option == 's') {
	    option = '<';
	}
	if (option == 'g') {
	    option = '>';
	}
	int min = intent.getValue(DustDetector.THRESHOLD_MIN, Integer.class);
	int max = intent.getValue(DustDetector.THRESHOLD_MAX, Integer.class);
	getDevice().setDustDensityCallbackThreshold(option, min, max);
	getStatus(DustDensityCallbackThresholdStatus.class).update(intent);
    }

    @Override
    public void dustDensity(int i) {
	getEvent(DustDensityValueEvent.class).update(VALUE, i);
    }

    @Override
    public void dustDensityReached(int i) {
	getEvent(DustDensityReachedEvent.class).update(VALUE, i);
    }

}
