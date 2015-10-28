/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.tilt.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TiltStateEvent extends AnEvent {

    public short state;

    public TiltStateEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "tilt", mqttClient);
	super.addDescription("state", Short.class, "JSON", "0", "1", "2");
    }

    public void updateTiltState(short state) {
	if (this.state == state) {
	    return;
	} else {
	    this.state = state;
	    publish("state", toJSONMQTTMessage(state));
	}
	return;
    }
}
