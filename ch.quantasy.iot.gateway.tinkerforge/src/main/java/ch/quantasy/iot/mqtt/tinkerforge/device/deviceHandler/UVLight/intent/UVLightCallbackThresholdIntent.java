/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.UVLight.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.UVLight.UVLight;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class UVLightCallbackThresholdIntent extends AnIntent<UVLight> {

    public UVLightCallbackThresholdIntent(UVLight deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "UVLightCallbackThreshold");
	super.addDescription(UVLight.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "s", "g");
	super.addDescription(UVLight.THRESHOLD_MIN, Integer.class, "JSON", "0", "...", "325000");
	super.addDescription(UVLight.THRESHOLD_MAX, Integer.class, "JSON", "0", "...", "325000");
	super.addDescription(UVLight.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return isEnabled() && isOptionInRange() && isMaxInRange() && isMaxInRange();
    }

    private boolean isEnabled() {
	return getContent(UVLight.ENABLED).getValue(Boolean.class);
    }

    private boolean isOptionInRange() {
	char option = getContent(UVLight.THRESHOLD_OPTION).getValue(Character.class);
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
	int min = getContent(UVLight.THRESHOLD_MIN).getValue(Integer.class);
	return (min <= 325000 && min >= 0);
    }

    private boolean isMaxInRange() {
	int max = getContent(UVLight.THRESHOLD_MAX).getValue(Integer.class);
	return (max <= 325000 && max >= 0);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
