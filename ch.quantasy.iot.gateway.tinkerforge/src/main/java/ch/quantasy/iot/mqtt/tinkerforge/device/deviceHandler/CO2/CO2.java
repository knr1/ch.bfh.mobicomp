/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.CO2;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.CO2.event.AirPressureReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.CO2.event.AirPressureValueEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.CO2.intent.AirPressureCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.CO2.intent.AirPressureCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.CO2.intent.DebouncePeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.CO2.status.CO2ConcentrationCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.CO2.status.AirPressureCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.CO2.status.DebounceStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletCO2;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CO2 extends ADeviceHandler<BrickletCO2> implements BrickletCO2.CO2ConcentrationListener, BrickletCO2.CO2ConcentrationReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";
    public static final String ENABLED = "enabled";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "CO2";
    }

    public CO2(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(CO2ConcentrationCallbackPeriodStatus.class, AirPressureCallbackThresholdStatus.class, DebounceStatus.class);
	super.addEventClass(AirPressureValueEvent.class, AirPressureReachedEvent.class);
	super.addIntentClass(AirPressureCallbackPeriodIntent.class, AirPressureCallbackThresholdIntent.class, DebouncePeriodIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addCO2ConcentrationListener(this);
	getDevice().addCO2ConcentrationReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeCO2ConcentrationListener(this);
	getDevice().removeCO2ConcentrationReachedListener(this);
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(CO2.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(CO2.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(AirPressureCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(CO2.PERIOD, Long.class);
	getDevice().setCO2ConcentrationCallbackPeriod(period);
	getStatus(CO2ConcentrationCallbackPeriodStatus.class).update(PERIOD, getDevice().getCO2ConcentrationCallbackPeriod());
    }

    public void executeIntent(AirPressureCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(CO2.THRESHOLD_OPTION, Character.class);
	int min = intent.getValue(CO2.THRESHOLD_MIN, Integer.class);
	int max = intent.getValue(CO2.THRESHOLD_MAX, Integer.class);
	getDevice().setCO2ConcentrationCallbackThreshold(option, min, max);
	getStatus(AirPressureCallbackThresholdStatus.class).update(intent);
    }

    @Override
    public void co2Concentration(int i) {
	getEvent(AirPressureValueEvent.class).update(VALUE, i);
    }

    @Override
    public void co2ConcentrationReached(int i) {
	getEvent(AirPressureReachedEvent.class).update(VALUE, i);
    }

}
