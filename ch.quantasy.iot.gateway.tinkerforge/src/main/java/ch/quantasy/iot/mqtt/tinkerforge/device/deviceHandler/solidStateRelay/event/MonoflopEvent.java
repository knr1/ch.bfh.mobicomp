/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.solidStateRelay.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.solidStateRelay.SolidStateRelay;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MonoflopEvent extends AnEvent {

    public MonoflopEvent(AHandler deviceHandler, String topic, MqttAsyncClient mqttClient) {
	super(deviceHandler, topic, "monoflop", mqttClient);
	super.addDescription(SolidStateRelay.MONOFLOP_TIME, Long.class, "JSON", "0", "...", "" + Long.MAX_VALUE);
	super.addDescription(SolidStateRelay.STATE, Boolean.class, "JSON", "true", "false");
    }
}
