/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.event;

import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.intent.MorseIntent;
import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
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

    public MorseEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "morse", mqttClient);
	super.addDescription("enabled", "Boolean", "JSON", "true", "false");
	super.addDescription("code", "String", "JSON", ".", "-", " ", "unbounded");
	super.addDescription("frequency", "Integer", "JSON", "585", "...", "7100");
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
	    publish("enabled", toJSONMQTTMessage(enabled));
	}
	return;
    }

    public void updateCode(String code) {
	if (Objects.equals(this.code, code)) {
	    return;
	} else {
	    this.code = code;
	    publish("code", toJSONMQTTMessage(code));
	}
	return;
    }

    public void updateFrequency(int frequency) {
	if (this.frequency == frequency) {
	    return;
	} else {
	    this.frequency = frequency;
	    publish("frequency", toJSONMQTTMessage(frequency));
	}
	return;
    }
}
