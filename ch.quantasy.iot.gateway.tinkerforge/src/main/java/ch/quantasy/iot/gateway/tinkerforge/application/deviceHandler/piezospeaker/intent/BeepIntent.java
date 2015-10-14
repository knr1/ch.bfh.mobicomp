/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.intent;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.PiezoSpeaker;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class BeepIntent extends AnIntent {

    public boolean enabled;
    public long duration;
    public int frequency;

    public BeepIntent(PiezoSpeaker deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "beep");
	super.addIntentTopicDefinition("enabled", "Boolean", "JSON", "true", "false");
	super.addIntentTopicDefinition("duration", "Long", "JSON", "1", "...", "" + Long.MAX_VALUE);
	super.addIntentTopicDefinition("frequency", "Integer", "JSON", "585", "...", "7100");
    }

    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getIntentName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
	if (string.endsWith(getIntentName() + "/duration")) {
	    duration = fromMQTTMessage(mm, Long.class);
	}
	if (string.endsWith(getIntentName() + "/frequency")) {
	    frequency = fromMQTTMessage(mm, Integer.class);
	}
	getDeviceHandler().executeIntent(this);

    }

    public boolean isExecutable() {
	return enabled && isDurationInRange() && isFrequencyInRange();
    }

    private boolean isFrequencyInRange() {
	return (frequency >= 685 && frequency <= 7100);
    }

    private boolean isDurationInRange() {
	return duration > 0;
    }
}
