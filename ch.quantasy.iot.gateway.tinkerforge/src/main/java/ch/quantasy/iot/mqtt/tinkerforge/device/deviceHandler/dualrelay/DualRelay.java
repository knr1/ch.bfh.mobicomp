/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay.event.DualRelayEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay.event.MonoflopEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay.intent.DualRelayIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay.intent.MonoflopIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay.intent.RelayIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay.status.DualRelayStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
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
    public static final String RELAY = "relay";
    public static final String STATE = "state";

    public static final String DUALRELAY_RELAY1 = "relay1";
    public static final String DUALRELAY_RELAY2 = "relay2";
    public static final String DUALRELAY_ENABLED = "enabled";

    public String getApplicationName() {
	return "DualRelay";
    }

    public DualRelay(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addIntentClass(MonoflopIntent.class, DualRelayIntent.class, RelayIntent.class);
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
	    getStatus(DualRelayStatus.class).update(DUALRELAY_RELAY1, state.relay1);
	    getStatus(DualRelayStatus.class).update(DUALRELAY_RELAY2, state.relay2);
	    getEvent(DualRelayEvent.class).update(DUALRELAY_RELAY1, state.relay1);
	    getEvent(DualRelayEvent.class).update(DUALRELAY_RELAY2, state.relay2);
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
	if (intent instanceof RelayIntent) {
	    executeIntent((RelayIntent) intent);
	}
    }

    public void executeIntent(RelayIntent intent) throws Throwable {
	short relay = intent.getValue(RELAY, Short.class);
	boolean state = intent.getValue(STATE, Boolean.class);
	getDevice().setSelectedState(relay, state);
	updateState();
    }

    public void executeIntent(DualRelayIntent intent) throws Throwable {
	boolean relay1 = intent.getValue(DUALRELAY_RELAY1, Boolean.class);
	boolean relay2 = intent.getValue(DUALRELAY_RELAY2, Boolean.class);
	getDevice().setState(relay1, relay2);
	updateState();
    }

    public void executeIntent(MonoflopIntent intent) throws TimeoutException, NotConnectedException {
	short relay = intent.getValue(RELAY, Short.class);
	boolean state = intent.getValue(STATE, Boolean.class);
	long time = intent.getValue(MONOFLOP_TIME, Long.class);

	getDevice().setMonoflop(relay, state, time);
	updateState();
	getEvent(MonoflopEvent.class).update(intent);
    }
}
