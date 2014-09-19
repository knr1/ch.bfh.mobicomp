/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.bus;

import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.messagebus.worker.implementation.ServiceEvent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ServiceDiscoveryEvent extends ServiceEvent {

    public ServiceDiscoveryEvent(Service eventSender) {
	super(eventSender);
    }

}
