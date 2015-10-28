/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.base.status;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class HandlerReadyStatus extends AStatus {

    public boolean reachable;

    public HandlerReadyStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "connectionState", mqttClient);
	super.addDescription("reachable", Boolean.class, "JSON", "true", "false");
    }

    public void updateReachable(boolean reachable) {
	super.getTriple("reachable");
	if (this.reachable == reachable) {
	    return;
	}
	this.reachable = reachable;
	publish("reachable", toJSONMQTTMessage(reachable));
    }
}
