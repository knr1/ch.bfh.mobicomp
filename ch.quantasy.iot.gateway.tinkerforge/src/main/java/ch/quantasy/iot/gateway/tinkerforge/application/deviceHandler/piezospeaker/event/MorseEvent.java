/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.event;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.intent.MorseIntent;
import java.util.Objects;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MorseEvent extends AnEvent {

    public boolean enabled;
    public String code;
    public int frequency;

    public MorseEvent(ADeviceHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "morse", mqttClient);
	super.addTopicDefinition("enabled", "Boolean", "JSON", "true", "false");
	super.addTopicDefinition("code", "String", "JSON", ".", "-", " ", "unbounded");
	super.addTopicDefinition("frequency", "Integer", "JSON", "585", "...", "7100");
    }

    public void updateIntent(MorseIntent intent) {

	updateCode(intent.code);
	updateEnabled(intent.enabled);
	updateFrequency(intent.frequency);

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

    public void updateCode(String code) {
	if (Objects.equals(this.code, code)) {
	    return;
	} else {
	    this.code = code;
	    publishEvent("code", toJSONMQTTMessage(code));
	}
	return;
    }

    public void updateFrequency(int frequency) {
	if (this.frequency == frequency) {
	    return;
	} else {
	    this.frequency = frequency;
	    publishEvent("frequency", toJSONMQTTMessage(frequency));
	}
	return;
    }
}
