/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.device;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class Definition {

    public final String topic;
    public final String type;
    public final String representation;
    public final String[] range;

    public Definition(String topic, String type, String representation, String... range) {
	this.topic = topic;
	this.type = type;
	this.representation = representation;
	this.range = range;
    }

}
