/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.linearPoti.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.linearPoti.LinearPoti;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AnalogValueEvent extends AnEvent {

    public AnalogValueEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "analogValue", mqttClient);
	super.addDescription(LinearPoti.VALUE, Integer.class, "JSON", "0", "...", "4095");
    }
}
