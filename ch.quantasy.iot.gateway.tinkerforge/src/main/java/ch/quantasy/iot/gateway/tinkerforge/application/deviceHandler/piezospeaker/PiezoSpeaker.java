/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.intent.CalibrationIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.intent.MorseIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.intent.BeepIntent;
import ch.quantasy.iot.gateway.tinkerforge.TFMQTTGateway;
import ch.quantasy.iot.gateway.tinkerforge.application.TinkerforgeMQTTPiezoSpeakerApplication;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.AnIntent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.google.gson.Gson;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class PiezoSpeaker implements BrickletPiezoSpeaker.MorseCodeFinishedListener, BrickletPiezoSpeaker.BeepFinishedListener, MqttCallback {

    private BrickletPiezoSpeaker device;
    private final String identityString;
    private final TinkerforgeStackAddress stackAddress;

    private final URI mqttURI;
    private final MqttAsyncClient mqttClient;
    private final String mqttClientID;

    private String statusTopic;
    private String eventTopic;
    private final String intentTopic;
    private String intentTopicEnabled;
    private String intentTopicBeep;
    private TinkerforgeMQTTPiezoSpeakerApplication stackApplication;

    private final Set<AnIntent> intents;

    public PiezoSpeaker(TinkerforgeMQTTPiezoSpeakerApplication stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws MqttException {
	this.stackApplication = stackApplication;
	this.mqttURI = mqttURI;
	this.stackAddress = stackAddress;
	this.identityString = identityString;
	this.mqttClientID = this.identityString;

	this.intentTopic = TFMQTTGateway.TOPIC + "/" + stackAddress.hostName + "/" + stackAddress.port + "/PiezoSpeaker/" + this.identityString + "/intent";
	this.statusTopic = TFMQTTGateway.TOPIC + "/" + stackAddress.hostName + "/" + stackAddress.port + "/PiezoSpeaker/" + this.identityString + "/status";
	this.mqttClient = new MqttAsyncClient(mqttURI.toString(), this.mqttClientID);

	connectToMQTT();
	intents = new HashSet<>();
	intents.add(new MorseIntent(this, intentTopic));
	intents.add(new BeepIntent(this, intentTopic));
	intents.add(new CalibrationIntent(this, intentTopic));
	for (AnIntent intent : intents) {
	    intent.publishTopicDefinition(mqttClient);
	}
	publishStatus();
    }

    public void publishStatus() {
	Gson gson = new Gson();
	String json = gson.toJson(true, Boolean.class);
	MqttMessage message = new MqttMessage(json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	message.setQos(1);
	message.setRetained(true);
	try {
	    mqttClient.publish(statusTopic + "/ready", message);
	} catch (Exception ex) {
	    Logger.getLogger(TFMQTTGateway.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public String getIdentityString() {
	return identityString;
    }

    public void enableDevice(BrickletPiezoSpeaker device) {
	try {
	    if (!this.identityString.equals(stackApplication.digestIdentityString(device.getIdentity().toString()))) {
		return;
	    }
	} catch (TimeoutException | NotConnectedException ex) {
	    return;
	}
	if (this.device != null) {
	    return;
	}
	this.device = device;
	this.device.addBeepFinishedListener(this);
	this.device.addMorseCodeFinishedListener(this);
    }

    public void disableDevice(BrickletPiezoSpeaker device) {
	if (!TinkerforgeDevice.areEqual(this.device, device)) {
	    return;
	}
	this.device.removeBeepFinishedListener(this);
	this.device.removeMorseCodeFinishedListener(this);
	this.device = null;

    }

    public BrickletPiezoSpeaker getDevice() {
	return this.device;
    }

    private void connectToMQTT() throws MqttException {
	MqttConnectOptions connectOptions = new MqttConnectOptions();
	connectOptions.setCleanSession(false);
	this.mqttClient.setCallback(this);
	IMqttToken token = this.mqttClient.connect(connectOptions);
	token.waitForCompletion();
	this.mqttClient.subscribe(intentTopic + "/#", 1);
    }

    public void disconnectFromMQTT() throws MqttException {
	IMqttToken token = this.mqttClient.disconnect();
	token.waitForCompletion();
    }

    @Override
    public void connectionLost(Throwable thrwbl) {

    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
	if (string.contains("intent")) {
	    for (AnIntent intent : intents) {
		intent.processMessage(string, mm);
	    }
	}
	if (string.contains("status")) {
	    for (AnIntent intent : intents) {
		intent.processMessage(string, mm);
	    }
	}
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {

    }

    @Override
    public void morseCodeFinished() {

    }

    @Override
    public void beepFinished() {

    }

}
