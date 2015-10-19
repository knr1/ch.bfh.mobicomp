/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.base.message;

import ch.quantasy.iot.gateway.tinkerforge.TFMQTTGateway;
import ch.quantasy.iot.gateway.tinkerforge.application.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.base.EventDescription;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class AnEvent {

    private final MqttAsyncClient mqttClient;
    private final Gson gson;
    private final String eventTopic;
    private final String eventName;
    List<EventDescription> descriptions = new ArrayList<>();

    public final Type descriptionsType = new TypeToken<List<EventDescription>>() {
    }.getType();
    private final AHandler deviceHandler;

    public AnEvent(AHandler deviceHandler, String eventTopic, String eventName, MqttAsyncClient mqttClient) {
	this.mqttClient = mqttClient;
	this.eventName = eventName;
	this.deviceHandler = deviceHandler;
	this.eventTopic = eventTopic;
	this.gson = new Gson();

    }

    public AHandler getDeviceHandler() {
	return deviceHandler;
    }

    public String getEventTopic() {
	return eventTopic;
    }

    public String getEventName() {
	return eventName;
    }

    public void publishTopicDefinition() {
	String json = gson.toJson(getTopicDefinitions(), descriptionsType);
	MqttMessage message = new MqttMessage(json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	message.setQos(1);
	message.setRetained(true);
	try {
	    this.mqttClient.publish(AHandler.DEVICE_DESCRIPTION_TOPIC + "/" + deviceHandler.getApplicationName() + "/event/" + eventName, message);
	} catch (Exception ex) {
	    Logger.getLogger(TFMQTTGateway.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    public MqttMessage toJSONMQTTMessage(Object object, Type type) {
	String json = gson.toJson(object, type);
	return new MqttMessage(json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    public MqttMessage toJSONMQTTMessage(Object object) {
	String json = gson.toJson(object);
	return new MqttMessage(json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    public void publishEvent(String eventPropertyName, MqttMessage mqttMessage) {
	mqttMessage.setQos(1);
	mqttMessage.setRetained(true);
	try {
	    mqttClient.publish(eventTopic + "/" + eventName + "/" + eventPropertyName, mqttMessage);
	} catch (Exception ex) {
	    Logger.getLogger(TFMQTTGateway.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    protected void addTopicDefinition(String eventPropertyName, String type, String representation, String... range) {
	descriptions.add(new EventDescription(AHandler.DEVICE_DESCRIPTION_TOPIC, deviceHandler.getApplicationName(), eventName, eventPropertyName, type, representation, range));
    }

    protected List<EventDescription> getTopicDefinitions() {
	return descriptions;
    }

}
