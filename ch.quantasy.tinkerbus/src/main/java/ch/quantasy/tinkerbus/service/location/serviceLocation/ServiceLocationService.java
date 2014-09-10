/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.location.serviceLocation;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeService;
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
    protected void handleTinkerMessage(ServiceLocationIntent message) {
	if (message.isRequestServiceLocationSet()) {
	    ServiceLocationEvent event = createEvent();
	    event.addReceiverIDs(message.getSenderID());
	    event.addServiceLocations(serviceLocationSet);
	    publish(event);
	}
	synchronized (this) {
	    if (message.geServicetLocationSet() != null) {
		for (ServiceLocation location : message.geServicetLocationSet()) {
		    if (location.isValid()) {
			serviceLocationSet.add(location);
			if (!serviceLocationMapByLocation.containsKey(location.getLocationID())) {
			    serviceLocationMapByLocation.put(location.getLocationID(), new HashSet<ServiceLocation>());
			}
			serviceLocationMapByLocation.get(location.getLocationID()).add(location);

			if (!serviceLocationMapByDevice.containsKey(location.getServiceID())) {
			    serviceLocationMapByDevice.put(location.getServiceID(), new HashSet<ServiceLocation>());
			}
			serviceLocationMapByLocation.get(location.getServiceID()).add(location);
		    } else {
			serviceLocationSet.remove(location);
			if (!serviceLocationMapByLocation.containsKey(location.getLocationID())) {
			    Set<ServiceLocation> locations = serviceLocationMapByLocation.get(location.getLocationID());
			    locations.remove(location);
			    if (locations.isEmpty()) {
				serviceLocationMapByLocation.remove(location.getLocationID());
			    }
			}
			if (!serviceLocationMapByDevice.containsKey(location.getServiceID())) {
			    Set<ServiceLocation> locationSet = serviceLocationMapByDevice.get(location.getServiceID());
			    locationSet.remove(location);
			    if (locationSet.isEmpty()) {
				serviceLocationMapByDevice.remove(location.getServiceID());
			    }
			}
		    }
		}
		ServiceLocationEvent event = createEvent();
		event.addServiceLocations(serviceLocationSet);
		publish(event);
	    }
	}
    }

    @Override
    public ServiceLocationEvent createEvent() {
	return new ServiceLocationEvent(this);
    }

    public static ServiceLocationIntent createIntent(Agent agent) {
	ServiceLocationIntent intent = new ServiceLocationIntent(agent);
	return intent;
    }

    public static ServiceLocationEvent getServiceLocationEvent(DefaultEvent event) {
	if (event instanceof ServiceLocationEvent) {
	    return (ServiceLocationEvent) event;
	}
	return null;
    }

    @Override
    public String getID() {
	return "ServiceLocationService";
    }

}
