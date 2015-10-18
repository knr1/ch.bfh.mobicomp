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
public class IntentDescription {

    public final String applicationIntentTopic;
    public final String intentTopic;
    public final String intentPropertyTopic;
    public final String type;
    public final String representation;
    public final String[] range;

    public IntentDescription(String applicationIntentTopic, String applicationName, String intentTopic, String intentPropertyTopic, String type, String representation, String... range) {
	this.applicationIntentTopic = "/" + applicationIntentTopic + "/" + applicationName + "/[identificationString]/intent";
	this.intentTopic = "/" + intentTopic;
	this.intentPropertyTopic = "/" + intentPropertyTopic;
	this.type = type;
	this.representation = representation;
	this.range = range;
    }

}
