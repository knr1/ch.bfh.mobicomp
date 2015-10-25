/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.event;

import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.intent.CalibrateIntent;
import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CalibrateEvent extends AnEvent {

    public boolean enabled;

    public CalibrateEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "calibrate", mqttClient);
	super.addDescription("enabled", "Boolean", "JSON", "true", "false");
    }

    public void updateIntent(CalibrateIntent intent) {
	updateEnabled(intent.enabled);
    }

    public void updateEnabled(boolean enabled) {
	if (this.enabled == enabled) {
	    return;
	} else {
	    this.enabled = enabled;
	    publish("enabled", toJSONMQTTMessage(enabled));
	}
	return;
    }
}
