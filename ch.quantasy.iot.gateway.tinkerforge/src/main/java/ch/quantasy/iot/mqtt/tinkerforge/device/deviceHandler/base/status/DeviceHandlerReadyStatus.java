/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DeviceHandlerReadyStatus extends AStatus {

    public DeviceHandlerReadyStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "ready", mqttClient);
	super.addDescription(ADeviceHandler.ENABLED, Boolean.class, "JSON", "true", "false");

    }

    public void updateEnabled(boolean enabled) {
	if (super.getContent(ADeviceHandler.ENABLED).updateContent(enabled)) {
	    publish(ADeviceHandler.ENABLED, toJSONMQTTMessage(enabled));
	}
    }

}
