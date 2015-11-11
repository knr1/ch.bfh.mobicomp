/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.Accelerometer;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AccelerationReachedEvent extends AnEvent {

    public AccelerationReachedEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "accelerationReached", mqttClient);
	super.addDescription(Accelerometer.X, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "" + Short.MAX_VALUE);
	super.addDescription(Accelerometer.Y, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "" + Short.MAX_VALUE);
	super.addDescription(Accelerometer.Z, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "" + Short.MAX_VALUE);

    }

}
