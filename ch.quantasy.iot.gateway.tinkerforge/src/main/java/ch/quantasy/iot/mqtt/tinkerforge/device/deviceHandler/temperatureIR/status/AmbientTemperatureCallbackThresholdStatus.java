/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.TemperatureIR;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AmbientTemperatureCallbackThresholdStatus extends AStatus {

    public AmbientTemperatureCallbackThresholdStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "ambientTemperatureCallbackThreshold", mqttClient);
	super.addDescription(TemperatureIR.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "s", "g");
	super.addDescription(TemperatureIR.THRESHOLD_MIN, Integer.class, "JSON", "0", "...", "4095");
	super.addDescription(TemperatureIR.THRESHOLD_MAX, Integer.class, "JSON", "0", "...", "4095");
    }
}
