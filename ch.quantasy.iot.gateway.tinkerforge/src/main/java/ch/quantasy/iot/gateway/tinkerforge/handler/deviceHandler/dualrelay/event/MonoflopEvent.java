/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MonoflopEvent extends AnEvent {

    public short relay;
    public long time;
    public boolean state;

    public MonoflopEvent(AHandler deviceHandler, String topic, MqttAsyncClient mqttClient) {
	super(deviceHandler, topic, "monoflop", mqttClient);
	super.addDescription("time", Long.class, "JSON", "0", "...", "" + Long.MAX_VALUE);
	super.addDescription("relay", Short.class, "JSON", "0", "...", "15");
	super.addDescription("state", Boolean.class, "JSON", "true", "false");
    }

    public void updateTime(long time) {
	if (this.time == time) {
	    return;
	} else {
	    this.time = time;
	    publish("time", toJSONMQTTMessage(time));
	}
    }

    public void updateRelay(short relay) {
	if (this.relay == relay) {
	    return;
	} else {
	    this.relay = relay;
	    publish("relay", toJSONMQTTMessage(relay));
	}
    }

    public void updateState(boolean state) {
	if (this.state == state) {
	    return;
	} else {
	    this.state = state;
	    publish("state", toJSONMQTTMessage(state));
	}
    }
}
