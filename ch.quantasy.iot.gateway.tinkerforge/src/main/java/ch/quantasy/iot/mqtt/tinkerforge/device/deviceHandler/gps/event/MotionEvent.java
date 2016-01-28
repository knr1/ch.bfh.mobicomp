/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.GPS;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MotionEvent extends AnEvent {

    public MotionEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "motion", mqttClient);
	super.addDescription(GPS.COURSE, Long.class, "JSON", "" + Long.MIN_VALUE, "" + Long.MAX_VALUE);
	super.addDescription(GPS.SPEED, Long.class, "JSON", "0", "" + Long.MAX_VALUE);

    }

}
