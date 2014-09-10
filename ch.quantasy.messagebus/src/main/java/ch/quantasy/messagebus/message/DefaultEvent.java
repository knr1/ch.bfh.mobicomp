/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.message;

import ch.quantasy.messagebus.worker.definition.Service;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DefaultEvent extends AMessage implements Event {

    private final long serialVersionUID = 1L;

    private Boolean discovered;

    public DefaultEvent(Service eventSender) {
	setSenderID(eventSender.getID());
	setTimeStamp(System.nanoTime());
    }

    public void setDiscovered(Boolean discovered) {
	this.discovered = discovered;
    }

    public Boolean isDiscovered() {
	return discovered;
    }

}
