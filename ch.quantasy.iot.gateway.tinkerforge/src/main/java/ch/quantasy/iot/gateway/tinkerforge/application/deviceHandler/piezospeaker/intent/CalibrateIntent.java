/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CalibrateIntent extends AnIntent {

    public boolean enabled;

    public CalibrateIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "calibrate");
	super.addTopicDescription("enabled", "Boolean", "JSON", "true", "false");
    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getIntentName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
    }

    @Override
    public boolean isExecutable() {
	return enabled;
    }

}
