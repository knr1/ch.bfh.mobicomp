/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.event;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.intent.BeepIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class BeepEvent extends AnEvent {

    public boolean enabled;
    public long duration;
    public int frequency;

    public BeepEvent(ADeviceHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "beep", mqttClient);
	super.addTopicDefinition("enabled", "Boolean", "JSON", "true", "false");
	super.addTopicDefinition("duration", "Long", "JSON", "1", "...", "" + Long.MAX_VALUE);
	super.addTopicDefinition("frequency", "Integer", "JSON", "585", "...", "7100");
    }

    public void updateIntent(BeepIntent intent) {
	updateDuration(intent.duration);
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

    public void updateDuration(long duration) {
	if (this.duration == duration) {
	    return;
	} else {
	    this.duration = duration;
	    publishEvent("duration", toJSONMQTTMessage(duration));
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
