/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.base.message;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.EventDescription;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class AnEvent<H extends AHandler> extends AMessage<H, EventDescription> {

    private final MqttAsyncClient mqttClient;

    public AnEvent(H deviceHandler, String eventTopic, String eventName, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, eventName);
	this.mqttClient = mqttClient;
    }

    @Override
    public String getMessageType() {
	return "event";
    }

    public void publishDescriptions() {
	super.publishDescriptions(mqttClient);
    }

    protected void addDescription(String propertyName, Class type, String representation, String... range) {
	super.addDescription(new EventDescription(AHandler.DEVICE_DESCRIPTION_TOPIC, getDeviceHandler().getApplicationName(), getName(), propertyName, type, representation, range));
    }

    public void update(AnIntent intent) {
	try {
	    super.update(mqttClient, intent);
	} catch (MqttException ex) {
	    Logger.getLogger(AnEvent.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public IMqttDeliveryToken update(String property, Object value) {
	return super.update(mqttClient, property, value);
    }

}
