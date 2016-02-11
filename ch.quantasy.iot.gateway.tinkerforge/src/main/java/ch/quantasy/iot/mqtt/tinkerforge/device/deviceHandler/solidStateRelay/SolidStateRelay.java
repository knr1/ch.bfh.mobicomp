/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.solidStateRelay;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.solidStateRelay.event.MonoflopEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.solidStateRelay.event.SolidStateRelayEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.solidStateRelay.intent.MonoflopIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.solidStateRelay.intent.SolidStateRelayIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.solidStateRelay.status.SolidStateRelayStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletSolidStateRelay;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SolidStateRelay extends ADeviceHandler<BrickletSolidStateRelay> implements BrickletSolidStateRelay.MonoflopDoneListener {

    public static final String MONOFLOP_TIME = "time";
    public static final String STATE = "state";

    public String getApplicationName() {
	return "SolidStateRelay";
    }

    public SolidStateRelay(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addIntentClass(MonoflopIntent.class, SolidStateRelayIntent.class);
	super.addStatusClass(SolidStateRelayStatus.class);
	super.addEventClass(SolidStateRelayEvent.class, MonoflopEvent.class);
    }

    private void updateState() {
	try {
	    boolean state = getDevice().getState();
	    getStatus(SolidStateRelayStatus.class).update(STATE, state);
	    getEvent(SolidStateRelayEvent.class).update(STATE, state);
	} catch (Throwable ex) {
	    Logger.getLogger(SolidStateRelay.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addMonoflopDoneListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeMonoflopDoneListener(this);
    }

    public void executeIntent(SolidStateRelayIntent intent) throws Throwable {
	boolean state = intent.getValue(STATE, Boolean.class);
	getDevice().setState(state);
	updateState();
    }

    public void executeIntent(MonoflopIntent intent) throws TimeoutException, NotConnectedException {
	boolean state = intent.getValue(STATE, Boolean.class);
	long time = intent.getValue(MONOFLOP_TIME, Long.class);

	getDevice().setMonoflop(state, time);
	updateState();
	getEvent(MonoflopEvent.class).update(intent);
    }

    @Override
    public void monoflopDone(boolean bln) {
	updateState();
    }
}
