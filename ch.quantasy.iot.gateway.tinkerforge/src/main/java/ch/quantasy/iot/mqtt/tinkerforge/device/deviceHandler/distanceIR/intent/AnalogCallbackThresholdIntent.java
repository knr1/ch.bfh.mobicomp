/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.distanceIR.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.distanceIR.DistanceIR;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AnalogCallbackThresholdIntent extends AnIntent<DistanceIR> {

    public AnalogCallbackThresholdIntent(DistanceIR deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "analogCallbackThreshold");
	super.addDescription(DistanceIR.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "s", "g");
	super.addDescription(DistanceIR.THRESHOLD_MIN, Integer.class, "JSON", "0", "...", "4095");
	super.addDescription(DistanceIR.THRESHOLD_MAX, Integer.class, "JSON", "0", "...", "4095");
	super.addDescription(DistanceIR.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return isEnabled() && isOptionInRange() && isMaxInRange() && isMaxInRange();
    }

    private boolean isEnabled() {
	return getContent(DistanceIR.ENABLED).getValue(Boolean.class);
    }

    private boolean isOptionInRange() {
	char option = getContent(DistanceIR.THRESHOLD_OPTION).getValue(Character.class);
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
	int min = getContent(DistanceIR.THRESHOLD_MIN).getValue(Integer.class);
	return (min <= 4095 && min >= 0);
    }

    private boolean isMaxInRange() {
	int max = getContent(DistanceIR.THRESHOLD_MAX).getValue(Integer.class);
	return (max <= 4095 && max >= 0);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
