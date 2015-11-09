/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualButton.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualButton.DualButton;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LEDIntent extends AnIntent {

    public LEDIntent(AHandler deviceHandler, String topic) {
	super(deviceHandler, topic, "LED");
	super.addDescription(DualButton.LED, Short.class, "JSON", "1", "2");
	super.addDescription(DualButton.STATE, Short.class, "JSON", "0", "1", "2", "3");
	super.addDescription(DualButton.ENABLED, Boolean.class, "JSON", "true", "false");

    }

    @Override
    public boolean isExecutable() {
	try {
	    return getContent(DualButton.ENABLED).getValue(Boolean.class) && isLEDInRange() && isStateInRange();
	} catch (Throwable th) {
	    return false;
	}
    }

    private boolean isStateInRange() {
	short led = getContent(DualButton.STATE).getValue(Short.class);
	return (led >= 0 || led <= 3);
    }

    private boolean isLEDInRange() {
	short led = getContent(DualButton.LED).getValue(Short.class);
	return (led == 1 || led == 2);
    }
}
