/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.implementation;

import ch.quantasy.messagebus.definition.BusFactory;
import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.messagebus.message.definition.Intent;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DefaultBusFactory implements BusFactory {

    private static final MBassador<Intent> intentBus;
    private static final MBassador<Event> eventBus;
    private static final DefaultBusFactory instance;

    static {
	intentBus = new MBassador<>(BusConfiguration.SyncAsync());
	eventBus = new MBassador<>(BusConfiguration.SyncAsync());
	instance = new DefaultBusFactory();
    }

    private DefaultBusFactory() {
	//Singleton.
    }

    @Override
    public MBassador<Intent> getIntentBus() {
	return intentBus;
    }

    @Override
    public MBassador<Event> getEventBus() {
	return eventBus;
    }

    public static DefaultBusFactory getInstance() {
	return instance;
    }

}
