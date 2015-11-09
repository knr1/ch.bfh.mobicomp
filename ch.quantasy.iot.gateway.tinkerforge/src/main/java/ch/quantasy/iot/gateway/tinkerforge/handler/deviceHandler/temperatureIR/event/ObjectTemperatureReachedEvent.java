/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.temperatureIR.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.temperatureIR.TemperatureIR;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ObjectTemperatureReachedEvent extends AnEvent {

    public ObjectTemperatureReachedEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "objectTemperature", mqttClient);
	super.addDescription(TemperatureIR.VALUE, Integer.class, "JSON", "-700", "...", "8300");
    }

}