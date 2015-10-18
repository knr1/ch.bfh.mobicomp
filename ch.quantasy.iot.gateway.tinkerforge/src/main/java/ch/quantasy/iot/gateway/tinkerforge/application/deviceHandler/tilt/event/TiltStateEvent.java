/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.tilt.event;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.AnEvent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TiltStateEvent extends AnEvent {

    public short state;

    public TiltStateEvent(ADeviceHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "tilt", mqttClient);
	super.addTopicDefinition("state", "Short", "JSON", "0", "1", "2");
    }

    public void updateTiltState(short state) {
	if (this.state == state) {
	    return;
	} else {
	    this.state = state;
	    publishEvent("state", toJSONMQTTMessage(state));
	}
	return;
    }
}
