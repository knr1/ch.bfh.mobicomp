/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.IMUV2;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LinearAccelerationEvent extends AnEvent {

    public LinearAccelerationEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "linearAcceleration", mqttClient);
	super.addDescription(IMUV2.VALUE, Short[].class, "JSON", "[" + Short.MIN_VALUE + "," + Short.MIN_VALUE + "," + Short.MIN_VALUE + "]", "...", "[" + Short.MAX_VALUE + "," + Short.MAX_VALUE + "," + Short.MAX_VALUE + "]");
    }
}
