/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dustDetector.DustDetector;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DustDensityCallbackThresholdStatus extends AStatus {

    public DustDensityCallbackThresholdStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "dustDensityCallbackThreshold", mqttClient);
	super.addDescription(DustDetector.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(DustDetector.THRESHOLD_MIN, Integer.class, "JSON", "0", "...", "500");
	super.addDescription(DustDetector.THRESHOLD_MAX, Integer.class, "JSON", "0", "...", "500");
    }
}
