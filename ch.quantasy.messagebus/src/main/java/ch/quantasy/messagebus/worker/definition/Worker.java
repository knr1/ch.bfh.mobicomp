/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.worker.definition;

import ch.quantasy.messagebus.definition.BusFactory;
import ch.quantasy.messagebus.message.definition.Message;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <SEND>
 * @param <RECEIVE>
 */
public interface Worker<SEND extends Message, RECEIVE extends Message> {

    public String getID();

    public BusFactory getBusFactory();

    public void publish(SEND message);

    public void messageReceieved(RECEIVE message);
}
