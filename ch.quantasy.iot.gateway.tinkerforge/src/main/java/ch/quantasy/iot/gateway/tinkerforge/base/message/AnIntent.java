/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.base.message;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.IntentDescription;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class AnIntent extends AMessage {

    public AnIntent(AHandler deviceHandler, String intentTopic, String intentName) {
	super(deviceHandler, intentTopic, intentName);
    }

    private boolean isProcessable(String mqttTopic) {
	return (mqttTopic != null && mqttTopic.startsWith(getTopic()) && mqttTopic.contains(getName()));
    }

    public void processMessage(String string, MqttMessage mm) {
	if (!isProcessable(string)) {
	    return;
	}
	try {
	    update(string, mm);
	    if (isExecutable()) {
		getDeviceHandler().executeIntent(this);
	    }
	} catch (Throwable th) {
	    th.printStackTrace();
	}
    }

    public <T> T fromMQTTMessage(MqttMessage message, Class<T> classOfT) {
	return getGson().fromJson(new InputStreamReader(new ByteArrayInputStream(message.getPayload())), classOfT);
    }

    @Override
    public String getMessageType() {
	return "intent";
    }

    protected void addDescription(String intentPropertyName, String type, String representation, String... range) {
	super.addDescription(new IntentDescription(AHandler.DEVICE_DESCRIPTION_TOPIC, getDeviceHandler().getApplicationName(), getName(), intentPropertyName, type, representation, range));
    }

    protected abstract void update(String string, MqttMessage mm) throws Throwable;

    public abstract boolean isExecutable();

}
