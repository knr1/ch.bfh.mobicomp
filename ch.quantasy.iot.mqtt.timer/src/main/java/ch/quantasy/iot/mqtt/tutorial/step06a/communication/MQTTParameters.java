/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tutorial.step06a.communication;

import ch.quantasy.iot.mqtt.tutorial.step06a.communication.*;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttCallback;

/**
 *
 * @author reto
 */
public class MQTTParameters {

    private URI[] serverURIs;
    private String clientID;

    private boolean isCleanSession;
    private String willTopic;
    private byte[] lastWillMessage;
    private boolean isLastWillRetained;
    private int lastWillQoS;

    private MqttCallback mqttCallback;

    private boolean inUse;

    public MQTTParameters() {

    }

    public MQTTParameters(String clientID, boolean isCleanSession, String willTopic, byte[] lastWillMessage, boolean isLastWillRetained, int lastWillQoS, MqttCallback mqttCallback, URI... serverURIs) {
        this.serverURIs = serverURIs;
        this.clientID = clientID;
        this.isCleanSession = isCleanSession;
        this.willTopic = willTopic;
        this.lastWillMessage = lastWillMessage;
        this.isLastWillRetained = isLastWillRetained;
        this.lastWillQoS = lastWillQoS;
        this.mqttCallback = mqttCallback;
    }

    public URI[] getServerURIs() {
        return serverURIs.clone();
    }

    public String[] getServerURIsAsString() {
        String[] urisAsStrings = new String[serverURIs.length];
        for (int i = 0; i < serverURIs.length; i++) {
            urisAsStrings[i] = serverURIs[i].toString();
        }
        return urisAsStrings;
    }

    public void setServerURIs(URI... serverURIs) {
        if (isInUse()) {
            return;
        }
        this.serverURIs = serverURIs.clone();
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        if (isInUse()) {
            return;
        }

        this.clientID = clientID;
    }

    public boolean isCleanSession() {
        return isCleanSession;
    }

    public void setIsCleanSession(boolean isCleanSession) {
        if (isInUse()) {
            return;
        }

        this.isCleanSession = isCleanSession;
    }

    public String getWillTopic() {
        return willTopic;
    }

    public void setWillTopic(String willTopic) {
        if (isInUse()) {
            return;
        }

        this.willTopic = willTopic;
    }

    public byte[] getLastWillMessage() {
        return lastWillMessage.clone();
    }

    public void setLastWillMessage(byte[] lastWillMessage) {
        if (isInUse()) {
            return;
        }

        this.lastWillMessage = lastWillMessage.clone();
    }

    public boolean isLastWillRetained() {
        return isLastWillRetained;
    }

    public void setIsLastWillRetained(boolean isLastWillRetained) {
        if (isInUse()) {
            return;
        }

        this.isLastWillRetained = isLastWillRetained;
    }

    public int getLastWillQoS() {
        return lastWillQoS;
    }

    public void setLastWillQoS(int lastWillQoS) {
        if (isInUse()) {
            return;
        }
        this.lastWillQoS = lastWillQoS;
    }

    public MqttCallback getMqttCallback() {
        return mqttCallback;
    }

    public void setMqttCallback(MqttCallback mqttCallback) {
        this.mqttCallback = mqttCallback;
    }

    public boolean isValid() {
        return this.clientID != null && this.serverURIs != null && willTopic != null && this.mqttCallback != null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Used by MQTTCommunication to indicate if a connection uses these
     * parameters
     *
     * @return
     */
    public boolean isInUse() {
        return inUse;
    }

    /**
     * Used by MQTTCommunication to indicate that a connection uses these
     * parameters.
     *
     * @param inUse
     */
    protected void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

}
