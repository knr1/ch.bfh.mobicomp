/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.message.implementation;

import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.messagebus.worker.definition.Service;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class AnEvent extends AMessage<Service> implements Event {

    private final long serialVersionUID = 1L;

    public AnEvent(Service eventSender) {
	super(eventSender);
    }

}
