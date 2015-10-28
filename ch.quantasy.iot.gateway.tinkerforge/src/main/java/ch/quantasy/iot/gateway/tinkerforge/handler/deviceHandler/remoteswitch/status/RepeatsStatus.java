/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.status;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.intent.RepeatsIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RepeatsStatus extends AStatus {

    public short repeats;

    public RepeatsStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "repeats", mqttClient);
	super.addDescription("repeats", Short.class, "JSON", "0", "...", "" + Short.MAX_VALUE);
    }

    public void updateIntent(RepeatsIntent intent) {
	updateRepeats(intent.repeats);
    }

    public void updateRepeats(short repeats) {
	if (this.repeats == repeats) {
	    return;
	} else {
	    this.repeats = repeats;
	    publish("repeats", toJSONMQTTMessage(repeats));
	}
	return;
    }
}
