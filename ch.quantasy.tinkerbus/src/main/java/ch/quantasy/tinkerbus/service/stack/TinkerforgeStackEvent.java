/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.stack;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
class TinkerforgeStackEvent extends DefaultEvent {

    private StackConnectionEventState stackConnectionEventState;
    private String exception;
    private TinkerforgeStackAddress stackAddress;

    public TinkerforgeStackEvent(Service eventSender) {
	super(eventSender);
    }

    public void setException(String exception) {
	this.exception = exception;
    }

    public void setStackAddress(TinkerforgeStackAddress stackAddress) {
	this.stackAddress = stackAddress;
    }

    public TinkerforgeStackAddress getStackAddress() {
	return stackAddress;
    }

    public String getException() {
	return exception;
    }

    public void setStackConnectionEventState(StackConnectionEventState stackConnectionEventState) {
	this.stackConnectionEventState = stackConnectionEventState;
    }

    public StackConnectionEventState getStackConnectionEventState() {
	return stackConnectionEventState;
    }

    @Override
    public String toString() {
	return "TinkerforgeStackEvent{" + "stackConnectionEventState=" + stackConnectionEventState + ", exception=" + exception + ", stackAddress=" + stackAddress + '}';
    }

}
