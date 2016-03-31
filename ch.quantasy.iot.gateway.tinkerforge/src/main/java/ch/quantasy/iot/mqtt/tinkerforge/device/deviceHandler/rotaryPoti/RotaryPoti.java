/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.event.AnalogValueEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.event.AnalogValueReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.event.PositionValueEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.event.PositionValueReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.intent.AnalogCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.intent.AnalogCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.intent.DebouncePeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.intent.PositionCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.intent.PositionCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.status.AnalogCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.status.AnalogCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.status.DebounceStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.status.PositionCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.status.PositionCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletRotaryPoti;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RotaryPoti extends ADeviceHandler<BrickletRotaryPoti> implements BrickletRotaryPoti.AnalogValueListener, BrickletRotaryPoti.AnalogValueReachedListener, BrickletRotaryPoti.PositionListener, BrickletRotaryPoti.PositionReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";

    public static final String VALUE = "value";

    @Override
    public String getApplicationName() {
	return "RotaryPoti";
    }

    public RotaryPoti(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(AnalogCallbackPeriodStatus.class, AnalogCallbackThresholdStatus.class, PositionCallbackPeriodStatus.class, PositionCallbackThresholdStatus.class, DebounceStatus.class);
	super.addEventClass(AnalogValueEvent.class, PositionValueReachedEvent.class, AnalogValueReachedEvent.class, PositionValueEvent.class);
	super.addIntentClass(AnalogCallbackPeriodIntent.class, AnalogCallbackThresholdIntent.class, PositionCallbackPeriodIntent.class, PositionCallbackThresholdIntent.class, DebouncePeriodIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addAnalogValueListener(this);
	getDevice().addAnalogValueReachedListener(this);
	getDevice().addPositionListener(this);
	getDevice().addPositionReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeAnalogValueListener(this);
	getDevice().removeAnalogValueReachedListener(this);
	getDevice().removePositionListener(this);
	getDevice().removePositionReachedListener(this);
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(RotaryPoti.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(RotaryPoti.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(AnalogCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(RotaryPoti.PERIOD, Long.class);
	getDevice().setAnalogValueCallbackPeriod(period);
	getStatus(AnalogCallbackPeriodStatus.class).update(PERIOD, getDevice().getAnalogValueCallbackPeriod());
    }

    public void executeIntent(PositionCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(RotaryPoti.PERIOD, Long.class);
	getDevice().setPositionCallbackPeriod(period);
	getStatus(PositionCallbackPeriodStatus.class).update(PERIOD, getDevice().getPositionCallbackPeriod());
    }

    public void executeIntent(AnalogCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(RotaryPoti.THRESHOLD_OPTION, Character.class);
	if (option == 's') {
	    option = '<';
	}
	if (option == 'g') {
	    option = '>';
	}
	int min = intent.getValue(RotaryPoti.THRESHOLD_MIN, Integer.class);
	int max = intent.getValue(RotaryPoti.THRESHOLD_MAX, Integer.class);
	getDevice().setAnalogValueCallbackThreshold(option, min, max);
	getStatus(AnalogCallbackThresholdStatus.class).update(intent);
    }

    public void executeIntent(PositionCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(RotaryPoti.THRESHOLD_OPTION, Character.class);
	if (option == 's') {
	    option = '<';
	}
	if (option == 'g') {
	    option = '>';
	}
	short min = intent.getValue(RotaryPoti.THRESHOLD_MIN, Short.class);
	short max = intent.getValue(RotaryPoti.THRESHOLD_MAX, Short.class);
	getDevice().setPositionCallbackThreshold(option, min, max);
	getStatus(PositionCallbackThresholdStatus.class).update(intent);
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
    public void position(short s) {
	getEvent(PositionValueEvent.class).update(VALUE, s);
    }

    @Override
    public void positionReached(short s) {
	getEvent(PositionValueReachedEvent.class).update(VALUE, s);
    }

}
