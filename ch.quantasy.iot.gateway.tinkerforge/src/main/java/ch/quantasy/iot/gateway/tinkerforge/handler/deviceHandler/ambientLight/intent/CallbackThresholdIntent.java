/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.AmbientLight;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CallbackThresholdIntent extends AnIntent {

    public CallbackThresholdIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "callbackThreshold");
	super.addDescription(AmbientLight.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(AmbientLight.THRESHOLD_MIN, Short.class, "JSON", "0", "...", "4095");
	super.addDescription(AmbientLight.THRESHOLD_MAX, Short.class, "JSON", "0", "...", "4095");
	super.addDescription(AmbientLight.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return isEnabled() && isOptionInRange() && isMaxInRange() && isMaxInRange();
    }

    private boolean isEnabled() {
	return getContent(AmbientLight.ENABLED).getValue(Boolean.class);
    }

    private boolean isOptionInRange() {
	char option = getContent(AmbientLight.THRESHOLD_OPTION).getValue(Character.class);
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

    private boolean isMinInRange() {
	int min = getContent(AmbientLight.THRESHOLD_MIN).getValue(Integer.class);
	return (min <= 4095 && min >= 0);
    }

    private boolean isMaxInRange() {
	int max = getContent(AmbientLight.THRESHOLD_MAX).getValue(Integer.class);
	return (max <= 4095 && max >= 0);
    }

}
