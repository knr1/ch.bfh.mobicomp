/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.location.serviceLocation;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.messagebus.worker.definition.Service;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
class ServiceLocationEvent extends DefaultEvent {

    private Set<ServiceLocation> locationSet;

    public ServiceLocationEvent(Service eventSender) {
	super(eventSender);
    }

    public void addServiceLocations(Collection<ServiceLocation> locations) {
	if (locationSet == null) {
	    locationSet = new HashSet<>();
	}
	locationSet.addAll(locations);
    }

    public void addServiceLocations(ServiceLocation... locations) {
	ServiceLocationEvent.this.addServiceLocations(Arrays.asList(locations));
    }

    public Set<ServiceLocation> getServiceLocationSet() {
	return locationSet;
    }

    @Override
    public String toString() {
	return super.toString() + " ServiceLocationEvent{" + "locationSet=" + locationSet + '}';
    }

}
