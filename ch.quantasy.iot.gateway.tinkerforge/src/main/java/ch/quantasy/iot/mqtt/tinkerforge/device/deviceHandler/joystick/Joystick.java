/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.event.AnalogValueEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.event.AnalogValueReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.event.CalibrateEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.event.PositionValueEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.event.PositionValueReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.event.PressedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.intent.AnalogCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.intent.AnalogCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.intent.CalibrateIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.intent.DebouncePeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.intent.PositionCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.intent.PositionCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.status.AnalogCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.status.AnalogCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.status.DebounceStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.status.PositionCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.status.PositionCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletJoystick;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class Joystick extends ADeviceHandler<BrickletJoystick> implements BrickletJoystick.PressedListener, BrickletJoystick.ReleasedListener, BrickletJoystick.AnalogValueListener, BrickletJoystick.AnalogValueReachedListener, BrickletJoystick.PositionListener, BrickletJoystick.PositionReachedListener {

    public static final String VALUE = "value";
    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN_X = "minX";
    public static final String THRESHOLD_MAX_X = "maxX";
    public static final String THRESHOLD_MIN_Y = "minY";
    public static final String THRESHOLD_MAX_Y = "maxY";
    public static final String ENABLED = "enabled";

    public static final String VALUE_X = "valueX";
    public static final String VALUE_Y = "valueY";

    public String getApplicationName() {
	return "Joystick";
    }

    public Joystick(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(AnalogCallbackPeriodStatus.class, AnalogCallbackThresholdStatus.class, PositionCallbackPeriodStatus.class, PositionCallbackThresholdStatus.class, DebounceStatus.class);
	super.addEventClass(PressedEvent.class, AnalogValueEvent.class, PositionValueEvent.class, AnalogValueReachedEvent.class, PositionValueReachedEvent.class);
	super.addIntentClass(CalibrateIntent.class, AnalogCallbackPeriodIntent.class, AnalogCallbackThresholdIntent.class, PositionCallbackPeriodIntent.class, PositionCallbackThresholdIntent.class, DebouncePeriodIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addAnalogValueListener(this);
	getDevice().addAnalogValueReachedListener(this);
	getDevice().addPositionListener(this);
	getDevice().addPositionReachedListener(this);
	getDevice().addPressedListener(this);
	getDevice().addReleasedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeAnalogValueListener(this);
	getDevice().removeAnalogValueReachedListener(this);
	getDevice().removePositionListener(this);
	getDevice().removePositionReachedListener(this);
	getDevice().removePressedListener(this);
	getDevice().removeReleasedListener(this);
    }

    public void executeIntent(CalibrateIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().calibrate();
	getEvent(CalibrateEvent.class).update(VALUE, true);
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Joystick.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(Joystick.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(AnalogCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Joystick.PERIOD, Long.class);
	getDevice().setAnalogValueCallbackPeriod(period);
	getStatus(AnalogCallbackPeriodStatus.class).update(PERIOD, getDevice().getAnalogValueCallbackPeriod());
    }

    public void executeIntent(PositionCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Joystick.PERIOD, Long.class);
	getDevice().setPositionCallbackPeriod(period);
	getStatus(PositionCallbackPeriodStatus.class).update(PERIOD, getDevice().getPositionCallbackPeriod());
    }

    public void executeIntent(AnalogCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(Joystick.THRESHOLD_OPTION, Character.class);
	if (option == 's') {
	    option = '<';
	}
	if (option == 'g') {
	    option = '>';
	}
	int minX = intent.getValue(Joystick.THRESHOLD_MIN_X, Integer.class);
	int maxX = intent.getValue(Joystick.THRESHOLD_MAX_X, Integer.class);
	int minY = intent.getValue(Joystick.THRESHOLD_MIN_Y, Integer.class);
	int maxY = intent.getValue(Joystick.THRESHOLD_MAX_Y, Integer.class);
	getDevice().setAnalogValueCallbackThreshold(option, minX, maxX, minY, maxX);
	getStatus(AnalogCallbackThresholdStatus.class).update(intent);
    }

    public void executeIntent(PositionCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(Joystick.THRESHOLD_OPTION, Character.class);
	if (option == 's') {
	    option = '<';
	}
	if (option == 'g') {
	    option = '>';
	}
	short minX = intent.getValue(Joystick.THRESHOLD_MIN_X, Short.class);
	short maxX = intent.getValue(Joystick.THRESHOLD_MAX_X, Short.class);
	short minY = intent.getValue(Joystick.THRESHOLD_MIN_Y, Short.class);
	short maxY = intent.getValue(Joystick.THRESHOLD_MAX_Y, Short.class);
	getDevice().setPositionCallbackThreshold(option, minX, maxX, minY, minY);
	getStatus(PositionCallbackThresholdStatus.class).update(intent);
    }

    @Override
    public void analogValue(int i, int i1) {
	IMqttToken token = null;
	try {
	    getEvent(AnalogValueEvent.class).update(VALUE_X, i);
	    while (token == null) {
		getEvent(AnalogValueEvent.class).update(VALUE_Y, i1);
	    }
	    token.waitForCompletion(10);
	} catch (Exception ex) {
	    Logger.getLogger(Joystick.class.getName()).log(Level.SEVERE, null, ex);
	    System.out.println(token.getException());
	}
    }

    @Override
    public void analogValueReached(int i, int i1) {
	try {
	    getEvent(AnalogValueReachedEvent.class).update(VALUE_X, i);
	    IMqttToken token = null;
	    while (token == null) {
		getEvent(AnalogValueReachedEvent.class).update(VALUE_Y, i);
	    }
	    token.waitForCompletion(10);

	} catch (Exception ex) {
	    Logger.getLogger(Joystick.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void position(short s, short s1) {
	IMqttToken token = null;

	try {
	    getEvent(PositionValueEvent.class
	    ).update(VALUE_X, s);
	    while (token
		    == null) {
		token = getEvent(PositionValueEvent.class).update(VALUE_Y, s1);
	    }

	    token.waitForCompletion(
		    10);
	} catch (Exception ex) {
	    Logger.getLogger(Joystick.class
		    .getName()).log(Level.SEVERE, null, ex);
	    System.out.println(token.getException());

	}

    }

    @Override
    public
	    void positionReached(short s, short s1) {
	try {
	    getEvent(PositionValueEvent.class
	    ).update(VALUE_X, s);
	    IMqttToken token = null;
	    while (token == null) {
		getEvent(PositionValueEvent.class
		).update(VALUE_Y, s1);
	    }
	    token.waitForCompletion(10);
	} catch (Exception ex) {
	    Logger.getLogger(Joystick.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public
	    void pressed() {
	getEvent(PressedEvent.class
	).update(VALUE, true);
    }

    @Override
    public
	    void released() {
	getEvent(PressedEvent.class
	).update(VALUE, false);
    }

}
