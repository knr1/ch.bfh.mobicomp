/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.location.location;

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
public class LocationEvent extends DefaultEvent {

    private Set<Location> locationSet;

    public LocationEvent(Service eventSender) {
	super(eventSender);
    }

    public void addLocations(Collection<Location> locations) {
	if (locationSet == null) {
	    locationSet = new HashSet<>();
	}
	locationSet.addAll(locations);
    }

    public void addLocations(Location... locations) {
	this.addLocations(Arrays.asList(locations));
    }

    public Set<Location> getLocationSet() {
	return locationSet;
    }

}
