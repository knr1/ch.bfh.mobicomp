/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.base.message;

import ch.quantasy.iot.gateway.tinkerforge.TFMQTTGateway;
import ch.quantasy.iot.gateway.tinkerforge.application.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.base.StatusDescription;
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
public abstract class AStatus {

    private final MqttAsyncClient mqttClient;
    private final Gson gson;
    private final String statusTopic;
    private final String statusName;
    List<StatusDescription> descriptions = new ArrayList<>();

    public final Type descriptionsType = new TypeToken<List<StatusDescription>>() {
    }.getType();
    private final AHandler deviceHandler;

    public AStatus(AHandler deviceHandler, String statusTopic, String statusName, MqttAsyncClient mqttClient) {
	this.mqttClient = mqttClient;
	this.statusName = statusName;
	this.deviceHandler = deviceHandler;
	this.statusTopic = statusTopic;
	this.gson = new Gson();

    }

    public AHandler getDeviceHandler() {
	return deviceHandler;
    }

    public String getStatusTopic() {
	return statusTopic;
    }

    public String getStatusName() {
	return statusName;
    }

    public void publishTopicDefinition() {
	String json = gson.toJson(getTopicDefinitions(), descriptionsType);
	MqttMessage message = new MqttMessage(json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	message.setQos(1);
	message.setRetained(true);
	try {
	    this.mqttClient.publish(AHandler.DEVICE_DESCRIPTION_TOPIC + "/" + deviceHandler.getApplicationName() + "/status/" + statusName, message);
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

    public void publishStatus(String statusPropertyName, MqttMessage mqttMessage) {
	mqttMessage.setQos(1);
	mqttMessage.setRetained(true);
	try {
	    mqttClient.publish(statusTopic + "/" + statusName + "/" + statusPropertyName, mqttMessage);
	} catch (Exception ex) {
	    Logger.getLogger(TFMQTTGateway.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    protected void addTopicDescription(String statusPropertyName, String type, String representation, String... range) {
	descriptions.add(new StatusDescription(AHandler.DEVICE_DESCRIPTION_TOPIC, deviceHandler.getApplicationName(), statusName, statusPropertyName, type, representation, range));
    }

    protected List<StatusDescription> getTopicDefinitions() {
	return descriptions;
    }

}
