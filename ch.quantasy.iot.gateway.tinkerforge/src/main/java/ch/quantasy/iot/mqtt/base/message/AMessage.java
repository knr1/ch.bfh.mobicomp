/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.base.message;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.MessageDescription;
import ch.quantasy.iot.mqtt.tinkerforge.gateway.MQTT2TF;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class AMessage<H extends AHandler, E extends MessageDescription> {

    private final Gson gson;
    private final String topic;
    private final String name;
    List<E> descriptions = new ArrayList<>();
    private Map<String, Content> valueMap;
    //public final Type descriptionsType = new TypeToken<List<E>>() {
    //}.getType();
    private final H deviceHandler;
    private IMqttDeliveryToken mqttDeliveryToken;

    public AMessage(H deviceHandler, String messageTopic, String messageName) {
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

    public H getDeviceHandler() {
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
	for (E description : getDescriptions()) {
	    String json = gson.toJson(description);
	    MqttMessage message = new MqttMessage(json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	    message.setQos(1);
	    message.setRetained(true);
	    try {
		mqttClient.publish(AHandler.DEVICE_DESCRIPTION_TOPIC + "/" + deviceHandler.getApplicationName() + "/" + getMessageType() + "/" + name + description.messagePropertyTopic, message).waitForCompletion(500);

	    } catch (Exception ex) {
		Logger.getLogger(MQTT2TF.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

    }

    public IMqttDeliveryToken publish(String messagePropertyName, MqttMessage mqttMessage, MqttAsyncClient mqttClient) {
	mqttMessage.setQos(1);
	mqttMessage.setRetained(true);
	try {
	    return mqttClient.publish(topic + "/" + name + "/" + messagePropertyName, mqttMessage);
	} catch (Exception ex) {
	    Logger.getLogger(MQTT2TF.class.getName()).log(Level.SEVERE, null, ex);
	}
	return null;
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
	Content content = new Content(messageDescription);
	valueMap.put(content.getProperty(), content);
    }

    protected void update(MqttAsyncClient mqttClient, AnIntent intent) throws MqttException {
	for (Content content : this.getValueMap().values()) {
	    String property = content.getProperty();
	    if (property != null) {
		Content intentContent = intent.getContent(property);
		if (intentContent != null) {
		    IMqttDeliveryToken token = publish(property, toJSONMQTTMessage(intentContent.getValue(intentContent.description.typeOfClass)), mqttClient);
		    try {
			token.waitForCompletion(10);
		    } catch (Exception ex) {
			System.out.println(token.getException());
		    }
		}
	    }
	}
    }

    protected IMqttDeliveryToken update(MqttAsyncClient mqttClient, String property, Object value) {
	synchronized (this) {
	    if (mqttDeliveryToken == null || mqttDeliveryToken.isComplete()) {
		try {
		    mqttDeliveryToken = publish(property, toJSONMQTTMessage(value), mqttClient);
		    return mqttDeliveryToken;
		} catch (Exception ex) {
		    System.out.println("During publish, the following exception occured:" + ex.getStackTrace());
		}
	    }
	    return null;
	}
    }

    protected Map<String, Content> getValueMap() {
	return valueMap;
    }

    protected List<E> getDescriptions() {
	return descriptions;
    }
}
