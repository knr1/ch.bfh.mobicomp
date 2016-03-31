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
public class MonoflopIntent extends AnIntent<SolidStateRelay> {

    public MonoflopIntent(SolidStateRelay deviceHandler, String topic) {
	super(deviceHandler, topic, "monoflop");
	super.addDescription(SolidStateRelay.STATE, Boolean.class, "JSON", "true", "false");
	super.addDescription(SolidStateRelay.MONOFLOP_TIME, Long.class, "JSON", "0", "...", "" + Long.MAX_VALUE);
	super.addDescription(SolidStateRelay.ENABLED, Boolean.class, "JSON", "true", "false");

    }

    public boolean isExecutable() {
	try {
	    return getContent(SolidStateRelay.ENABLED).getValue(Boolean.class) && isTimeInRange() && getContent(SolidStateRelay.ENABLED).getValue(Boolean.class);
	} catch (Throwable th) {
	    return false;
	}
    }

    private boolean isTimeInRange() {
	return (getContent(SolidStateRelay.MONOFLOP_TIME).getValue(Long.class) >= 0);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
