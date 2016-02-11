package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.event.CountResetEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.event.CountValueEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.event.CountValueReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.event.PressedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.intent.CountCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.intent.CountCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.intent.CountResetIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.status.CountCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.status.CountCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletRotaryEncoder;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RotaryEncoder extends ADeviceHandler<BrickletRotaryEncoder> implements BrickletRotaryEncoder.CountListener, BrickletRotaryEncoder.CountReachedListener, BrickletRotaryEncoder.PressedListener, BrickletRotaryEncoder.ReleasedListener {

    public static final String ENABLED = "enabled";
    public static final String VALUE = "value";
    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";

    public String getApplicationName() {
	return "RotaryEncoder";
    }

    public RotaryEncoder(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addIntentClass(CountCallbackPeriodIntent.class, CountCallbackThresholdIntent.class, CountResetIntent.class);
	super.addEventClass(CountValueEvent.class, CountValueReachedEvent.class, PressedEvent.class, CountResetEvent.class);
	super.addStatusClass(CountCallbackThresholdStatus.class, CountCallbackPeriodStatus.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addCountListener(this);
	getDevice().addCountReachedListener(this);
	getDevice().addPressedListener(this);
	getDevice().addReleasedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeCountListener(this);
	getDevice().removeCountReachedListener(this);
	getDevice().removePressedListener(this);
	getDevice().removeReleasedListener(this);
    }

    public void executeIntent(CountResetIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().getCount(true);
	getEvent(CountResetEvent.class).update(intent);

    }

    public void executeIntent(CountCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().setCountCallbackPeriod(intent.getValue(PERIOD, Long.class));
	getStatus(CountCallbackPeriodStatus.class).update(intent);

    }

    public void executeIntent(CountCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(THRESHOLD_OPTION, Character.class);
	if (option == 's') {
	    option = '<';
	}
	if (option == 'g') {
	    option = '>';
	}
	int min = intent.getValue(THRESHOLD_MIN, Integer.class);
	int max = intent.getValue(THRESHOLD_MAX, Integer.class);
	getDevice().setCountCallbackThreshold(option, min, max);
	getStatus(CountCallbackThresholdStatus.class).update(intent);
    }

    @Override
    public void count(int i) {
	getEvent(CountValueEvent.class).update(VALUE, i);
    }

    @Override
    public void countReached(int i) {
	getEvent(CountValueReachedEvent.class).update(VALUE, i);
    }

    @Override
    public void pressed() {
	getEvent(PressedEvent.class).update(VALUE, true);
    }

    @Override
    public void released() {
	getEvent(PressedEvent.class).update(VALUE, false);
    }

}
