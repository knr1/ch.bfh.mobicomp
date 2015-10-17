/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.moisture.intent;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.AnIntent;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MovingAverageIntent extends AnIntent {

    public short average;

    public MovingAverageIntent(ADeviceHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "movingAverage");
	super.addIntentTopicDefinition("average", "Short", "JSON", "0", "...", "100");
    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getIntentName() + "/average")) {
	    average = fromMQTTMessage(mm, Short.class);
	}
    }

    @Override
    public boolean isExecutable() {
	return isAverageInRange();
    }

    private boolean isAverageInRange() {
	return average >= 0 && average <= 100;
    }

}
