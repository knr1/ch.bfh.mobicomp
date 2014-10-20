/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.worker.implementation;

import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.messagebus.message.definition.Intent;
import ch.quantasy.messagebus.message.implementation.AnIntent;
import ch.quantasy.messagebus.message.implementation.DiscoveryIntent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.definition.Service;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <SEND>
 * @param <RECEIVE>
 */
public abstract class AService<SEND extends Event, RECEIVE extends Intent> extends AWorker<SEND, RECEIVE> implements Service<SEND, RECEIVE> {

    public AService() {
	getBusFactory().getIntentBus().subscribe(this);
    }

    @Override
    public void publish(SEND message) {
	getBusFactory().getEventBus().post(message).asynchronously(100, TimeUnit.MILLISECONDS);
    }

    @Override
    public final void handleMessage(Intent message) {
	if (message == null) {
	    return;
	}
	if (message.containsReceiverIDs() && !message.containsReceiverID(this.getID())) {
	    return;
	}
	if (message instanceof DiscoveryIntent) {
	    publish(createEvent());
	}
	try {
	    handleIntent((RECEIVE) message);
	} catch (ClassCastException ex) {
	    //Nope, this intent is not for us...
	}
    }

    protected abstract void handleIntent(RECEIVE message);

    public static DiscoveryIntent discover(Agent intentSender, String... intentReceivers) {
	return new ServiceDiscoveryIntent(intentSender, intentReceivers);
    }

}

class ServiceDiscoveryIntent extends AnIntent implements DiscoveryIntent {

    public ServiceDiscoveryIntent(Agent intentSender) {
	super(intentSender);
    }

    public ServiceDiscoveryIntent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
    }

}
