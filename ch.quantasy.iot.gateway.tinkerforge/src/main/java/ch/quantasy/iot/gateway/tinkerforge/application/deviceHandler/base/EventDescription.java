/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class EventDescription {

    public final String applicationEventTopic;
    public final String eventTopic;
    public final String eventPropertyTopic;
    public final String type;
    public final String representation;
    public final String[] range;

    public EventDescription(String applicationEventTopic, String applicationName, String eventTopic, String eventPropertyTopic, String type, String representation, String... range) {
	this.applicationEventTopic = "/" + applicationEventTopic + "/" + applicationName + "/[identificationString]/event";
	this.eventTopic = "/" + eventTopic;
	this.eventPropertyTopic = "/" + eventPropertyTopic;
	this.type = type;
	this.representation = representation;
	this.range = range;
    }

}
