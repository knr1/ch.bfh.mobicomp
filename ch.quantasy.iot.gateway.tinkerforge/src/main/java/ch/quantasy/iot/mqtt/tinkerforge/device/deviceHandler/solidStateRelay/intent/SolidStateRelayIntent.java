/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.solidStateRelay.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.solidStateRelay.SolidStateRelay;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SolidStateRelayIntent extends AnIntent<SolidStateRelay> {

    public SolidStateRelayIntent(SolidStateRelay deviceHandler, String topic) {
	super(deviceHandler, topic, "relay");
	super.addDescription(SolidStateRelay.STATE, Boolean.class, "JSON", "true", "false");
    }

    @Override
    public boolean isExecutable() {
	try {
	    return isStateInRange();
	} catch (Throwable th) {
	    return false;
	}
    }

    private boolean isStateInRange() {
	return getContent(SolidStateRelay.STATE).getValue(Boolean.class) | true;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }
}
