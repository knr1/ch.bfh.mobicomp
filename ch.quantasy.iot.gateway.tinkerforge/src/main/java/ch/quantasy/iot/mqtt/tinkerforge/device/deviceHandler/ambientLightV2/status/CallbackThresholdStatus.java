/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ambientLightV2.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ambientLightV2.AmbientLightV2;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CallbackThresholdStatus extends AStatus {

    public CallbackThresholdStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "callbackThreshold", mqttClient);
	super.addDescription(AmbientLightV2.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "s", "g");
	super.addDescription(AmbientLightV2.THRESHOLD_MIN, Integer.class, "JSON", "0", "...", "4095");
	super.addDescription(AmbientLightV2.THRESHOLD_MAX, Integer.class, "JSON", "0", "...", "4095");
    }
}
