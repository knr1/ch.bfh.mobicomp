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
public class StatusDescription {

    public final String applicationStatusTopic;
    public final String statusTopic;
    public final String statusPropertyTopic;
    public final String type;
    public final String representation;
    public final String[] range;

    public StatusDescription(String applicationStatusTopic, String applicationName, String statusTopic, String statusPropertyTopic, String type, String representation, String... range) {
	this.applicationStatusTopic = "/" + applicationStatusTopic + "/" + applicationName + "/[identificationString]/status";
	this.statusTopic = "/" + statusTopic;
	this.statusPropertyTopic = "/" + statusPropertyTopic;
	this.type = type;
	this.representation = representation;
	this.range = range;
    }

}
