/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.base.message;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.StatusDescription;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

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
	for (Content content : this.getValueMap().values()) {
	    byte[] intentContent = intent.getContent(content.getProperty()).rawValue;
	    if (intentContent != null) {
		if (content.updateContent(intentContent)) {
		    publish(content.getProperty(), toJSONMQTTMessage(intentContent));
		}
	    }
	}
    }

    public boolean update(String property, Object value) {
	return super.update(mqttClient, property, value);
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

    protected void addDescription(String propertyName, Class type, String representation, String... range) {
	super.addDescription(new StatusDescription(AHandler.DEVICE_DESCRIPTION_TOPIC, getDeviceHandler().getApplicationName(), getName(), propertyName, type, representation, range));
    }

}
