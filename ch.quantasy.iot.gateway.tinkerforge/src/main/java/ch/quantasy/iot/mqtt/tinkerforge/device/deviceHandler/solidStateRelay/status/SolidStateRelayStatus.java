/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.solidStateRelay.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.solidStateRelay.SolidStateRelay;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SolidStateRelayStatus extends AStatus {

    public SolidStateRelayStatus(AHandler deviceHandler, String topic, MqttAsyncClient mqttClient) {
	super(deviceHandler, topic, "solidStateRelay", mqttClient);
	super.addDescription(SolidStateRelay.STATE, Boolean.class, "JSON", "true", "false");
    }
}
