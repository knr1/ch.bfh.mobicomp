/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.moisture.event;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.base.message.AnEvent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MoistureReachedEvent extends AnEvent {

    public int moisture;

    public MoistureReachedEvent(ADeviceHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "moistureReached", mqttClient);
	super.addTopicDefinition("moisture", "Integer", "JSON", "0", "...", "4095");
    }

    public void updateMoisture(int moisture) {
	this.moisture = moisture;
	publishEvent("moisture", toJSONMQTTMessage(moisture));

	return;
    }
}
