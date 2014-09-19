/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.bus;

import ch.quantasy.messagebus.definition.BusFactory;
import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.messagebus.message.DefaultIntent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.implementation.AService;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <S>
 */
public abstract class ATinkerforgeService<I extends DefaultIntent, E extends DefaultEvent> extends AService<E, I> {

    @Override
    public BusFactory getBusFactory() {
	return TinkerforgeBusFactory.getInstance();
    }

    @Override
    public final void handleMessage(DefaultIntent message) {
	if (message == null) {
	    return;
	}
	if (message.containsReceiverIDs() && !message.containsReceiverID(this.getID())) {
	    return;
	}

	if (message instanceof ServiceDiscoveryIntent) {
	    E event = createServiceDiscoveryEvent();
	    publish(event);
	}
	try {
	    handleTinkerMessage((I) message);
	} catch (ClassCastException ex) {
	    //Nope, this intent is not for us...
	}
    }

    protected abstract void handleTinkerMessage(I message);

    @Override
    public abstract E createEvent();

    public static ServiceDiscoveryIntent getIntentForDeviceServiceDiscovery(Agent intentSender, String... intentReceivers) {
	return new ServiceDiscoveryIntent(intentSender, intentReceivers);
    }
}
