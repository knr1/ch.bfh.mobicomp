/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay.DualRelay;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DualRelayStatus extends AStatus {

    public DualRelayStatus(AHandler deviceHandler, String topic, MqttAsyncClient mqttClient) {
	super(deviceHandler, topic, "dualRelay", mqttClient);
	super.addDescription(DualRelay.DUALRELAY_RELAY1, Boolean.class, "JSON", "true", "false");
	super.addDescription(DualRelay.DUALRELAY_RELAY2, Boolean.class, "JSON", "true", "false");
    }
}
