/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.worker.implementation;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.messagebus.message.DefaultIntent;
import ch.quantasy.messagebus.worker.definition.Service;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <SEND>
 * @param <RECEIVE>
 */
public abstract class AService<SEND extends DefaultEvent, RECEIVE extends DefaultIntent> extends AWorker<SEND, RECEIVE> implements Service<SEND, RECEIVE> {

    public AService() {
	getBusFactory().getIntentBus().subscribe(this);
    }

    public SEND createServiceDiscoveryEvent() {
	SEND event = createEvent();
	event.setDiscovered(true);
	return event;
    }

    @Override
    public void publish(SEND message) {
	getBusFactory().getEventBus().post(message).asynchronously();
    }

}
