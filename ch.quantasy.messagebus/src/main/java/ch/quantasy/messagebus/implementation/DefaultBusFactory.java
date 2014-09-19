/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.implementation;

import ch.quantasy.messagebus.definition.BusFactory;
import ch.quantasy.messagebus.message.Event;
import ch.quantasy.messagebus.message.Intent;
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
	intentBus = new MBassador<>(BusConfiguration.Default());
	eventBus = new MBassador<>(BusConfiguration.Default());
	instance = new DefaultBusFactory();
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
