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
public class MonoflopIntent extends AnIntent<DualRelay> {

    public MonoflopIntent(DualRelay deviceHandler, String topic) {
	super(deviceHandler, topic, "monoflop");
	super.addDescription(DualRelay.MONOFLOP_ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(DualRelay.MONOFLOP_TIME, Long.class, "JSON", "0", "...", "" + Long.MAX_VALUE);
	super.addDescription(DualRelay.RELAY, Short.class, "JSON", "1", "2");
	super.addDescription(DualRelay.STATE, Boolean.class, "JSON", "true", "false");

    }

    public boolean isExecutable() {
	try {
	    return getContent(DualRelay.MONOFLOP_ENABLED).getValue(Boolean.class) && isTimeInRange() && isRelayInRange();
	} catch (Throwable th) {
	    return false;
	}
    }

    private boolean isTimeInRange() {
	return (getContent(DualRelay.MONOFLOP_TIME).getValue(Long.class) >= 0);
    }

    private boolean isRelayInRange() {
	int relay = getContent(DualRelay.RELAY).getValue(Integer.class);
	return (relay == 1 || relay == 2);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
