/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.temperatureIR.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.temperatureIR.TemperatureIR;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AmbientTemperatureCallbackThresholdIntent extends AnIntent {

    public AmbientTemperatureCallbackThresholdIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "ambientTemperatureCallbackThreshold");
	super.addDescription(TemperatureIR.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(TemperatureIR.THRESHOLD_MIN, Integer.class, "JSON", "-400", "...", "1250");
	super.addDescription(TemperatureIR.THRESHOLD_MAX, Integer.class, "JSON", "-400", "...", "1250");
	super.addDescription(TemperatureIR.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return isEnabled() && isOptionInRange() && isMaxInRange() && isMaxInRange();
    }

    private boolean isEnabled() {
	return getContent(TemperatureIR.ENABLED).getValue(Boolean.class);
    }

    private boolean isOptionInRange() {
	char option = getContent(TemperatureIR.THRESHOLD_OPTION).getValue(Character.class);
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
	int min = getContent(TemperatureIR.THRESHOLD_MIN).getValue(Integer.class);
	return (min <= 1250 && min >= -400);
    }

    private boolean isMaxInRange() {
	int max = getContent(TemperatureIR.THRESHOLD_MAX).getValue(Integer.class);
	return (max <= 1250 && max >= -400);
    }

}
