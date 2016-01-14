/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.barometer.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.barometer.Barometer;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AltitudeCallbackThresholdIntent extends AnIntent<Barometer> {

    public AltitudeCallbackThresholdIntent(Barometer deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "altitudeCallbackThreshold");
	super.addDescription(Barometer.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(Barometer.THRESHOLD_MIN, Integer.class, "JSON", "0", "...", "1000");
	super.addDescription(Barometer.THRESHOLD_MAX, Integer.class, "JSON", "0", "...", "1000");
	super.addDescription(Barometer.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return isEnabled() && isOptionInRange() && isMaxInRange() && isMaxInRange();
    }

    private boolean isEnabled() {
	return getContent(Barometer.ENABLED).getValue(Boolean.class);
    }

    private boolean isOptionInRange() {
	char option = getContent(Barometer.THRESHOLD_OPTION).getValue(Character.class);
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
	int min = getContent(Barometer.THRESHOLD_MIN).getValue(Integer.class);
	return (min <= 4095 && min >= 0);
    }

    private boolean isMaxInRange() {
	int max = getContent(Barometer.THRESHOLD_MAX).getValue(Integer.class);
	return (max <= 4095 && max >= 0);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
