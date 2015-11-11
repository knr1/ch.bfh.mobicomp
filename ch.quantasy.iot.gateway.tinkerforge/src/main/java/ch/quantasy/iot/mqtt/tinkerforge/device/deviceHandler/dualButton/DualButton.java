/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualButton;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualButton.event.StateChangedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualButton.intent.DualLEDIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualButton.intent.LEDIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualButton.status.DualLEDStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletDualButton;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DualButton extends ADeviceHandler<BrickletDualButton> implements BrickletDualButton.StateChangedListener {

    public static final String LED = "led";
    public static final String STATE = "state";

    public static final String BUTTON1 = "button1";
    public static final String BUTTON2 = "button2";

    public static final String LED_L = "ledL";
    public static final String LED_R = "ledR";

    public String getApplicationName() {
	return "DualButton";
    }

    public DualButton(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addIntentClass(DualLEDIntent.class, LEDIntent.class);
	super.addStatusClass(DualLEDStatus.class);
	super.addEventClass(StateChangedEvent.class);
    }

    private void updateState() {
	BrickletDualButton.LEDState state;
	try {
	    state = getDevice().getLEDState();
	    getStatus(DualLEDStatus.class).update(LED_L, state.ledL);
	    getStatus(DualLEDStatus.class).update(LED_R, state.ledR);
	} catch (Throwable ex) {
	    Logger.getLogger(DualButton.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addStateChangedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeStateChangedListener(this);
    }

    /**
     * This method allows to describe the strategy of the DeviceHandler for any incoming intent. In this specific case
     * it simply dispatches every intent to the concrete execution.
     *
     * @param intent
     */
    @Override
    public void executeIntent(AnIntent intent) throws Throwable {
	if (intent instanceof DualLEDIntent) {
	    executeIntent((DualLEDIntent) intent);
	}
	if (intent instanceof LEDIntent) {
	    executeIntent((LEDIntent) intent);
	}
    }

    public void executeIntent(LEDIntent intent) throws Throwable {
	short led = intent.getValue(LED, Short.class);
	short state = intent.getValue(STATE, Short.class);
	getDevice().setSelectedLEDState(led, state);
	updateState();
    }

    public void executeIntent(DualLEDIntent intent) throws Throwable {
	short ledL = intent.getValue(LED_L, Short.class);
	short ledR = intent.getValue(LED_R, Short.class);
	getDevice().setLEDState(ledL, ledR);
	updateState();
    }

    @Override
    public void stateChanged(short s, short s1, short s2, short s3) {
	getEvent(StateChangedEvent.class).update(BUTTON1, s);
	getEvent(StateChangedEvent.class).update(BUTTON2, s1);
	getEvent(StateChangedEvent.class).update(LED_L, s2);
	getEvent(StateChangedEvent.class).update(LED_R, s3);
    }
}
