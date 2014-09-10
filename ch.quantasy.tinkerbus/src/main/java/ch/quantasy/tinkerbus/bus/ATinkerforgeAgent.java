/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.bus;

import ch.quantasy.messagebus.definition.BusFactory;
import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.messagebus.message.DefaultIntent;
import ch.quantasy.messagebus.worker.implementation.AnAgent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class ATinkerforgeAgent extends AnAgent<DefaultIntent, DefaultEvent> {

    @Override
    public void handleMessage(DefaultEvent message) {

	if (message == null) {
	    return;
	}
	if (message.containsReceiverIDs() && !message.containsReceiverID(this.getID())) {
	    return;
	}
	handleTinkerMessage(message);
    }

    protected abstract void handleTinkerMessage(DefaultEvent message);

    @Override
    public BusFactory getBusFactory() {
	return TinkerforgeBusFactory.getInstance();
    }

}
