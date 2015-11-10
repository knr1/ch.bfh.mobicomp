/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.rotaryEncoder.status;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.rotaryEncoder.RotaryEncoder;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CountCallbackThresholdStatus extends AStatus {

    public CountCallbackThresholdStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "countCallbackThreshold", mqttClient);
	super.addDescription(RotaryEncoder.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(RotaryEncoder.THRESHOLD_MIN, Short.class, "JSON", "" + Integer.MIN_VALUE, "...", "" + Integer.MAX_VALUE);
	super.addDescription(RotaryEncoder.THRESHOLD_MAX, Short.class, "JSON", "" + Integer.MIN_VALUE, "...", "" + Integer.MAX_VALUE);
    }
}
