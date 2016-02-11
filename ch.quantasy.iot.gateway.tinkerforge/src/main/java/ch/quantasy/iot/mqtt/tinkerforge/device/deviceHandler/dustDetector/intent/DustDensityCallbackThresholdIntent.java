/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector.DustDetector;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DustDensityCallbackThresholdIntent extends AnIntent<DustDetector> {

    public DustDensityCallbackThresholdIntent(DustDetector deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "DustDensityCallbackThreshold");
	super.addDescription(DustDetector.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "s", "g");
	super.addDescription(DustDetector.THRESHOLD_MIN, Integer.class, "JSON", "0", "...", "500");
	super.addDescription(DustDetector.THRESHOLD_MAX, Integer.class, "JSON", "0", "...", "500");
	super.addDescription(DustDetector.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return isEnabled() && isOptionInRange() && isMaxInRange() && isMaxInRange();
    }

    private boolean isEnabled() {
	return getContent(DustDetector.ENABLED).getValue(Boolean.class);
    }

    private boolean isOptionInRange() {
	char option = getContent(DustDetector.THRESHOLD_OPTION).getValue(Character.class);
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
	int min = getContent(DustDetector.THRESHOLD_MIN).getValue(Integer.class);
	return (min <= 500 && min >= 0);
    }

    private boolean isMaxInRange() {
	int max = getContent(DustDetector.THRESHOLD_MAX).getValue(Integer.class);
	return (max <= 500 && max >= 0);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
