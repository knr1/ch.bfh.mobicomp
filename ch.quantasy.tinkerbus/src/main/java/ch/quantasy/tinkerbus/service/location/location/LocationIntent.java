/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.location.location;

import ch.quantasy.messagebus.message.DefaultIntent;
import ch.quantasy.messagebus.worker.definition.Agent;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
class LocationIntent extends DefaultIntent {

    private Boolean requestLocationSet;
    private Set<Location> locationSet;

    public void setLocation(Location location, boolean isValid) {
	if (locationSet == null) {
	    locationSet = new HashSet<>();
	}
	locationSet.add(location);
    }

    public void setRequestLocationSet(Boolean requestLocationSet) {
	this.requestLocationSet = requestLocationSet;
    }

    public Boolean isRequestLocationSet() {
	return requestLocationSet != null && requestLocationSet;
    }

    public Set<Location> getLocationSet() {
	return locationSet;
    }

    public LocationIntent(Agent intentSender) {
	super(intentSender);

    }

    public LocationIntent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);

    }

}
