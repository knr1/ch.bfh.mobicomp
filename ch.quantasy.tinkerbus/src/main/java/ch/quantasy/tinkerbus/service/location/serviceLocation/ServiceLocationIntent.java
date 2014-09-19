/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.location.serviceLocation;

import ch.quantasy.messagebus.message.DefaultIntent;
import ch.quantasy.messagebus.worker.definition.Agent;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
class ServiceLocationIntent extends DefaultIntent {

    private Boolean requestServiceLocationSet;
    private Set<ServiceLocation> serviceLocationSet;
    private String requestLocationsForService;
    private String requestServiceForLocations;

    public void setServiceLocationSet(ServiceLocation serviceLocation, boolean isValid) {
	if (serviceLocationSet == null) {
	    serviceLocationSet = new HashSet<>();
	}
	serviceLocationSet.add(serviceLocation);
    }

    public void setRequestServiceLocationSet(Boolean requestLocationSet) {
	this.requestServiceLocationSet = requestLocationSet;
    }

    public Boolean isRequestServiceLocationSet() {
	return requestServiceLocationSet != null && requestServiceLocationSet;
    }

    public Set<ServiceLocation> geServicetLocationSet() {
	return serviceLocationSet;
    }

    public ServiceLocationIntent(Agent intentSender) {
	super(intentSender);

    }

    public ServiceLocationIntent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);

    }

}
