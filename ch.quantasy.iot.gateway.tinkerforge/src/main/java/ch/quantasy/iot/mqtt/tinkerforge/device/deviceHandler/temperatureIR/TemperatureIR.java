/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.event.AmbientTemperatureEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.event.AmbientTemperatureReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.event.ObjectTemperatureEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.event.ObjectTemperatureReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.intent.AmbientTemperatureCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.intent.AmbientTemperatureCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.intent.DebouncePeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.intent.EmisivityIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.intent.ObjectTemperatureCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.intent.ObjectTemperatureCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.status.AmbientTemperatureCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.status.AmbientTemperatureCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.status.DebounceStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.status.EmissivityStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.status.ObjectTemperatureCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.status.ObjectTemperatureCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletTemperatureIR;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TemperatureIR extends ADeviceHandler<BrickletTemperatureIR> implements BrickletTemperatureIR.AmbientTemperatureListener, BrickletTemperatureIR.AmbientTemperatureReachedListener, BrickletTemperatureIR.ObjectTemperatureListener, BrickletTemperatureIR.ObjectTemperatureReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";
    public static final String ENABLED = "enabled";
    public static final String EMISSIVITY = "emissivity";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "TemperatureIR";
    }

    public TemperatureIR(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(AmbientTemperatureCallbackPeriodStatus.class, AmbientTemperatureCallbackThresholdStatus.class, ObjectTemperatureCallbackPeriodStatus.class, ObjectTemperatureCallbackThresholdStatus.class, DebounceStatus.class, EmissivityStatus.class);
	super.addEventClass(AmbientTemperatureEvent.class, AmbientTemperatureReachedEvent.class, ObjectTemperatureEvent.class, ObjectTemperatureReachedEvent.class);
	super.addIntentClass(AmbientTemperatureCallbackPeriodIntent.class, AmbientTemperatureCallbackThresholdIntent.class, ObjectTemperatureCallbackPeriodIntent.class, ObjectTemperatureCallbackThresholdIntent.class, DebouncePeriodIntent.class, EmisivityIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addAmbientTemperatureListener(this);
	getDevice().addAmbientTemperatureReachedListener(this);
	getDevice().addObjectTemperatureListener(this);
	getDevice().addObjectTemperatureReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeAmbientTemperatureListener(this);
	getDevice().removeAmbientTemperatureReachedListener(this);
	getDevice().removeObjectTemperatureListener(this);
	getDevice().removeObjectTemperatureReachedListener(this);
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(TemperatureIR.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(TemperatureIR.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(AmbientTemperatureCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(TemperatureIR.PERIOD, Long.class);
	getDevice().setAmbientTemperatureCallbackPeriod(period);
	getStatus(AmbientTemperatureCallbackPeriodStatus.class).update(PERIOD, getDevice().getAmbientTemperatureCallbackPeriod());
    }

    public void executeIntent(ObjectTemperatureCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(TemperatureIR.PERIOD, Long.class);
	getDevice().setObjectTemperatureCallbackPeriod(period);
	getStatus(ObjectTemperatureCallbackPeriodStatus.class).update(PERIOD, getDevice().getObjectTemperatureCallbackPeriod());
    }

    public void executeIntent(AmbientTemperatureCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(TemperatureIR.THRESHOLD_OPTION, Character.class);
	short min = intent.getValue(TemperatureIR.THRESHOLD_MIN, Short.class);
	short max = intent.getValue(TemperatureIR.THRESHOLD_MAX, Short.class);
	getDevice().setAmbientTemperatureCallbackThreshold(option, min, max);
	getStatus(AmbientTemperatureCallbackThresholdStatus.class).update(intent);
    }

    public void executeIntent(ObjectTemperatureCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(TemperatureIR.THRESHOLD_OPTION, Character.class);
	short min = intent.getValue(TemperatureIR.THRESHOLD_MIN, Short.class);
	short max = intent.getValue(TemperatureIR.THRESHOLD_MAX, Short.class);
	getDevice().setObjectTemperatureCallbackThreshold(option, min, max);
	getStatus(ObjectTemperatureCallbackThresholdStatus.class).update(intent);
    }

    public void executeIntent(EmisivityIntent intent) throws TimeoutException, NotConnectedException {
	int emissivity = intent.getContent(TemperatureIR.EMISSIVITY).getValue(Integer.class);

	getDevice().setEmissivity(emissivity);
	getStatus(EmissivityStatus.class).update(TemperatureIR.EMISSIVITY, getDevice().getEmissivity());
    }

    @Override
    public void ambientTemperature(short s) {
	getEvent(AmbientTemperatureEvent.class).update(VALUE, s);
    }

    @Override
    public void ambientTemperatureReached(short s) {
	getEvent(AmbientTemperatureReachedEvent.class).update(VALUE, s);
    }

    @Override
    public void objectTemperature(short s) {
	getEvent(ObjectTemperatureEvent.class).update(VALUE, s);
    }

    @Override
    public void objectTemperatureReached(short s) {
	getEvent(ObjectTemperatureReachedEvent.class).update(VALUE, s);
    }

}
