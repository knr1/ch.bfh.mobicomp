/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.Barometer;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AveragingIntent extends AnIntent {

    public AveragingIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "averaging");
	super.addDescription(Barometer.MOVING_AVERAGE_PRESSURE, Short.class, "JSON", "0", "..", "25");
	super.addDescription(Barometer.AVERAGE_PRESSURE, Short.class, "JSON", "0", "..", "10");
	super.addDescription(Barometer.AVERAGE_TEMPERATURE, Short.class, "JSON", "0", "..", "255");
	super.addDescription(Barometer.ENABLED, Boolean.class, "JSON", "true", "false");

    }

    @Override
    public boolean isExecutable() {
	return isEnabled() && isMovingAveragePressureInRange() && isAveragePressureInRange() && isAverageTemperatureInRange();

    }

    private boolean isEnabled() {
	return getContent(Barometer.ENABLED).getValue(Boolean.class);
    }

    private boolean isMovingAveragePressureInRange() {
	short average = getContent(Barometer.MOVING_AVERAGE_PRESSURE).getValue(Short.class);
	return average >= 0 && average <= 25;
    }

    private boolean isAveragePressureInRange() {
	short average = getContent(Barometer.AVERAGE_PRESSURE).getValue(Short.class);
	return average >= 0 && average <= 25;
    }

    private boolean isAverageTemperatureInRange() {
	short average = getContent(Barometer.AVERAGE_TEMPERATURE).getValue(Short.class);
	return average >= 0 && average <= 255;
    }

}
