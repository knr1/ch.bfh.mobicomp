/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.tilt;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.tilt.event.TiltStateEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.tilt.intent.TiltStateCallbackIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.tilt.status.TiltStateCallbackStatus;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletTilt;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class Tilt extends ADeviceHandler<BrickletTilt> implements BrickletTilt.TiltStateListener {

    public static final String ENABLED = "enabled";

    public static final String STATE = "state";

    public String getApplicationName() {
	return "Tilt";
    }

    public Tilt(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(TiltStateCallbackStatus.class);
	super.addEventClass(TiltStateEvent.class);
	super.addIntentClass(TiltStateCallbackIntent.class);
    }

    public Class[] getIntentClasses() {
	return new Class[]{TiltStateCallbackIntent.class};
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addTiltStateListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeTiltStateListener(this);
    }

    /**
     * This method allows to describe the strategy of the DeviceHandler for any incoming intent. In this specific case
     * it simply dispatches every intent to the concrete execution.
     *
     * @param intent
     */
    public void executeIntent(AnIntent intent) throws Throwable {
	if (intent instanceof TiltStateCallbackIntent) {
	    executeIntent((TiltStateCallbackIntent) intent);
	}
    }

    public void executeIntent(TiltStateCallbackIntent intent) throws TimeoutException, NotConnectedException {
	if (intent.getContent(ENABLED).getValue(Boolean.class)) {
	    getDevice().enableTiltStateCallback();
	} else {
	    getDevice().disableTiltStateCallback();
	}
	getStatus(TiltStateCallbackStatus.class).update(intent);
    }

    @Override
    public void tiltState(short s) {
	getEvent(TiltStateEvent.class).update(STATE, s);
    }

}
