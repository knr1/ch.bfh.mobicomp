/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.tilt.status;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.tilt.intent.TiltStateCallbackIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TiltStateCallbackStatus extends AStatus {

    public boolean enabled;

    public TiltStateCallbackStatus(ADeviceHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "stateCallback", mqttClient);
	super.addTopicDescription("enabled", "Boolean", "JSON", "true", "false");
    }

    public void updateIntent(TiltStateCallbackIntent intent) {
	updateTiltState(intent.enabled);
    }

    public void updateTiltState(boolean enabled) {
	if (this.enabled == enabled) {
	    return;
	} else {
	    this.enabled = enabled;
	    publishStatus("enabled", toJSONMQTTMessage(enabled));
	}
	return;
    }
}
