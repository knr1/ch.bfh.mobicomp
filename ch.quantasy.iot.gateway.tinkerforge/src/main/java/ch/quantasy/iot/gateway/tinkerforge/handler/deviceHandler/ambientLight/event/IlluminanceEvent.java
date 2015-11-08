/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLight.AmbientLight;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class IlluminanceEvent extends AnEvent {

    public IlluminanceEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "illuminance", mqttClient);
	super.addDescription(AmbientLight.VALUE, Integer.class, "JSON", "70", "...", "815");
    }
}
