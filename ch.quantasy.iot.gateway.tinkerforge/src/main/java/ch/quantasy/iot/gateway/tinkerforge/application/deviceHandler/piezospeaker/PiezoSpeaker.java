/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker;

import ch.quantasy.iot.gateway.tinkerforge.TFMQTTGateway;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.AnIntent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
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

    private final Set<AnIntent> intents;

    public PiezoSpeaker(URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws MqttException {
	this.mqttURI = mqttURI;
	this.stackAddress = stackAddress;
	this.identityString = identityString;
	this.mqttClientID = this.identityString;
	this.intentTopic = TFMQTTGateway.TOPIC + "/" + stackAddress.hostName + "/" + stackAddress.port + "/PiezoSpeaker/" + this.identityString + "/intent";
	this.mqttClient = new MqttAsyncClient(mqttURI.toString(), this.mqttClientID);

	connectToMQTT();
	intents = new HashSet<>();
	intents.add(new MorseIntent(this, intentTopic));
	intents.add(new BeepIntent(this, intentTopic));
	for (AnIntent intent : intents) {
	    intent.publishTopicDefinition(mqttClient);
	}
    }

    public String getIdentityString() {
	return identityString;
    }

    public void enableDevice(BrickletPiezoSpeaker device) {
	try {
	    if (!this.identityString.equals(device.getIdentity().toString())) {
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
	return device;
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
