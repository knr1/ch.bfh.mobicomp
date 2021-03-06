/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.LinearPoti;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class PositionCallbackThresholdStatus extends AStatus {

    public PositionCallbackThresholdStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "positionCallbackThreshold", mqttClient);
	super.addDescription(LinearPoti.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "s", "g");
	super.addDescription(LinearPoti.THRESHOLD_MIN, Short.class, "JSON", "0", "...", "100");
	super.addDescription(LinearPoti.THRESHOLD_MAX, Short.class, "JSON", "0", "...", "100");
    }
}
