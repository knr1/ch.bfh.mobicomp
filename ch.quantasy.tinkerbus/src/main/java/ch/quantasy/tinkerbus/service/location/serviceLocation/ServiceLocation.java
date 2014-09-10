/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.location.serviceLocation;

import java.util.Objects;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
class ServiceLocation {

    private final String serviceID;
    private final String locationID;
    private final Boolean valid;

    public ServiceLocation(String serviceID, String locationID, boolean isValid) {
	this.serviceID = serviceID;
	this.locationID = locationID;
	this.valid = isValid;
    }

    public String getServiceID() {
	return serviceID;
    }

    public String getLocationID() {
	return locationID;
    }

    public Boolean isValid() {
	return valid != null && valid;
    }

    @Override
    public String toString() {
	return "Location{" + "serviceID=" + serviceID + ", locationID=" + locationID + ", valid=" + valid + '}';
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 43 * hash + Objects.hashCode(this.serviceID);
	hash = 43 * hash + Objects.hashCode(this.locationID);
	hash = 43 * hash + Objects.hashCode(this.valid);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final ServiceLocation other = (ServiceLocation) obj;
	if (!Objects.equals(this.serviceID, other.serviceID)) {
	    return false;
	}
	if (!Objects.equals(this.locationID, other.locationID)) {
	    return false;
	}
	if (!Objects.equals(this.valid, other.valid)) {
	    return false;
	}
	return true;
    }

}
