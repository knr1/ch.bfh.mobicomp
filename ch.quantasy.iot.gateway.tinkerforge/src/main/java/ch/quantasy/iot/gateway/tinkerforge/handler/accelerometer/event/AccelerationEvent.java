/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.accelerometer.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.accelerometer.Accelerometer;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AccelerationEvent extends AnEvent {

    public AccelerationEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "acceleration", mqttClient);
	super.addDescription(Accelerometer.X, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "" + Short.MAX_VALUE);
	super.addDescription(Accelerometer.Y, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "" + Short.MAX_VALUE);
	super.addDescription(Accelerometer.Z, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "" + Short.MAX_VALUE);

    }
}
