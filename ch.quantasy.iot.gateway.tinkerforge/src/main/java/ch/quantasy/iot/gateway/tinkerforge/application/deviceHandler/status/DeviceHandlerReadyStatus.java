/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.status;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.AStatus;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DeviceHandlerReadyStatus extends AStatus {

    public boolean enabled;
    public boolean reachable;

    public DeviceHandlerReadyStatus(ADeviceHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "ready", mqttClient);
	super.addStatusTopicDescription("enabled", "Boolean", "JSON", "true", "false");
	super.addStatusTopicDescription("connected", "Boolean", "JSON", "true", "false");
    }

    public void updateEnabled(boolean enabled) {
	if (this.enabled == enabled) {
	    return;
	} else {
	    this.enabled = enabled;
	    publishStatus("enabled", toJSONMQTTMessage(enabled));
	}
	return;
    }

    public void updateReachable(boolean reachable) {
	if (this.reachable == reachable) {
	    return;
	}
	this.reachable = reachable;
	publishStatus("reachable", toJSONMQTTMessage(reachable));
    }
}
