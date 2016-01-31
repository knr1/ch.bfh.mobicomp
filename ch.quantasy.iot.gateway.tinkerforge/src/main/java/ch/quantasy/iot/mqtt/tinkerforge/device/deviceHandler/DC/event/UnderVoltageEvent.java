/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.DC;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class UnderVoltageEvent extends AnEvent {

    public UnderVoltageEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "underVoltage", mqttClient);
	super.addDescription(DC.VALUE, Short.class, "JSON", "0", "...", "" + Integer.MAX_VALUE);
    }
}
