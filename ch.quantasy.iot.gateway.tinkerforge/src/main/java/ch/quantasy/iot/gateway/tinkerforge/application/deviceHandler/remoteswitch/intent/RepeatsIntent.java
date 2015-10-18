/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.intent;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.AnIntent;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RepeatsIntent extends AnIntent {

    public short repeats;

    public RepeatsIntent(ADeviceHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "repeats");
	super.addTopicDefinition("repeats", "Short", "JSON", "0", "...", "" + Short.MAX_VALUE);
    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getIntentName() + "/repeats")) {
	    repeats = fromMQTTMessage(mm, Short.class);
	}

    }

    public boolean isExecutable() {
	return isRepeatsInRange();
    }

    private boolean isRepeatsInRange() {
	return (repeats >= 0);
    }

}
