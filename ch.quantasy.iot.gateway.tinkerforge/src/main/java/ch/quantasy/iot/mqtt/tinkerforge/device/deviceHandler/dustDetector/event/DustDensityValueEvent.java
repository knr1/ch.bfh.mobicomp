/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.CO2.CO2;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DustDensityValueEvent extends AnEvent {

    public DustDensityValueEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "dustDensity", mqttClient);
	super.addDescription(CO2.VALUE, Integer.class, "JSON", "0", "...", "500");
    }
}
