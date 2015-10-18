/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.motiondetector.event;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.AnEvent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MotionDetectedEvent extends AnEvent {

    public boolean detected;

    public MotionDetectedEvent(ADeviceHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "motionDetected", mqttClient);
	super.addTopicDefinition("detected", "Boolean", "JSON", "true", "false");

    }

    public void updateDetected(boolean detected) {
	if (this.detected == detected) {
	    return;
	} else {
	    this.detected = detected;
	    publishEvent("detected", toJSONMQTTMessage(detected));
	}
	return;
    }
}
