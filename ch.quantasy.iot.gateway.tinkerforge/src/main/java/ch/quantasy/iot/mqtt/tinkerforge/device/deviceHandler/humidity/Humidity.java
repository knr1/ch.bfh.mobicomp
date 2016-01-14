/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.event.AnalogValueEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.event.AnalogValueReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.event.HumidityValueEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.event.HumidityValueReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.intent.AnalogCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.intent.AnalogCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.intent.DebouncePeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.intent.HumidityCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.intent.PositionCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.status.AnalogCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.status.AnalogCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.status.DebounceStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.status.HumidityCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.status.HumidityCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
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
	getStatus(AnalogCallbackThresholdStatus.class).update(intent);
    }

    public void executeIntent(HumidityCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(Humidity.THRESHOLD_OPTION, Character.class);
	short min = intent.getValue(Humidity.THRESHOLD_MIN, Short.class);
	short max = intent.getValue(Humidity.THRESHOLD_MAX, Short.class);
	getDevice().setHumidityCallbackThreshold(option, min, max);
	getStatus(HumidityCallbackThresholdStatus.class).update(intent);
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
