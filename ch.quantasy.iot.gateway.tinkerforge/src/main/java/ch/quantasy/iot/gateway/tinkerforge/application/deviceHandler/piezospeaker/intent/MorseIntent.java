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
public class MorseIntent extends AnIntent {

    public boolean enabled;
    public String code;
    public int frequency;

    public MorseIntent(PiezoSpeaker deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "morse");
	super.addIntentTopicDefinition("enabled", "Boolean", "JSON", "true", "false");
	super.addIntentTopicDefinition("code", "String", "JSON", ".", "-", " ", "unbounded");
	super.addIntentTopicDefinition("frequency", "Integer", "JSON", "685", "...", "7100");
    }

    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getIntentName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
	if (string.endsWith(getIntentName() + "/code")) {
	    code = fromMQTTMessage(mm, String.class);
	}
	if (string.endsWith(getIntentName() + "/frequency")) {
	    frequency = fromMQTTMessage(mm, Integer.class);
	}
	getDeviceHandler().executeIntent(this);
    }

    @Override
    public boolean isExecutable() {
	return enabled && code != null && isFrequencyInRange();
    }

    private boolean isFrequencyInRange() {
	return (frequency >= 685 && frequency <= 7100);
    }
}
