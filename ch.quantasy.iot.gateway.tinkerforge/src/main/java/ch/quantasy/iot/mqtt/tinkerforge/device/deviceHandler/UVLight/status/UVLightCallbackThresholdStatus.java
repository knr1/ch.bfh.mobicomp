/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.UVLight.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.UVLight.UVLight;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class UVLightCallbackThresholdStatus extends AStatus {

    public UVLightCallbackThresholdStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "UVLightCallbackThreshold", mqttClient);
	super.addDescription(UVLight.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "s", "g");
	super.addDescription(UVLight.THRESHOLD_MIN, Long.class, "JSON", "0", "...", "328000");
	super.addDescription(UVLight.THRESHOLD_MAX, Long.class, "JSON", "0", "...", "328000");
    }
}
