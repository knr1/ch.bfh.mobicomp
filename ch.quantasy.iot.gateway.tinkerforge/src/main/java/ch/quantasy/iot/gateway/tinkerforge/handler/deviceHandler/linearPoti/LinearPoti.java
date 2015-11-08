/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti;

import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.event.AnalogValueEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.event.AnalogValueReachedEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.event.PositionValueEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.event.PositionValueReachedEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.intent.AnalogCallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.intent.AnalogCallbackThresholdIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.intent.DebouncePeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.intent.PositionCallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.intent.PositionCallbackThresholdIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.status.AnalogCallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.status.AnalogCallbackThresholdStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.status.DebounceStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.status.PositionCallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.status.PositionCallbackThresholdStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.stackHandler.MQTTTinkerforgeStackHandler;
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
    public static final String ENABLED = "enabled";

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
	if (intent instanceof PositionCallbackThresholdIntent) {
	    executeIntent((PositionCallbackThresholdIntent) intent);
	}
	if (intent instanceof AnalogCallbackThresholdIntent) {
	    executeIntent((AnalogCallbackThresholdIntent) intent);
	}
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
	int min = intent.getValue(LinearPoti.THRESHOLD_MIN, Integer.class);
	int max = intent.getValue(LinearPoti.THRESHOLD_MAX, Integer.class);
	getDevice().setAnalogValueCallbackThreshold(option, min, max);
	getStatus(AnalogCallbackThresholdStatus.class).update(intent);
    }

    public void executeIntent(PositionCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(LinearPoti.THRESHOLD_OPTION, Character.class);
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
