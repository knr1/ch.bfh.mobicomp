/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base;

import ch.quantasy.iot.gateway.tinkerforge.TFMQTTGateway;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
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
public abstract class AnIntent {

    private final Gson gson;
    private final String intentTopic;
    private final String intentName;
    List<IntentDescription> descriptions = new ArrayList<>();

    public final Type descriptionsType = new TypeToken<List<IntentDescription>>() {
    }.getType();
    private final ADeviceHandler deviceHandler;

    public AnIntent(ADeviceHandler deviceHandler, String intentTopic, String intentName) {
	this.intentName = intentName;
	this.deviceHandler = deviceHandler;
	this.intentTopic = intentTopic;
	this.gson = new Gson();

    }

    public ADeviceHandler getDeviceHandler() {
	return deviceHandler;
    }

    public String getIntentTopic() {
	return intentTopic;
    }

    public String getIntentName() {
	return intentName;
    }

    private boolean isProcessable(String mqttTopic) {
	return (mqttTopic != null && mqttTopic.startsWith(this.intentTopic) && mqttTopic.contains(intentName));
    }

    public void processMessage(String string, MqttMessage mm) {
	if (!isProcessable(string)) {
	    return;
	}
	try {
	    update(string, mm);
	    if (isExecutable()) {
		deviceHandler.executeIntent(this);
	    }
	} catch (Throwable th) {
	    th.printStackTrace();
	}
    }

    public <T> T fromMQTTMessage(MqttMessage message, Class<T> classOfT) {
	return gson.fromJson(new InputStreamReader(new ByteArrayInputStream(message.getPayload())), classOfT);
    }

    public void publishTopicDefinition(MqttAsyncClient mqttClient) {
	String json = gson.toJson(getTopicDefinitions(), descriptionsType);
	MqttMessage message = new MqttMessage(json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	message.setQos(1);
	message.setRetained(true);
	try {
	    mqttClient.publish(ADeviceHandler.DEVICE_DESCRIPTION_TOPIC + "/" + deviceHandler.getApplicationName() + "/intent/" + intentName, message);
	} catch (Exception ex) {
	    Logger.getLogger(TFMQTTGateway.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    protected void addTopicDefinition(String intentPropertyName, String type, String representation, String... range) {
	descriptions.add(new IntentDescription(ADeviceHandler.DEVICE_DESCRIPTION_TOPIC, deviceHandler.getApplicationName(), intentName, intentPropertyName, type, representation, range));
    }

    protected abstract void update(String string, MqttMessage mm) throws Throwable;

    public abstract boolean isExecutable();

    protected List<IntentDescription> getTopicDefinitions() {
	return descriptions;
    }

}
