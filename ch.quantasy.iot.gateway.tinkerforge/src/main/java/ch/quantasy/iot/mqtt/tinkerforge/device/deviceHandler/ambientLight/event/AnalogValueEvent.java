/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ambientLight.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ambientLight.AmbientLight;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AnalogValueEvent extends AnEvent {

    public AnalogValueEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "analogValue", mqttClient);
	super.addDescription(AmbientLight.VALUE, Integer.class, "JSON", "0", "...", "4095");
    }
}
