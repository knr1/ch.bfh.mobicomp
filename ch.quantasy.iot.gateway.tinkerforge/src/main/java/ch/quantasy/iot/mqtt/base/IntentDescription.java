/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.base;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class IntentDescription extends MessageDescription {

    public IntentDescription(String applicationMessageTopic, String applicationName, String messageTopic, String messagePropertyTopic, Class type, String representation, String... range) {
	super(applicationMessageTopic, applicationName, messageTopic, messagePropertyTopic, type, representation, range);
    }

    @Override
    public String getMessageType() {
	return "intent";
    }

}
