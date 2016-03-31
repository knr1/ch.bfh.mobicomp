/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.LinearPoti;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class PositionCallbackThresholdIntent extends AnIntent<LinearPoti> {

    public PositionCallbackThresholdIntent(LinearPoti deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "positionCallbackThreshold");
	super.addDescription(LinearPoti.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "s", "g");
	super.addDescription(LinearPoti.THRESHOLD_MIN, Short.class, "JSON", "0", "...", "100");
	super.addDescription(LinearPoti.THRESHOLD_MAX, Short.class, "JSON", "0", "...", "100");
	super.addDescription(LinearPoti.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return isEnabled() && isOptionInRange() && isMaxInRange() && isMaxInRange();
    }

    private boolean isEnabled() {
	return getContent(LinearPoti.ENABLED).getValue(Boolean.class);
    }

    private boolean isOptionInRange() {
	char option = getContent(LinearPoti.THRESHOLD_OPTION).getValue(Character.class);
	switch (option) {
	    case 'x':
	    case 'o':
	    case 'i':
	    case 's': //<
	    case 'g': //>
		return true;
	}
	return false;
    }

    private boolean isMinInRange() {
	int min = getContent(LinearPoti.THRESHOLD_MIN).getValue(Integer.class);
	return (min <= 4095 && min >= 0);
    }

    private boolean isMaxInRange() {
	int max = getContent(LinearPoti.THRESHOLD_MAX).getValue(Integer.class);
	return (max <= 4095 && max >= 0);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
