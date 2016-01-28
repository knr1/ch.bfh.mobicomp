/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay.DualRelay;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RelayIntent extends AnIntent<DualRelay> {

    public RelayIntent(DualRelay deviceHandler, String topic) {
	super(deviceHandler, topic, "relay");
	super.addDescription(DualRelay.RELAY, Short.class, "JSON", "1", "2");
	super.addDescription(DualRelay.STATE, Boolean.class, "JSON", "true", "false");
	super.addDescription(DualRelay.ENABLED, Boolean.class, "JSON", "true", "false");

    }

    @Override
    public boolean isExecutable() {
	try {
	    return getContent(DualRelay.ENABLED).getValue(Boolean.class) && isRelayInRange() && isStateInRange();
	} catch (Throwable th) {
	    return false;
	}
    }

    private boolean isStateInRange() {
	return getContent(DualRelay.STATE).getValue(Boolean.class) | true;
    }

    private boolean isRelayInRange() {
	short relay = getContent(DualRelay.RELAY).getValue(Short.class);
	return (relay == 1 || relay == 2);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }
}
