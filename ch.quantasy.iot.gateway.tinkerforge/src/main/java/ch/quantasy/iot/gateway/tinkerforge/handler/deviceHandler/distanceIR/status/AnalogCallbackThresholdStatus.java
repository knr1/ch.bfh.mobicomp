/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceIR.status;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceIR.DistanceIR;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AnalogCallbackThresholdStatus extends AStatus {

    public AnalogCallbackThresholdStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "analogCallbackThreshold", mqttClient);
	super.addDescription(DistanceIR.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(DistanceIR.THRESHOLD_MIN, Integer.class, "JSON", "0", "...", "4095");
	super.addDescription(DistanceIR.THRESHOLD_MAX, Integer.class, "JSON", "0", "...", "4095");
    }
}
