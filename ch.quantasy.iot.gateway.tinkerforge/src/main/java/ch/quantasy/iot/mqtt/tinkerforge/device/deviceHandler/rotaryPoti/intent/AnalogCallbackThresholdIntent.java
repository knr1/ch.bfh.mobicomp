/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.LinearPoti;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryPoti.RotaryPoti;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AnalogCallbackThresholdIntent extends AnIntent<RotaryPoti> {

    public AnalogCallbackThresholdIntent(RotaryPoti deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "analogCallbackThreshold");
	super.addDescription(RotaryPoti.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "s", "g");
	super.addDescription(RotaryPoti.THRESHOLD_MIN, Integer.class, "JSON", "0", "...", "4095");
	super.addDescription(RotaryPoti.THRESHOLD_MAX, Integer.class, "JSON", "0", "...", "4095");
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
