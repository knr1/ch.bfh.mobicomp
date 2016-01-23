/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.CO2.event;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.barometer.event.*;
import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.barometer.Barometer;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AirPressureValueEvent extends AnEvent {

    public AirPressureValueEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "airPressure", mqttClient);
	super.addDescription(Barometer.VALUE, Integer.class, "JSON", "0", "...", "4095");
    }
}
