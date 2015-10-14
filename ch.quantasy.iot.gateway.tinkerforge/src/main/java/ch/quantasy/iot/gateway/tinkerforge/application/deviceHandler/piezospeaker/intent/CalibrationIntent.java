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
public class CalibrationIntent extends AnIntent {

    public boolean enabled;
    public long duration;
    public int frequency;

    public CalibrationIntent(PiezoSpeaker deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "calibrate");
	super.addIntentTopicDefinition("enabled", "Boolean", "JSON", "true", "false");
    }

    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getIntentName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
	getDeviceHandler().executeIntent(this);
    }

    @Override
    public boolean isExecutable() {
	return enabled;
    }

}
