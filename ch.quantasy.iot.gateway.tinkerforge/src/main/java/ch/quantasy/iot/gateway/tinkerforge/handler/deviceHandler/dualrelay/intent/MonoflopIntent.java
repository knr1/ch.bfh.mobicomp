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
public class MonoflopIntent extends AnIntent {

    public boolean enabled;
    public long time;
    public short relay;
    public boolean state;

    public MonoflopIntent(AHandler deviceHandler, String topic) {
	super(deviceHandler, topic, "monoflop");
	super.addTopicDescription("enabled", "Boolean", "JSON", "true", "false");
	super.addTopicDescription("time", "Long", "JSON", "0", "...", "" + Long.MAX_VALUE);
	super.addTopicDescription("relay", "Short", "JSON", "1", "2");
	super.addTopicDescription("state", "Boolean", "JSON", "true", "false");

    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getIntentName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
	if (string.endsWith(getIntentName() + "/time")) {
	    time = fromMQTTMessage(mm, Long.class);
	}
	if (string.endsWith(getIntentName() + "/relay")) {
	    relay = fromMQTTMessage(mm, Short.class);
	}
	if (string.endsWith(getIntentName() + "/state")) {
	    state = fromMQTTMessage(mm, Boolean.class);
	}

    }

    public boolean isExecutable() {
	return enabled && isTimeInRange() && isRelayInRange();
    }

    private boolean isTimeInRange() {
	return (time >= 0);
    }

    private boolean isRelayInRange() {
	return (relay == 1 || relay == 2);
    }

}
