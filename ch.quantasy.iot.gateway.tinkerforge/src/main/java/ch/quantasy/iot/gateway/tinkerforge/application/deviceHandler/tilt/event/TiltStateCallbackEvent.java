/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.tilt.event;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.tilt.intent.TiltStateCallbackIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TiltStateCallbackEvent extends AnEvent {

    public boolean enabled;

    public TiltStateCallbackEvent(ADeviceHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "stateCallback", mqttClient);
	super.addEventTopicDefinition("enabled", "Boolean", "JSON", "true", "false");
    }

    public void updateIntent(TiltStateCallbackIntent intent) {
	updateTiltState(intent.enabled);
    }

    public void updateTiltState(boolean enabled) {
	if (this.enabled == enabled) {
	    return;
	} else {
	    this.enabled = enabled;
	    publishEvent("enabled", toJSONMQTTMessage(enabled));
	}
	return;
    }
}
