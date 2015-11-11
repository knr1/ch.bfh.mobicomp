/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.base.message;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.MessageDescription;
import ch.quantasy.iot.mqtt.tinkerforge.gateway.MQTT2TFGateway;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Map<String, Content> valueMap;
    public final Type descriptionsType = new TypeToken<List<E>>() {
    }.getType();
    private final AHandler deviceHandler;

    public AMessage(AHandler deviceHandler, String messageTopic, String messageName) {
	valueMap = new HashMap<>();
	this.name = messageName;
	this.deviceHandler = deviceHandler;
	this.topic = messageTopic;
	this.gson = new Gson();
    }

    public Content getContent(String name) {
	return valueMap.get(name);
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
	    Logger.getLogger(MQTT2TFGateway.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    public void publish(String statusPropertyName, MqttMessage mqttMessage, MqttAsyncClient mqttClient) {
	mqttMessage.setQos(1);
	mqttMessage.setRetained(true);
	try {
	    mqttClient.publish(topic + "/" + name + "/" + statusPropertyName, mqttMessage);
	} catch (Exception ex) {
	    Logger.getLogger(MQTT2TFGateway.class.getName()).log(Level.SEVERE, null, ex);
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

    public MqttMessage toJSONMQTTMessage(byte[] byteArray) {
	return new MqttMessage(byteArray);
    }

    protected void addDescription(E messageDescription) {
	descriptions.add(messageDescription);
	Content triple = new Content(messageDescription);
	valueMap.put(triple.getProperty(), triple);
    }

    protected boolean update(MqttAsyncClient mqttClient, String property, Object value) {
	if (getContent(property).updateContent(value)) {
	    publish(property, toJSONMQTTMessage(value), mqttClient);
	    return true;
	}
	return false;
    }

    protected Map<String, Content> getValueMap() {
	return valueMap;
    }

    protected List<E> getDescriptions() {
	return descriptions;
    }
}
