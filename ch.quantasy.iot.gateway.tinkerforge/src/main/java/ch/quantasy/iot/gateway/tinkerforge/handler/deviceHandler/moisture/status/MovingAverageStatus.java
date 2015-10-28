/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.status;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.intent.MovingAverageIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MovingAverageStatus extends AStatus {

    public short average;

    public MovingAverageStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "movingAverage", mqttClient);
	super.addDescription("average", Short.class, "JSON", "0", "..", "100");
    }

    public void updateIntent(MovingAverageIntent intent) {
	updateMovingAverage(intent.average);
    }

    public void updateMovingAverage(short average) {
	if (this.average == average) {
	    return;
	} else {
	    this.average = average;
	    publish("average", toJSONMQTTMessage(average));
	}
	return;
    }
}
