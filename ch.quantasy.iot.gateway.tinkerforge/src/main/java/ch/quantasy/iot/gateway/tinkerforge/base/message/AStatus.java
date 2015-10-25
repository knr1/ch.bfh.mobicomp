/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.base.message;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.StatusDescription;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class AStatus extends AMessage<StatusDescription> {

    private final MqttAsyncClient mqttClient;

    public AStatus(AHandler deviceHandler, String statusTopic, String statusName, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, statusName);

	this.mqttClient = mqttClient;
    }

    public void publish(String statusPropertyName, MqttMessage mqttMessage) {
	super.publish(statusPropertyName, mqttMessage, mqttClient);
    }

    public void publishDescriptions() {
	super.publishDescriptions(mqttClient);
    }

    @Override
    public String getMessageType() {
	return "status";
    }

    protected void addDescription(String propertyName, String type, String representation, String... range) {
	super.addDescription(new StatusDescription(AHandler.DEVICE_DESCRIPTION_TOPIC, getDeviceHandler().getApplicationName(), getName(), propertyName, type, representation, range));
    }

}
