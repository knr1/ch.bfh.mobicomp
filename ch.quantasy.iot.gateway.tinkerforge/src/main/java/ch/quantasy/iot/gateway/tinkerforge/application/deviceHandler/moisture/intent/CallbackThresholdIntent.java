/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.moisture.intent;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.base.message.AnIntent;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CallbackThresholdIntent extends AnIntent {

    public char option;
    public int min;
    public int max;
    public boolean enabled;

    public CallbackThresholdIntent(ADeviceHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "callbackThreshold");
	super.addTopicDescription("option", "Character", "JSON", "x", "o", "i", "\\<", "\\>");
	super.addTopicDescription("min", "Integer", "JSON", "0", "...", "4095");
	super.addTopicDescription("max", "Integer", "JSON", "0", "...", "4095");
	super.addTopicDescription("enabled", "Boolean", "JSON", "true", "false");
    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getIntentName() + "/option")) {
	    option = fromMQTTMessage(mm, Character.class);
	}
	if (string.endsWith(getIntentName() + "/min")) {
	    min = fromMQTTMessage(mm, Integer.class);
	}
	if (string.endsWith(getIntentName() + "/max")) {
	    max = fromMQTTMessage(mm, Integer.class);
	}
	if (string.endsWith(getIntentName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
    }

    public boolean isExecutable() {
	return enabled && isOptionInRange() && isMaxInRange() && isMaxInRange();
    }

    private boolean isOptionInRange() {
	switch (option) {
	    case 'x':
	    case 'o':
	    case 'i':
	    case '<':
	    case '>':
		return true;
	}
	return false;
    }

    private boolean isMinInRange() {
	return (min <= 4095 && min >= 0);
    }

    private boolean isMaxInRange() {
	return (max <= 4095 && max >= 0);
    }

}
