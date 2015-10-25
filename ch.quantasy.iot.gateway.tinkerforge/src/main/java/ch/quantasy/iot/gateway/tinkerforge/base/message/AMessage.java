/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.base.message;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.MessageDescription;
import ch.quantasy.iot.gateway.tinkerforge.gateway.TFMQTTGateway;
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
public abstract class AMessage<E extends MessageDescription> {

    private final Gson gson;
    private final String topic;
    private final String name;
    List<E> descriptions = new ArrayList<>();

    public final Type descriptionsType = new TypeToken<List<E>>() {
    }.getType();
    private final AHandler deviceHandler;

    public AMessage(AHandler deviceHandler, String eventTopic, String eventName) {
	this.name = eventName;
	this.deviceHandler = deviceHandler;
	this.topic = eventTopic;
	this.gson = new Gson();

    }

    public Gson getGson() {
	return gson;
    }

    public AHandler getDeviceHandler() {
	return deviceHandler;
    }

    public String getTopic() {
	return topic;
    }

    public String getName() {
	return name;
    }

    public abstract String getMessageType();

    public void publishDescriptions(MqttAsyncClient mqttClient) {
	String json = gson.toJson(getDescriptions(), descriptionsType);
	MqttMessage message = new MqttMessage(json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	message.setQos(1);
	message.setRetained(true);
	try {
	    mqttClient.publish(AHandler.DEVICE_DESCRIPTION_TOPIC + "/" + deviceHandler.getApplicationName() + "/" + getMessageType() + "/" + name, message);
	} catch (Exception ex) {
	    Logger.getLogger(TFMQTTGateway.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    public void publish(String statusPropertyName, MqttMessage mqttMessage, MqttAsyncClient mqttClient) {
	mqttMessage.setQos(1);
	mqttMessage.setRetained(true);
	try {
	    mqttClient.publish(topic + "/" + name + "/" + statusPropertyName, mqttMessage);
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

    protected void addDescription(E messageDescription) {
	descriptions.add(messageDescription);
    }

    protected List<E> getDescriptions() {
	return descriptions;
    }

}
