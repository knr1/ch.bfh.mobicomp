/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MovingAverageIntent extends AnIntent {

    public short average;

    public MovingAverageIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "movingAverage");
	super.addDescription("average", Short.class, "JSON", "0", "...", "100");
    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getName() + "/average")) {
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
