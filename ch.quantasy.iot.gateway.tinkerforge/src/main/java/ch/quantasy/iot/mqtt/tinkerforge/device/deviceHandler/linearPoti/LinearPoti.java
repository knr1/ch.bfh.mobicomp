/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.event.AnalogValueEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.event.AnalogValueReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.event.PositionValueEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.event.PositionValueReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.intent.AnalogCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.intent.AnalogCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.intent.DebouncePeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.intent.PositionCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.intent.PositionCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.status.AnalogCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.status.AnalogCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.status.DebounceStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.status.PositionCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.status.PositionCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletLinearPoti;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LinearPoti extends ADeviceHandler<BrickletLinearPoti> implements BrickletLinearPoti.AnalogValueListener, BrickletLinearPoti.AnalogValueReachedListener, BrickletLinearPoti.PositionListener, BrickletLinearPoti.PositionReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "LinearPoti";
    }

    public LinearPoti(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(AnalogCallbackPeriodStatus.class, AnalogCallbackThresholdStatus.class, PositionCallbackPeriodStatus.class, PositionCallbackThresholdStatus.class, DebounceStatus.class);
	super.addEventClass(AnalogValueEvent.class, PositionValueEvent.class, AnalogValueReachedEvent.class, PositionValueReachedEvent.class);
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
	long period = intent.getValue(LinearPoti.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(LinearPoti.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(AnalogCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(LinearPoti.PERIOD, Long.class);
	getDevice().setAnalogValueCallbackPeriod(period);
	getStatus(AnalogCallbackPeriodStatus.class).update(PERIOD, getDevice().getAnalogValueCallbackPeriod());
    }

    public void executeIntent(PositionCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(LinearPoti.PERIOD, Long.class);
	getDevice().setPositionCallbackPeriod(period);
	getStatus(PositionCallbackPeriodStatus.class).update(PERIOD, getDevice().getPositionCallbackPeriod());
    }

    public void executeIntent(AnalogCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(LinearPoti.THRESHOLD_OPTION, Character.class);
	if (option == 's') {
	    option = '<';
	}
	if (option == 'g') {
	    option = '>';
	}
	int min = intent.getValue(LinearPoti.THRESHOLD_MIN, Integer.class);
	int max = intent.getValue(LinearPoti.THRESHOLD_MAX, Integer.class);
	getDevice().setAnalogValueCallbackThreshold(option, min, max);
	getStatus(AnalogCallbackThresholdStatus.class).update(intent);
    }

    public void executeIntent(PositionCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(LinearPoti.THRESHOLD_OPTION, Character.class);
	if (option == 's') {
	    option = '<';
	}
	if (option == 'g') {
	    option = '>';
	}
	short min = intent.getValue(LinearPoti.THRESHOLD_MIN, Short.class);
	short max = intent.getValue(LinearPoti.THRESHOLD_MAX, Short.class);
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
    public void position(int i) {
	getEvent(PositionValueEvent.class).update(VALUE, i);
    }

    @Override
    public void positionReached(int i) {
	getEvent(PositionValueReachedEvent.class).update(VALUE, i);
    }

}
