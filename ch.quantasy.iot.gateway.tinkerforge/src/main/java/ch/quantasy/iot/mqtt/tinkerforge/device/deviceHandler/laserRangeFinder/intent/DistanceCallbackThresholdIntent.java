/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.LaserRangeFinder;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DistanceCallbackThresholdIntent extends AnIntent<LaserRangeFinder> {

    public DistanceCallbackThresholdIntent(LaserRangeFinder deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "distanceCallbackThreshold");
	super.addDescription(LaserRangeFinder.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(LaserRangeFinder.THRESHOLD_MIN, Integer.class, "JSON", "0", "...", "4000");
	super.addDescription(LaserRangeFinder.THRESHOLD_MAX, Integer.class, "JSON", "0", "...", "4000");
	super.addDescription(LaserRangeFinder.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return isEnabled() && isOptionInRange() && isMaxInRange() && isMaxInRange();
    }

    private boolean isEnabled() {
	return getContent(LaserRangeFinder.ENABLED).getValue(Boolean.class);
    }

    private boolean isOptionInRange() {
	char option = getContent(LaserRangeFinder.THRESHOLD_OPTION).getValue(Character.class);
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
	int min = getContent(LaserRangeFinder.THRESHOLD_MIN).getValue(Integer.class);
	return (min <= 4000 && min >= 0);
    }

    private boolean isMaxInRange() {
	int max = getContent(LaserRangeFinder.THRESHOLD_MAX).getValue(Integer.class);
	return (max <= 4000 && max >= 0);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
