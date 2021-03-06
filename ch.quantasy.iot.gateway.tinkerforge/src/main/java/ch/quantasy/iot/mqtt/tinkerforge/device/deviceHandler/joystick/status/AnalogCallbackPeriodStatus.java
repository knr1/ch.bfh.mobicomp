/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.LinearPoti;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AnalogCallbackPeriodStatus extends AStatus {

    public AnalogCallbackPeriodStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "analogCallbackPeriod", mqttClient);
	super.addDescription(LinearPoti.PERIOD, Long.class, "JSON", "0", "..", "" + Long.MAX_VALUE);
    }
}
