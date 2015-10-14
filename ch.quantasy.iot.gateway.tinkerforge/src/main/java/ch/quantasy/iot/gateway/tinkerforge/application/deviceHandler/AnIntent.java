/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler;

import ch.quantasy.iot.gateway.tinkerforge.TFMQTTGateway;
import ch.quantasy.iot.gateway.tinkerforge.application.TinkerforgeMQTTPiezoSpeakerApplication;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.PiezoSpeaker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tinkerforge.Device;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class AnIntent< D extends Device> {

    public final Gson gson;
    private String intentTopic;
    private String spezializedIntentTopic;

    public final Type definitionsType = new TypeToken<List<Definition>>() {
    }.getType();
    private final PiezoSpeaker deviceHandler;

    public AnIntent(PiezoSpeaker deviceHandler, String intentTopic, String spezializedIntentTopic) {
	this.spezializedIntentTopic = spezializedIntentTopic;
	this.deviceHandler = deviceHandler;
	this.intentTopic = intentTopic;
	this.gson = new Gson();

    }

    public PiezoSpeaker getDeviceHandler() {
	return deviceHandler;
    }

    public String getIntentTopic() {
	return intentTopic;
    }

    public String getSpezializedIntentTopic() {
	return spezializedIntentTopic;
    }

    public void processMessage(String string, MqttMessage mm) {
	if (!string.startsWith(this.intentTopic) || !string.contains(spezializedIntentTopic)) {
	    return;
	}
	try {
	    processSpezializedIntent(string, mm);
	} catch (Throwable th) {
	    th.printStackTrace();
	}
    }

    public void publishTopicDefinition(MqttAsyncClient mqttClient) {
	String json = gson.toJson(getIntentTopicDefinitions(), definitionsType);
	MqttMessage message = new MqttMessage(json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	message.setQos(1);
	message.setRetained(true);
	try {
	    mqttClient.publish(TinkerforgeMQTTPiezoSpeakerApplication.APPLICATION_DEFINITION + "/intent" + spezializedIntentTopic, message);
	} catch (Exception ex) {
	    Logger.getLogger(TFMQTTGateway.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    protected abstract void processSpezializedIntent(String string, MqttMessage mm);

    protected abstract List<Definition> getIntentTopicDefinitions();

}
