/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.intent;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.base.message.AnIntent;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MorseIntent extends AnIntent {

    public boolean enabled;
    public String code;
    public int frequency;

    public MorseIntent(ADeviceHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "morse");
	super.addTopicDescription("enabled", "Boolean", "JSON", "true", "false");
	super.addTopicDescription("code", "String", "JSON", ".", "-", " ", "unbounded");
	super.addTopicDescription("frequency", "Integer", "JSON", "685", "...", "7100");
    }

    @Override
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
    }

    @Override
    public boolean isExecutable() {
	return enabled && code != null && isFrequencyInRange();
    }

    private boolean isFrequencyInRange() {
	return (frequency >= 685 && frequency <= 7100);
    }
}
