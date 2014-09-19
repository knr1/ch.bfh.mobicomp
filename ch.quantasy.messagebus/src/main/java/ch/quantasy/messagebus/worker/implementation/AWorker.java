/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.worker.implementation;

import ch.quantasy.messagebus.message.Message;
import ch.quantasy.messagebus.worker.definition.Worker;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <SEND>
 * @param <RECEIVE>
 */
public abstract class AWorker<SEND extends Message, RECEIVE extends Message> implements Worker<SEND, RECEIVE> {

    @Override
    @net.engio.mbassy.listener.Handler
    public void messageReceieved(RECEIVE message) {
	handleMessage(message);
    }

    public abstract void handleMessage(RECEIVE message);

}
