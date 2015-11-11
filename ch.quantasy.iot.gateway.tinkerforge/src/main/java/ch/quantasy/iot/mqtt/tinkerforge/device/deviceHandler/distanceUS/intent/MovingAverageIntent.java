/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.distanceUS.intent;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.distanceUS.DistanceUS;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MovingAverageIntent extends AnIntent {

    public MovingAverageIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "movingAverage");
	super.addDescription(DistanceUS.AVERAGE, Short.class, "JSON", "0", "...", "100");
    }

    @Override
    public boolean isExecutable() {
	return isAverageInRange();
    }

    private boolean isAverageInRange() {
	short average = getContent(DistanceUS.AVERAGE).getValue(Short.class);
	return average >= 0 && average <= 100;
    }

}
