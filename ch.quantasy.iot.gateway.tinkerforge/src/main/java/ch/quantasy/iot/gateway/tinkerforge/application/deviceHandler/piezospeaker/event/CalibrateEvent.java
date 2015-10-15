/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.event;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.intent.CalibrateIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CalibrateEvent extends AnEvent {

    public boolean enabled;

    public CalibrateEvent(ADeviceHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "calibrate", mqttClient);
	super.addEventTopicDefinition("enabled", "Boolean", "JSON", "true", "false");
    }

    public void updateIntent(CalibrateIntent intent) {
	updateEnabled(intent.enabled);
    }

    public void updateEnabled(boolean enabled) {
	if (this.enabled == enabled) {
	    return;
	} else {
	    this.enabled = enabled;
	    publishEvent("enabled", toJSONMQTTMessage(enabled));
	}
	return;
    }
}
