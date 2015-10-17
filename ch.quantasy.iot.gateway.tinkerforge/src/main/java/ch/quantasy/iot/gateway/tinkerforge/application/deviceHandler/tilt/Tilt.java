/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.tilt;

import ch.quantasy.iot.gateway.tinkerforge.application.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.tilt.event.TiltStateEvent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.tilt.intent.TiltStateCallbackIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.tilt.status.TiltStateCallbackStatus;
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

    public String getApplicationName() {
	return "Tilt";
    }

    public Tilt(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
    }

    public Class[] getIntentClasses() {
	return new Class[]{TiltStateCallbackIntent.class};
    }

    @Override
    protected Class[] getEventClasses() {
	return new Class[]{TiltStateEvent.class};
    }

    @Override
    protected Class[] getStatusClasses() {
	return new Class[]{TiltStateCallbackStatus.class};
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
	if (!intent.isExecutable()) {
	    return;
	}
	if (intent.enabled) {
	    getDevice().enableTiltStateCallback();
	} else {
	    getDevice().disableTiltStateCallback();
	}
	getStatus(TiltStateCallbackStatus.class).updateIntent(intent);
    }

    @Override
    public void tiltState(short s) {
	getEvent(TiltStateEvent.class).updateTiltState(s);
    }

}
