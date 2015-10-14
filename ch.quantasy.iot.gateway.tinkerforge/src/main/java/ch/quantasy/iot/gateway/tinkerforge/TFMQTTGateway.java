/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge;

import ch.quantasy.iot.gateway.tinkerforge.application.TinkerforgeMQTTPiezoSpeakerApplication;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
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
public class TFMQTTGateway {

    public static final String TOPIC = "IOT/Gateway/TF";
    public static final String STATUS_SUB_TOPIC = "/status";
    public static final String AGENTS_SUB_TOPIC = STATUS_SUB_TOPIC + "/agents";
    public final String THIS_TFMQTT_GATEWAY_TOPIC;
    public final String THIS_STATUS_AGENTS_TOPIC;
    private final TinkerforgeStackAgency agency;
    private final MqttAsyncClient mqttClient;
    private final String clientID;
    private final URI mqttURI;
    private final Gson gson;
    Type stackAddressSetType = new TypeToken<Set<TinkerforgeStackAddress>>() {
    }.getType();

    public TFMQTTGateway(String clientID, URI mqttURI) throws MqttException {
	this.gson = new Gson();
	this.clientID = clientID;
	this.THIS_TFMQTT_GATEWAY_TOPIC = TOPIC + "/" + this.clientID;
	this.THIS_STATUS_AGENTS_TOPIC = THIS_TFMQTT_GATEWAY_TOPIC + AGENTS_SUB_TOPIC;
	this.agency = TinkerforgeStackAgency.getInstance();
	this.mqttURI = mqttURI;
	this.mqttClient = new MqttAsyncClient(this.mqttURI.toString(), this.clientID);
	connectToMQTT();
    }

    private void connectToMQTT() throws MqttException {
	MqttConnectOptions connectOptions = new MqttConnectOptions();
	connectOptions.setCleanSession(false);
	this.mqttClient.setCallback(new AgencyManager());
	IMqttToken token = this.mqttClient.connect(connectOptions);
	token.waitForCompletion();
	this.mqttClient.subscribe(THIS_STATUS_AGENTS_TOPIC, 1);
    }

    public void disconnectFromMQTT() throws MqttException {
	IMqttToken token = this.mqttClient.disconnect();
	token.waitForCompletion();
    }

    public void addNewAgents(Set<TinkerforgeStackAddress> stackAddresses) {
	if (stackAddresses == null || stackAddresses.isEmpty()) {
	    return;
	}
	for (TinkerforgeStackAddress address : stackAddresses) {
	    TinkerforgeStackAgent agent = this.agency.getStackAgent(address);
	    agent.addApplication(new TinkerforgeMQTTPiezoSpeakerApplication(mqttURI));
	}
	publishAgents();

    }

    public void addNewAgent(TinkerforgeStackAddress stackAddress) {

	TinkerforgeStackAgent agent = this.agency.getStackAgent(stackAddress);
	agent.addApplication(new TinkerforgeMQTTPiezoSpeakerApplication(mqttURI));

	publishAgents();

    }

    public void publishAgents() {
	String json = gson.toJson(agency.getTinkerforgeStackAddress(), stackAddressSetType);
	MqttMessage message = new MqttMessage(json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	message.setQos(1);
	message.setRetained(true);
	try {
	    this.mqttClient.publish(THIS_STATUS_AGENTS_TOPIC, message);
	} catch (Exception ex) {
	    Logger.getLogger(TFMQTTGateway.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    class AgencyManager implements MqttCallback {

	@Override
	public void connectionLost(Throwable thrwbl) {
	}

	@Override
	public void messageArrived(String string, MqttMessage mm) {
	    try {
		Set<TinkerforgeStackAddress> agentNameSet = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(mm.getPayload()), java.nio.charset.StandardCharsets.UTF_8), stackAddressSetType);
		agentNameSet.removeAll(agency.getTinkerforgeStackAddress());
		addNewAgents(agentNameSet);
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken imdt
	) {
	}

    }

    public static void main(String[] args) throws MqttException, IOException {
	final TinkerforgeStackAddress identifier = new TinkerforgeStackAddress(
		"localhost");
	TFMQTTGateway gw = new TFMQTTGateway("TFMQTTGateway", URI.create("tcp://localhost:1883"));
	gw.addNewAgent(identifier);
	System.in.read();
	gw.disconnectFromMQTT();

    }
}
