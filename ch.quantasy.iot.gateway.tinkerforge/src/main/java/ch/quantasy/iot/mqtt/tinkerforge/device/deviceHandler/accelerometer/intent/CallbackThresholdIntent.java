/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.Accelerometer;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CallbackThresholdIntent extends AnIntent<Accelerometer> {

    public CallbackThresholdIntent(Accelerometer deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "callbackThreshold");
	super.addDescription(Accelerometer.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "s", "g");
	super.addDescription(Accelerometer.THRESHOLD_MIN_X, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "4095");
	super.addDescription(Accelerometer.THRESHOLD_MIN_Y, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "4095");
	super.addDescription(Accelerometer.THRESHOLD_MIN_Z, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "4095");
	super.addDescription(Accelerometer.THRESHOLD_MAX_X, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "4095");
	super.addDescription(Accelerometer.THRESHOLD_MAX_Y, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "4095");
	super.addDescription(Accelerometer.THRESHOLD_MAX_Z, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "4095");

	super.addDescription(Accelerometer.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return isEnabled() && isOptionInRange() && isMaxXInRange() && isMinXInRange() && isMaxYInRange() && isMinYInRange() && isMaxZInRange() && isMinZInRange();
    }

    private boolean isEnabled() {
	return getContent(Accelerometer.ENABLED).getValue(Boolean.class);
    }

    private boolean isOptionInRange() {
	char option = getContent(Accelerometer.THRESHOLD_OPTION).getValue(Character.class);
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

    private boolean isMinXInRange() {
	int min = getContent(Accelerometer.THRESHOLD_MIN_X).getValue(Integer.class);
	return (min <= 4095 && min >= Short.MIN_VALUE);
    }

    private boolean isMaxXInRange() {
	int max = getContent(Accelerometer.THRESHOLD_MAX_X).getValue(Integer.class);
	return (max <= 4095 && max >= Short.MIN_VALUE);
    }

    private boolean isMinYInRange() {
	int min = getContent(Accelerometer.THRESHOLD_MIN_Y).getValue(Integer.class);
	return (min <= 4095 && min >= Short.MIN_VALUE);
    }

    private boolean isMaxYInRange() {
	int max = getContent(Accelerometer.THRESHOLD_MAX_Y).getValue(Integer.class);
	return (max <= 4095 && max >= Short.MIN_VALUE);
    }

    private boolean isMinZInRange() {
	int min = getContent(Accelerometer.THRESHOLD_MIN_Z).getValue(Integer.class);
	return (min <= 4095 && min >= Short.MIN_VALUE);
    }

    private boolean isMaxZInRange() {
	int max = getContent(Accelerometer.THRESHOLD_MAX_Z).getValue(Integer.class);
	return (max <= 4095 && max >= Short.MIN_VALUE);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
