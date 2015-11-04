/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.joystick.status;

import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.status.*;
import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.linearPoti.LinearPoti;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AnalogCallbackThresholdStatus extends AStatus {

    public AnalogCallbackThresholdStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "analogCallbackThreshold", mqttClient);
	super.addDescription(LinearPoti.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(LinearPoti.THRESHOLD_MIN, Integer.class, "JSON", "0", "...", "4095");
	super.addDescription(LinearPoti.THRESHOLD_MAX, Integer.class, "JSON", "0", "...", "4095");
    }
}
