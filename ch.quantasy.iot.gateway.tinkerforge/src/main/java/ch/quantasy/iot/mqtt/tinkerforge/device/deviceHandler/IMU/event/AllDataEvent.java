/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.IMU;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AllDataEvent extends AnEvent {

    public AllDataEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "allData", mqttClient);
	super.addDescription(IMU.VALUE, short[].class, "JSON",
			     "[" + Short.MIN_VALUE + "," + Short.MIN_VALUE + "," + Short.MIN_VALUE + ","
			     + Short.MIN_VALUE + "," + Short.MIN_VALUE + "," + Short.MIN_VALUE + ","
			     + Short.MIN_VALUE + "," + Short.MIN_VALUE + "," + Short.MIN_VALUE + "," + Short.MIN_VALUE + "]",
			     "...",
			     "[" + Short.MAX_VALUE + "," + Short.MAX_VALUE + "," + Short.MAX_VALUE + ","
			     + Short.MAX_VALUE + "," + Short.MAX_VALUE + "," + Short.MAX_VALUE + ","
			     + Short.MAX_VALUE + "," + Short.MAX_VALUE + "," + Short.MAX_VALUE + "," + Short.MAX_VALUE + "]"
	);
    }
}
