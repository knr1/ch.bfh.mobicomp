/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.stack.registration;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeStackRegistrationEvent extends DefaultEvent {

    private StackRegistrationState registrationState;
    private String exception;
    private TinkerforgeStackAddress tinkerforgeStackAddress;
    private Set<String> stackServiceIDs;

    public TinkerforgeStackRegistrationEvent(Service eventSender) {
	super(eventSender);
    }

    public void setRegistrationState(StackRegistrationState registrationState) {
	this.registrationState = registrationState;
    }

    public void setTinkerforgeStackAddress(TinkerforgeStackAddress tinkerforgeStackAddress) {
	this.tinkerforgeStackAddress = tinkerforgeStackAddress;
    }

    public void addStackServiceID(String stackServiceID) {
	if (stackServiceIDs == null) {
	    stackServiceIDs = new HashSet<>();
	}
	this.stackServiceIDs.add(stackServiceID);
    }

    public void setException(String exception) {
	this.exception = exception;
    }

    public Set<String> getStackServiceIDs() {
	return new HashSet(stackServiceIDs);
    }

    public String getException() {
	return exception;
    }

    public StackRegistrationState getRegistrationState() {
	return registrationState;
    }

    public TinkerforgeStackAddress getTinkerforgeStackAddress() {
	return tinkerforgeStackAddress;
    }

    @Override
    public String toString() {
	return "TinkerforgeStackRegistrationEvent{" + "registrationState=" + registrationState + ", exception=" + exception + ", tinkerforgeStackAddress=" + tinkerforgeStackAddress + ", stackServiceIDs=" + stackServiceIDs + '}';
    }

}
