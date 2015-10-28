/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.tilt.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TiltStateCallbackIntent extends AnIntent {

    public boolean enabled;

    public TiltStateCallbackIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "callbackState");
	super.addDescription("enabled", Boolean.class, "JSON", "true", "false");
    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
    }

    @Override
    public boolean isExecutable() {
	return true;
    }

}
