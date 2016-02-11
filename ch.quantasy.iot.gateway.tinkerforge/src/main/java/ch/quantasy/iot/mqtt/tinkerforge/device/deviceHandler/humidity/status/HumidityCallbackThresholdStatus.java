/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.Humidity;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class HumidityCallbackThresholdStatus extends AStatus {

    public HumidityCallbackThresholdStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "humidityCallbackThreshold", mqttClient);
	super.addDescription(Humidity.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "s", "g");
	super.addDescription(Humidity.THRESHOLD_MIN, Short.class, "JSON", "0", "...", "1000");
	super.addDescription(Humidity.THRESHOLD_MAX, Short.class, "JSON", "0", "...", "1000");
    }
}
