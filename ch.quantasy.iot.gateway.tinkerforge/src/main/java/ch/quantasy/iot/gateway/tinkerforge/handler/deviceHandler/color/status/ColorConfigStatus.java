/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.status;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.Color;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ColorConfigStatus extends AStatus {

    public ColorConfigStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "config", mqttClient);
	super.addDescription(Color.GAIN, short.class, "JSON", "0", "1", "2", "3");
	super.addDescription(Color.INTEGRATION_TIME, short.class, "JSON", "0", "1", "2", "3", "4");

	super.addDescription(Color.ENABLED, Boolean.class, "JSON", "true", "false");
    }
}
