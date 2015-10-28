/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
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

    public CallbackThresholdIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "callbackThreshold");
	super.addDescription("option", Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription("min", Integer.class, "JSON", "0", "...", "4095");
	super.addDescription("max", Integer.class, "JSON", "0", "...", "4095");
	super.addDescription("enabled", Boolean.class, "JSON", "true", "false");
    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getName() + "/option")) {
	    option = fromMQTTMessage(mm, Character.class);
	}
	if (string.endsWith(getName() + "/min")) {
	    min = fromMQTTMessage(mm, Integer.class);
	}
	if (string.endsWith(getName() + "/max")) {
	    max = fromMQTTMessage(mm, Integer.class);
	}
	if (string.endsWith(getName() + "/enabled")) {
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
	    case '<': //<
	    case '>': //>
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
