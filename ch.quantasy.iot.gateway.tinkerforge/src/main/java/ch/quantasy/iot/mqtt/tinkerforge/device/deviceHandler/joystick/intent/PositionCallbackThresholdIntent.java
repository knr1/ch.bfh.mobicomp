/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.intent;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.Joystick;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class PositionCallbackThresholdIntent extends AnIntent {

    public PositionCallbackThresholdIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "positionCallbackThreshold");
	super.addDescription(Joystick.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(Joystick.THRESHOLD_MIN_X, Short.class, "JSON", "-100", "...", "100");
	super.addDescription(Joystick.THRESHOLD_MAX_X, Short.class, "JSON", "-100", "...", "100");
	super.addDescription(Joystick.THRESHOLD_MIN_Y, Short.class, "JSON", "-100", "...", "100");
	super.addDescription(Joystick.THRESHOLD_MAX_Y, Short.class, "JSON", "-100", "...", "100");
	super.addDescription(Joystick.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return isEnabled() && isOptionInRange() && isMaxXInRange() && isMaxXInRange() && isMaxYInRange() && isMinYInRange();
    }

    private boolean isEnabled() {
	return getContent(Joystick.ENABLED).getValue(Boolean.class);
    }

    private boolean isOptionInRange() {
	char option = getContent(Joystick.THRESHOLD_OPTION).getValue(Character.class);
	switch (option) {
	    case 'x':
	    case 'o':
	    case 'i':
	    case '<': //<
	    case '>': //>
		return true;
	}
	return false;
    }

    private boolean isMinXInRange() {
	int min = getContent(Joystick.THRESHOLD_MIN_X).getValue(Integer.class);
	return (min <= 100 && min >= -100);
    }

    private boolean isMaxXInRange() {
	int max = getContent(Joystick.THRESHOLD_MAX_X).getValue(Integer.class);
	return (max <= 100 && max >= -100);
    }

    private boolean isMinYInRange() {
	int min = getContent(Joystick.THRESHOLD_MIN_Y).getValue(Integer.class);
	return (min <= 100 && min >= -100);
    }

    private boolean isMaxYInRange() {
	int max = getContent(Joystick.THRESHOLD_MAX_Y).getValue(Integer.class);
	return (max <= 100 && max >= -100);
    }
}
