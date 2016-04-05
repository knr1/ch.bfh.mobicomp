/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tutorial.step01a.communication;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author reto
 */
public class MQTTCommunication implements IMqttActionListener{

    private MQTTParameters connectionParameters;
    private final MqttConnectOptions connectOptions;
    private IMqttAsyncClient mqttClient;
    
    public MQTTCommunication() {
        this.connectOptions = new MqttConnectOptions();
    }

    public synchronized void connect(MQTTParameters connectionParameters) throws MqttException {
        if (connectionParameters == null || !connectionParameters.isValid()) {
            return;
        }
        connectionParameters.setInUse(true);
        this.connectionParameters=connectionParameters;
        if (mqttClient == null) {
            mqttClient = new MqttAsyncClient(getMQTTParameters().getServerURIsAsString()[0], connectionParameters.getClientID(), new MemoryPersistence());
        }
        if (mqttClient.isConnected()) {
            return;
        }
        connectOptions.setServerURIs(connectionParameters.getServerURIsAsString());
        mqttClient.setCallback(connectionParameters.getMqttCallback());
        connectOptions.setCleanSession(connectionParameters.isCleanSession());
        connectOptions.setWill(connectionParameters.getWillTopic(), connectionParameters.getLastWillMessage(), connectionParameters.getLastWillQoS(), connectionParameters.isLastWillRetained());
        mqttClient.connect(connectOptions).waitForCompletion();
    }

    public synchronized IMqttDeliveryToken publishActualWill(byte[] actualWill) {
        MqttMessage message = new MqttMessage(actualWill);
        message.setQos(connectionParameters.getLastWillQoS());
        message.setRetained(connectionParameters.isLastWillRetained());
        return this.publish(connectionParameters.getWillTopic(), message);
    }

    public synchronized IMqttDeliveryToken publish(String topic, MqttMessage message) {
        try {
            if (mqttClient == null || !mqttClient.isConnected()) {
                return null;
            }
            return mqttClient.publish(topic, message);
        } catch (Exception ex) {
            return null;
        }
    }

    public synchronized IMqttToken subscribe(String topic, int qualityOfService) {
        try {
            if (mqttClient == null || !mqttClient.isConnected()) {
                return null;
            }
            return mqttClient.subscribe(topic, qualityOfService, null, this);
        } catch (Exception ex) {
            return null;
        }
    }

    public synchronized void disconnect() throws MqttException {
        if (mqttClient == null) {
            return;
        }
        mqttClient.disconnect();
        connectionParameters.setInUse(false);
    }

    public synchronized void disconnectForcibly() throws MqttException {
        if (mqttClient == null) {
            return;
        }
        mqttClient.disconnectForcibly();
        connectionParameters.setInUse(false);
    }

    public MQTTParameters getMQTTParameters() {
        return connectionParameters;
    }

    public synchronized boolean isConnected() {
        if (mqttClient == null) {
            return false;
        }
        return mqttClient.isConnected();
    }

    @Override
    public void onSuccess(IMqttToken imt) {
        System.out.printf("Success");
    }

    @Override
    public void onFailure(IMqttToken imt, Throwable thrwbl) {
        System.out.println("Fail"+thrwbl.toString());
    }

    
}
