/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.RemoteSwitch;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RepeatsIntent extends AnIntent {

    public RepeatsIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "repeats");
	super.addDescription(RemoteSwitch.REPEATS, Short.class, "JSON", "0", "...", "" + Short.MAX_VALUE);
    }

    @Override
    public boolean isExecutable() {
	return isRepeatsInRange();
    }

    private boolean isRepeatsInRange() {
	short repeats = getContent(RemoteSwitch.REPEATS).getValue(Short.class);
	return (repeats >= 0);
    }

}
