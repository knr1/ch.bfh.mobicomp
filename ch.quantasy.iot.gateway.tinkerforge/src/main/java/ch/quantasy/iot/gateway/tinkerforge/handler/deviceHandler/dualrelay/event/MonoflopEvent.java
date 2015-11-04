/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay.DualRelay;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MonoflopEvent extends AnEvent {

    public MonoflopEvent(AHandler deviceHandler, String topic, MqttAsyncClient mqttClient) {
	super(deviceHandler, topic, "monoflop", mqttClient);
	super.addDescription(DualRelay.MONOFLOP_TIME, Long.class, "JSON", "0", "...", "" + Long.MAX_VALUE);
	super.addDescription(DualRelay.RELAY, Short.class, "JSON", "0", "...", "15");
	super.addDescription(DualRelay.STATE, Boolean.class, "JSON", "true", "false");
    }
}
