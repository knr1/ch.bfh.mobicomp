/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.Accelerometer;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CallbackThresholdStatus extends AStatus {

    public CallbackThresholdStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "callbackThreshold", mqttClient);
	super.addDescription(Accelerometer.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(Accelerometer.THRESHOLD_MIN_X, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "4095");
	super.addDescription(Accelerometer.THRESHOLD_MIN_Y, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "4095");
	super.addDescription(Accelerometer.THRESHOLD_MIN_Z, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "4095");
	super.addDescription(Accelerometer.THRESHOLD_MAX_X, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "4095");
	super.addDescription(Accelerometer.THRESHOLD_MAX_Y, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "4095");
	super.addDescription(Accelerometer.THRESHOLD_MAX_Z, Short.class, "JSON", "" + Short.MIN_VALUE, "...", "4095");
    }
}
