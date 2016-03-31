/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.RotaryPoti;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class PositionCallbackThresholdIntent extends AnIntent<RotaryPoti> {

    public PositionCallbackThresholdIntent(RotaryPoti deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "positionCallbackThreshold");
	super.addDescription(RotaryPoti.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "s", "g");
	super.addDescription(RotaryPoti.THRESHOLD_MIN, Short.class, "JSON", "0", "...", "100");
	super.addDescription(RotaryPoti.THRESHOLD_MAX, Short.class, "JSON", "0", "...", "100");
	super.addDescription(RotaryPoti.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return isEnabled() && isOptionInRange() && isMaxInRange() && isMaxInRange();
    }

    private boolean isEnabled() {
	return getContent(RotaryPoti.ENABLED).getValue(Boolean.class);
    }

    private boolean isOptionInRange() {
	char option = getContent(RotaryPoti.THRESHOLD_OPTION).getValue(Character.class);
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
	int min = getContent(RotaryPoti.THRESHOLD_MIN).getValue(Integer.class);
	return (min <= 4095 && min >= 0);
    }

    private boolean isMaxInRange() {
	int max = getContent(RotaryPoti.THRESHOLD_MAX).getValue(Integer.class);
	return (max <= 4095 && max >= 0);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
