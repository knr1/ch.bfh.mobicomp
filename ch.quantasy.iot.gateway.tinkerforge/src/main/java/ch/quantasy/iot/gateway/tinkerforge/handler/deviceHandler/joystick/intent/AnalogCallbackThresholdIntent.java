/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.joystick.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.joystick.Joystick;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AnalogCallbackThresholdIntent extends AnIntent {

    public AnalogCallbackThresholdIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "analogCallbackThreshold");
	super.addDescription(Joystick.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(Joystick.THRESHOLD_MIN_X, Integer.class, "JSON", "0", "...", "4095");
	super.addDescription(Joystick.THRESHOLD_MAX_X, Integer.class, "JSON", "0", "...", "4095");
	super.addDescription(Joystick.THRESHOLD_MIN_Y, Integer.class, "JSON", "0", "...", "4095");
	super.addDescription(Joystick.THRESHOLD_MAX_Y, Integer.class, "JSON", "0", "...", "4095");
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
	return (min <= 4095 && min >= 0);
    }

    private boolean isMaxXInRange() {
	int max = getContent(Joystick.THRESHOLD_MAX_X).getValue(Integer.class);
	return (max <= 4095 && max >= 0);
    }

    private boolean isMinYInRange() {
	int min = getContent(Joystick.THRESHOLD_MIN_Y).getValue(Integer.class);
	return (min <= 4095 && min >= 0);
    }

    private boolean isMaxYInRange() {
	int max = getContent(Joystick.THRESHOLD_MAX_Y).getValue(Integer.class);
	return (max <= 4095 && max >= 0);
    }

}
