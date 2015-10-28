/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.intent.BeepIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class BeepEvent extends AnEvent {

    public boolean enabled;
    public long duration;
    public int frequency;

    public BeepEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "beep", mqttClient);
	super.addDescription("enabled", Boolean.class, "JSON", "true", "false");
	super.addDescription("duration", Long.class, "JSON", "1", "...", "" + Long.MAX_VALUE);
	super.addDescription("frequency", Integer.class, "JSON", "585", "...", "7100");
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
	    publish("enabled", toJSONMQTTMessage(enabled));
	}
	return;
    }

    public void updateDuration(long duration) {
	if (this.duration == duration) {
	    return;
	} else {
	    this.duration = duration;
	    publish("duration", toJSONMQTTMessage(duration));
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
