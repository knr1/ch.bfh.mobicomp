/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.location.serviceLocation;

import ch.quantasy.messagebus.message.implementation.AnEvent;
import ch.quantasy.messagebus.message.implementation.AnIntent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerbus.bus.ATinkerforgeService;
import ch.quantasy.tinkerbus.service.location.serviceLocation.content.ServiceLocationContent;
import ch.quantasy.tinkerbus.service.location.serviceLocation.content.ServiceLocationStateContent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ServiceLocationService extends ATinkerforgeService<ServiceLocationIntent, ServiceLocationEvent> {

    private final HashMap<String, Set<ServiceLocation>> serviceLocationMapByLocation;
    private final HashMap<String, Set<ServiceLocation>> serviceLocationMapByDevice;
    private final Set<ServiceLocation> serviceLocationSet;

    public ServiceLocationService() {
	serviceLocationMapByDevice = new HashMap<>();
	serviceLocationMapByLocation = new HashMap<>();
	serviceLocationSet = new HashSet<>();
    }

    @Override
    protected void handleIntent(ServiceLocationIntent message) {
	if (message == null) {
	    return;
	}
	ServiceLocationStateContent state = (ServiceLocationStateContent) message.getContentByID(ServiceLocationStateContent.class);
	if (state == null) {
	    return;
	}
	if (state.getValue() == ServiceLocationState.status) {
	    ServiceLocationEvent event = createEvent();
	    event.addReceiverIDs(message.getSenderID());
	    event.addContents(state);
	    event.addContents(new ServiceLocationContent(serviceLocationSet));
	    publish(event);
	} else {
	    ServiceLocationContent locations = (ServiceLocationContent) message.getContentByID(ServiceLocationContent.class);
	    if (locations.getValue() == null || locations.getValue().isEmpty()) {
		return;
	    }
	    Set<ServiceLocation> locSet = new HashSet(locations.getValue());
	    locSet.removeAll(this.serviceLocationSet);
	    if (locSet.isEmpty()) {
		return;
	    }
	    ServiceLocationEvent event = createEvent();
	    event.addContents(new ServiceLocationContent(locSet));

	    if (state.getValue() == ServiceLocationState.add) {
		serviceLocationSet.addAll(locSet);
		event.addContents(state);

	    }
	    if (state.getValue() == ServiceLocationState.remove) {
		serviceLocationSet.addAll(locSet);
		event.addContents(state);
	    }
	    publish(event);
	}
    }

    @Override
    public ServiceLocationEvent createEvent() {
	return new Event(this);
    }

    @Override
    public String getID() {
	return "ServiceLocationService";
    }

    public static ServiceLocationIntent add(Set<ServiceLocation> locations, Agent intentSender, String... intentReceivers) {
	ServiceLocationIntent intent = new Intent(intentSender, intentReceivers);
	intent.addContents(new ServiceLocationStateContent(ServiceLocationState.add));
	intent.addContents(new ServiceLocationContent(locations));
	return intent;
    }

    public static ServiceLocationIntent remove(Set<ServiceLocation> locations, Agent intentSender, String... intentReceivers) {
	ServiceLocationIntent intent = new Intent(intentSender, intentReceivers);
	intent.addContents(new ServiceLocationStateContent(ServiceLocationState.remove));
	intent.addContents(new ServiceLocationContent(locations));
	return intent;
    }

    public static ServiceLocationIntent status(Agent intentSender, String... intentReceivers) {
	ServiceLocationIntent intent = new Intent(intentSender, intentReceivers);
	intent.addContents(new ServiceLocationStateContent(ServiceLocationState.status));
	return intent;
    }

}

class Intent extends AnIntent implements ServiceLocationIntent {

    public Intent(Agent intentSender) {
	super(intentSender);
    }

    public Intent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
    }

}

class Event extends AnEvent implements ServiceLocationEvent {

    public Event(Service eventSender) {
	super(eventSender);
    }
}
