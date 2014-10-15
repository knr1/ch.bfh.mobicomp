/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.bus;

import ch.quantasy.messagebus.definition.BusFactory;
import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.messagebus.message.definition.Intent;
import ch.quantasy.messagebus.worker.implementation.AnAgent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class ATinkerforgeAgent extends AnAgent<Intent, Event> {

    @Override
    public BusFactory getBusFactory() {
	return TinkerforgeBusFactory.getInstance();
    }

}
