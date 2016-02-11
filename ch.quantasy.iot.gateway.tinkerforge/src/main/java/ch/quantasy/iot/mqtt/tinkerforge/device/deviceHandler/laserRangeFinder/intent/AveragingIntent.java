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
public class AveragingIntent extends AnIntent<LaserRangeFinder> {

    public AveragingIntent(LaserRangeFinder deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "averaging");
	super.addDescription(LaserRangeFinder.AVERAGE_DISTANCE_LENGTH, Short.class, "JSON", "0", "..", "30");
	super.addDescription(LaserRangeFinder.AVERAGE_VELOCITY_LENGTH, Short.class, "JSON", "0", "..", "30");
	super.addDescription(LaserRangeFinder.ENABLED, Boolean.class, "JSON", "true", "false");

    }

    @Override
    public boolean isExecutable() {
	return isEnabled() && isAverageDistanceInRange() && isAverageVelocityInRange();

    }

    private boolean isEnabled() {
	return getContent(LaserRangeFinder.ENABLED).getValue(Boolean.class);
    }

    private boolean isAverageDistanceInRange() {
	short average = getContent(LaserRangeFinder.AVERAGE_DISTANCE_LENGTH).getValue(Short.class);
	return average >= 0 && average <= 30;
    }

    private boolean isAverageVelocityInRange() {
	short average = getContent(LaserRangeFinder.AVERAGE_VELOCITY_LENGTH).getValue(Short.class);
	return average >= 0 && average <= 30;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
