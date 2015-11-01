/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.Color.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.Color.Color;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ColorEvent extends AnEvent {

    public ColorEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "color", mqttClient);
	super.addDescription(Color.RED, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.GREEN, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.BLUE, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.CLEAR, Integer.class, "JSON", "0", "...", "65535");
    }
}
