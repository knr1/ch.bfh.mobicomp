/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.accelerometer.status;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.accelerometer.Accelerometer;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ConfigStatus extends AStatus {

    public ConfigStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "config", mqttClient);
	super.addDescription(Accelerometer.DATA_RATE, Short.class, "JSON", "0,1,2,3,4,5,6,7,8,9");
	super.addDescription(Accelerometer.FULL_SCALE, Short.class, "JSON", "0,1,2,3,4");
	super.addDescription(Accelerometer.FILTER_BANDWIDTH, Short.class, "JSON", "0,1,2,3");
	super.addDescription(Accelerometer.ENABLED, Boolean.class, "JSON", "true", "false");
    }
}
