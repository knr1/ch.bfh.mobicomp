/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.moisture.event;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.AnEvent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MoistureEvent extends AnEvent {

    public int moisture;

    public MoistureEvent(ADeviceHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "moisture", mqttClient);
	super.addTopicDefinition("moisture", "Integer", "JSON", "0", "...", "4095");
    }

    public void updateMoisture(int moisture) {
	if (this.moisture == moisture) {
	    return;
	} else {
	    this.moisture = moisture;
	    publishEvent("moisture", toJSONMQTTMessage(moisture));
	}
	return;
    }
}
