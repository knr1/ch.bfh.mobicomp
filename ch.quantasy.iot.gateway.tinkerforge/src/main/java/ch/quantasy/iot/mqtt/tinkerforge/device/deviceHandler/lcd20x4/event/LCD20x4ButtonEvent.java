/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.LCD20x4;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LCD20x4ButtonEvent extends AnEvent {

    public LCD20x4ButtonEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "buttonPressed", mqttClient);
	super.addDescription(LCD20x4.BUTTON_0, Boolean.class, "JSON", "true,false");
	super.addDescription(LCD20x4.BUTTON_1, Boolean.class, "JSON", "true,false");
	super.addDescription(LCD20x4.BUTTON_2, Boolean.class, "JSON", "true,false");
	super.addDescription(LCD20x4.BUTTON_3, Boolean.class, "JSON", "true,false");

    }
}
