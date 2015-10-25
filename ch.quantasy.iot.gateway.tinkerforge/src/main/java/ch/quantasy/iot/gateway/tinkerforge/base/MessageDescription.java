/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.base;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class MessageDescription {

    public final String applicationMessageTopic;
    public final String messageTopic;
    public final String messagePropertyTopic;
    public final String type;
    public final String representation;
    public final String[] range;

    public MessageDescription(String applicationMessageTopic, String applicationName, String messageTopic, String messagePropertyTopic, String type, String representation, String... range) {
	this.applicationMessageTopic = "/" + applicationMessageTopic + "/" + applicationName + "/[identificationString]/" + getMessageType();
	this.messageTopic = "/" + messageTopic;
	this.messagePropertyTopic = "/" + messagePropertyTopic;
	this.type = type;
	this.representation = representation;
	this.range = range;
    }

    public abstract String getMessageType();
}
