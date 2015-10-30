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

    public void updateIntent(AnIntent intent) {
	for (Content triple : this.getValueMap().values()) {
	    byte[] content = intent.getContent(triple.getProperty()).rawValue;
	    if (content != null) {
		if (triple.updateContent(content)) {
		    publish(triple.getProperty(), toJSONMQTTMessage(content));
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
