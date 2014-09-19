/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.location.location;

import ch.quantasy.messagebus.message.Event;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeService;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LocationService extends ATinkerforgeService<LocationIntent, LocationEvent> {

    private final Set<Location> locationSet;

    public LocationService() {
	locationSet = new HashSet<>();
    }

    @Override
    protected void handleTinkerMessage(LocationIntent message) {
	if (message.isRequestLocationSet()) {
	    LocationEvent event = createEvent();
	    event.addReceiverIDs(message.getSenderID());
	    event.addLocations(locationSet);
	    publish(event);
	}
	synchronized (this) {
	    if (message.getLocationSet() != null) {
		for (Location location : message.getLocationSet()) {
		    if (location.isValid()) {
			locationSet.add(location);
		    } else {
			locationSet.remove(location);
		    }
		}
		LocationEvent event = createEvent();
		event.addLocations(locationSet);
		publish(event);
	    }
	}
    }

    @Override
    public LocationEvent createEvent() {
	return new LocationEvent(this);
    }

    public static LocationIntent createIntent(Agent agent) {
	return new LocationIntent(agent);
    }

    public static LocationEvent getLocationEvent(Event event) {
	if (event instanceof LocationEvent) {
	    return (LocationEvent) event;
	}
	return null;
    }

    @Override
    public String getID() {
	return "LocationService";
    }

}
