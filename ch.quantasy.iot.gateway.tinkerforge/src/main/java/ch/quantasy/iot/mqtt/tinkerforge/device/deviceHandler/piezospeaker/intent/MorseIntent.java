/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.intent;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MorseIntent extends AnIntent {

    public boolean enabled;
    public String code;
    public int frequency;

    public MorseIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "morse");
	super.addDescription("enabled", Boolean.class, "JSON", "true", "false");
	super.addDescription("code", String.class, "JSON", ".", "-", " ", "unbounded");
	super.addDescription("frequency", Integer.class, "JSON", "685", "...", "7100");
    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
	if (string.endsWith(getName() + "/code")) {
	    code = fromMQTTMessage(mm, String.class);
	}
	if (string.endsWith(getName() + "/frequency")) {
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
