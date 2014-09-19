/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.worker.definition;

import ch.quantasy.messagebus.message.Event;
import ch.quantasy.messagebus.message.Intent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <SEND>
 * @param <RECEIVE>
 */
public interface Service<SEND extends Event, RECEIVE extends Intent> extends Worker<SEND, RECEIVE> {

    public SEND createEvent();
}
