/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay;

import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay.event.DualRelayEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay.event.MonoflopEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay.intent.DualRelayIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay.intent.MonoflopIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay.status.DualRelayStatus;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletDualRelay;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DualRelay extends ADeviceHandler<BrickletDualRelay> implements BrickletDualRelay.MonoflopDoneListener {

    public static final String MONOFLOP_ENABLED = "enabled";
    public static final String MONOFLOP_TIME = "time";
    public static final String MONOFLOP_RELAY = "relay";
    public static final String MONOFLOP_STATE = "state";

    public static final String DUALRELAY_RELAY1 = "relay1";
    public static final String DUALRELAY_RELAY2 = "relay2";
    public static final String DUALRELAY_ENABLED = "enabled";

    public String getApplicationName() {
	return "DualRelay";
    }

    public DualRelay(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addIntentClass(MonoflopIntent.class, DualRelayIntent.class);
	super.addStatusClass(DualRelayStatus.class);
	super.addEventClass(DualRelayEvent.class, MonoflopEvent.class);
    }

    @Override
    public void monoflopDone(short s, boolean bln) {
	updateState();
    }

    private void updateState() {
	BrickletDualRelay.State state;
	try {
	    state = getDevice().getState();
	    getStatus(DualRelayStatus.class).updateState(state);
	    getEvent(DualRelayEvent.class).updateState(state);
	} catch (Throwable ex) {
	    Logger.getLogger(DualRelay.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * This method allows to describe the strategy of the DeviceHandler for any incoming intent. In this specific case
     * it simply dispatches every intent to the concrete execution.
     *
     * @param intent
     */
    @Override
    public void executeIntent(AnIntent intent) throws Throwable {
	if (intent instanceof MonoflopIntent) {
	    executeIntent((MonoflopIntent) intent);
	}
	if (intent instanceof DualRelayIntent) {
	    executeIntent((DualRelayIntent) intent);
	}
    }

    public void executeIntent(DualRelayIntent intent) throws Throwable {
	boolean relay1 = intent.getTriple(DUALRELAY_RELAY1).getValue(Boolean.class);
	boolean relay2 = intent.getTriple(DUALRELAY_RELAY2).getValue(Boolean.class);
	getDevice().setState(relay1, relay2);
	updateState();
    }

    public void executeIntent(MonoflopIntent intent) throws TimeoutException, NotConnectedException {
	short relay = intent.getTriple(MONOFLOP_RELAY).getValue(Short.class);
	boolean state = intent.getTriple(MONOFLOP_STATE).getValue(Boolean.class);
	long time = intent.getTriple(MONOFLOP_TIME).getValue(Long.class);

	getDevice().setMonoflop(relay, state, time);
	updateState();
	getEvent(MonoflopEvent.class).updateIntent(intent);
    }
}
