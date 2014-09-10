/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.worker.implementation;

import ch.quantasy.messagebus.message.Event;
import ch.quantasy.messagebus.message.Intent;
import ch.quantasy.messagebus.worker.definition.Agent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <SEND>
 * @param <RECEIVE>
 */
public abstract class AnAgent<SEND extends Intent, RECEIVE extends Event> extends AWorker<SEND, RECEIVE> implements Agent<SEND, RECEIVE> {

    public AnAgent() {
	getBusFactory().getEventBus().subscribe(this);
    }

    @Override
    public void publish(SEND intent) {
	getBusFactory().getIntentBus().post(intent).asynchronously();
    }

}
