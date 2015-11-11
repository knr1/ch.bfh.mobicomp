/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.moisture.intent;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.moisture.Moisture;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MovingAverageIntent extends AnIntent {

    public MovingAverageIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "movingAverage");
	super.addDescription(Moisture.AVERAGE, Short.class, "JSON", "0", "...", "100");
    }

    @Override
    public boolean isExecutable() {
	return isAverageInRange();
    }

    private boolean isAverageInRange() {
	short average = getContent(Moisture.AVERAGE).getValue(Short.class);
	return average >= 0 && average <= 100;
    }

}
