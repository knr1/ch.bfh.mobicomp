/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.base.message;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.IntentDescription;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class AnIntent<H extends AHandler> extends AMessage<H, IntentDescription> {

    public AnIntent(H deviceHandler, String intentTopic, String intentName) {
	super(deviceHandler, intentTopic, intentName);
    }

    public <T> T getValue(String name, Class<T> classOfT) {

	return getContent(name).getValue(classOfT);
    }

    private boolean isProcessable(String mqttTopic) {
	return (mqttTopic != null && mqttTopic.startsWith(getTopic()) && mqttTopic.contains("/" + getName() + "/"));
    }

    public void processMessage(String string, MqttMessage mm) {
	if (!isProcessable(string)) {
	    return;
	}
	try {
	    update(string, mm);
	    if (isExecutable()) {
		execute();
	    }
	} catch (Throwable th) {
	    //OK, not yet ready to be executed. //For debug reason, the stack trace is printed
	    //th.printStackTrace();
	}
    }

    public <T> T fromMQTTMessage(MqttMessage message, Class<T> classOfT) {
	return getGson().fromJson(new InputStreamReader(new ByteArrayInputStream(message.getPayload())), classOfT);
    }

    @Override
    public String getMessageType() {
	return "intent";
    }

    protected void addDescription(String intentPropertyName, Class type, String representation, String... range) {
	super.addDescription(new IntentDescription(AHandler.DEVICE_DESCRIPTION_TOPIC, getDeviceHandler().getApplicationName(), getName(), intentPropertyName, type, representation, range));
    }

    //TODO: auf Message-Ebene nehmen?
    protected void update(String string, MqttMessage mm) throws Throwable {
	int offset = ("/" + getName() + "/").length();
	String name = string.substring(string.lastIndexOf("/" + getName() + "/") + offset);
	Content triple = getContent(name);
	if (triple != null) {
	    triple.rawValue = mm.getPayload();
	}
    }

    //Visitor
    public abstract void execute() throws Throwable;

    public abstract boolean isExecutable();

}
