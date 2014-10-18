/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.bus;

import ch.quantasy.messagebus.definition.BusFactory;
import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.messagebus.message.definition.Intent;
import ch.quantasy.messagebus.worker.implementation.AService;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <I>
 * @param <E>
 */
public abstract class ATinkerforgeService<I extends Intent, E extends Event> extends AService<E, I> {

    @Override
    public BusFactory getBusFactory() {
	return TinkerforgeBusFactory.getInstance();
    }
}
