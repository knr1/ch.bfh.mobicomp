/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MoistureReachedEvent extends AnEvent {

    public int moisture;

    public MoistureReachedEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "moistureReached", mqttClient);
	super.addDescription("moisture", Integer.class, "JSON", "0", "...", "4095");
    }

    public void updateMoisture(int moisture) {
	this.moisture = moisture;
	publish("moisture", toJSONMQTTMessage(moisture));

	return;
    }
}
