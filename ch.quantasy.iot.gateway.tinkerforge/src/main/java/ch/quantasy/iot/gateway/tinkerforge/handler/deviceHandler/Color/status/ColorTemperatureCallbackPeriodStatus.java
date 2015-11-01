/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.Color.status;

import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.temperatureIR.status.*;
import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.temperatureIR.TemperatureIR;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ColorTemperatureCallbackPeriodStatus extends AStatus {

    public ColorTemperatureCallbackPeriodStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "altitudeCallbackThreshold", mqttClient);
	super.addDescription(TemperatureIR.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(TemperatureIR.THRESHOLD_MIN, Short.class, "JSON", "0", "...", "1000");
	super.addDescription(TemperatureIR.THRESHOLD_MAX, Short.class, "JSON", "0", "...", "1000");
    }
}
