/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.moisture.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.moisture.Moisture;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MoistureEvent extends AnEvent {

    public MoistureEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "moisture", mqttClient);
	super.addDescription(Moisture.MOISTURE, Integer.class, "JSON", "0", "...", "4095");
    }
}
