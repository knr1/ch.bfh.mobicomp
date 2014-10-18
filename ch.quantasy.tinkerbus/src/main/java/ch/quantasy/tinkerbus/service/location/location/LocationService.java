/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.location.location;

import ch.quantasy.messagebus.message.implementation.AnEvent;
import ch.quantasy.messagebus.message.implementation.AnIntent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerbus.bus.ATinkerforgeService;
import ch.quantasy.tinkerbus.service.location.location.content.LocationContent;
import ch.quantasy.tinkerbus.service.location.location.content.LocationStateContent;
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
    protected void handleIntent(LocationIntent message) {
	if (message == null) {
	    return;
	}
	LocationStateContent state = (LocationStateContent) message.getContentByID(LocationStateContent.class);
	if (state == null) {
	    return;
	}
	if (state.getValue() == LocationState.status) {
	    LocationEvent event = createEvent();
	    event.addReceiverIDs(message.getSenderID());
	    event.addContents(new LocationContent(locationSet));
	    publish(event);
	} else {
	    LocationContent locations = (LocationContent) message.getContentByID(LocationContent.class);
	    if (locations.getValue() == null || locations.getValue().isEmpty()) {
		return;
	    }
	    Set<Location> locSet = new HashSet(locations.getValue());
	    locSet.removeAll(this.locationSet);
	    if (locSet.isEmpty()) {
		return;
	    }
	    LocationEvent event = createEvent();
	    event.addContents(new LocationContent(locSet));

	    if (state.getValue() == LocationState.add) {
		locationSet.addAll(locSet);
		event.addContents(state);

	    }
	    if (state.getValue() == LocationState.remove) {
		locationSet.addAll(locSet);
		event.addContents(state);
	    }
	    publish(event);
	}
    }

    @Override
    public LocationEvent createEvent() {
	return new Event(this);
    }

    @Override
    public String getID() {
	return "LocationService";
    }

    public static LocationIntent add(Set<Location> locations, Agent intentSender, String... intentReceivers) {
	LocationIntent intent = new Intent(intentSender, intentReceivers);
	intent.addContents(new LocationStateContent(LocationState.add));
	intent.addContents(new LocationContent(locations));
	return intent;
    }

    public static LocationIntent remove(Set<Location> locations, Agent intentSender, String... intentReceivers) {
	LocationIntent intent = new Intent(intentSender, intentReceivers);
	intent.addContents(new LocationStateContent(LocationState.remove));
	intent.addContents(new LocationContent(locations));
	return intent;
    }

    public static LocationIntent status(Agent intentSender, String... intentReceivers) {
	LocationIntent intent = new Intent(intentSender, intentReceivers);
	intent.addContents(new LocationStateContent(LocationState.status));
	return intent;
    }

}

class Intent extends AnIntent implements LocationIntent {

    public Intent(Agent intentSender) {
	super(intentSender);
    }

    public Intent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
    }

}

class Event extends AnEvent implements LocationEvent {

    public Event(Service eventSender) {
	super(eventSender);
    }

}
