/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DualRelayIntent extends AnIntent {

    public boolean relay1;
    public boolean relay2;
    public boolean enabled;

    public DualRelayIntent(AHandler deviceHandler, String topic) {
	super(deviceHandler, topic, "dualRelay");
	super.addTopicDescription("relay1", "Boolean", "JSON", "true", "false");
	super.addTopicDescription("relay2", "Boolean", "JSON", "true", "false");
	super.addTopicDescription("enabled", "Boolean", "JSON", "true", "false");
    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getIntentName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
	if (string.endsWith(getIntentName() + "/relay1")) {
	    relay1 = fromMQTTMessage(mm, Boolean.class);
	}
	if (string.endsWith(getIntentName() + "/relay2")) {
	    relay2 = fromMQTTMessage(mm, Boolean.class);
	}

    }

    public boolean isExecutable() {
	return enabled;
    }
}
