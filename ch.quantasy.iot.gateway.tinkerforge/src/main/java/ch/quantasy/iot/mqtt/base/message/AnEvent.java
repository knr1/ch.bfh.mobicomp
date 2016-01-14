/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.base.message;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.EventDescription;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class AnEvent extends AMessage<EventDescription> {

    private final MqttAsyncClient mqttClient;

    public AnEvent(AHandler deviceHandler, String eventTopic, String eventName, MqttAsyncClient mqttClient) {
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

    public void publish(String statusPropertyName, MqttMessage mqttMessage) {
	super.publish(statusPropertyName, mqttMessage, mqttClient);
    }

    protected void addDescription(String propertyName, Class type, String representation, String... range) {
	super.addDescription(new EventDescription(AHandler.DEVICE_DESCRIPTION_TOPIC, getDeviceHandler().getApplicationName(), getName(), propertyName, type, representation, range));
    }

    public void update(AnIntent intent) {
	for (Content triple : this.getContentMap().values()) {
	    byte[] rawContent = null;
	    String property = triple.getProperty();
	    if (property != null) {
		Content content = intent.getContent(property);
		if (content != null) {
		    rawContent = content.rawValue;
		}
	    }
	    if (rawContent != null) {
		if (triple.updateContent(rawContent)) {
		    publish(triple.getProperty(), toJSONMQTTMessage(rawContent));
		}
	    }
	}
    }

    public boolean update(String property, Object value) {
	return super.update(mqttClient, property, value);
    }

}
