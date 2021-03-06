/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.RotaryEncoder;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CountCallbackThresholdIntent extends AnIntent<RotaryEncoder> {

    public CountCallbackThresholdIntent(RotaryEncoder deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "countCallbackThreshold");
	super.addDescription(RotaryEncoder.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "s", "g");
	super.addDescription(RotaryEncoder.THRESHOLD_MIN, Short.class, "JSON", "" + Integer.MIN_VALUE, "...", "" + Integer.MAX_VALUE);
	super.addDescription(RotaryEncoder.THRESHOLD_MAX, Short.class, "JSON", "" + Integer.MIN_VALUE, "...", "" + Integer.MAX_VALUE);
	super.addDescription(RotaryEncoder.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return isEnabled() && isOptionInRange() && isMaxInRange() && isMaxInRange();
    }

    private boolean isEnabled() {
	return getContent(RotaryEncoder.ENABLED).getValue(Boolean.class);
    }

    private boolean isOptionInRange() {
	char option = getContent(RotaryEncoder.THRESHOLD_OPTION).getValue(Character.class);
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
	int min = getContent(RotaryEncoder.THRESHOLD_MIN).getValue(Integer.class);
	return (min <= Integer.MAX_VALUE && min >= Integer.MIN_VALUE);
    }

    private boolean isMaxInRange() {
	int max = getContent(RotaryEncoder.THRESHOLD_MAX).getValue(Integer.class);
	return (max <= Integer.MAX_VALUE && max >= Integer.MIN_VALUE);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
