/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.base.message;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.StatusDescription;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <H>
 */
public abstract class AStatus<H extends AHandler> extends AMessage<H, StatusDescription> {

    private final MqttAsyncClient mqttClient;

    public AStatus(H deviceHandler, String statusTopic, String statusName, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, statusName);

	this.mqttClient = mqttClient;
    }

    public void update(AnIntent intent) {
	try {
	    super.update(mqttClient, intent);
	} catch (MqttException ex) {
	    Logger.getLogger(AStatus.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public IMqttDeliveryToken update(String property, Object value) {
	return super.update(mqttClient, property, value);
    }

    public void publishDescriptions() {
	super.publishDescriptions(mqttClient);
    }

    @Override
    public String getMessageType() {
	return "status";
    }

    protected void addDescription(String propertyName, Class type, String representation, String... range) {
	super.addDescription(new StatusDescription(AHandler.DEVICE_DESCRIPTION_TOPIC, getDeviceHandler().getApplicationName(), getName(), propertyName, type, representation, range));
    }

}
