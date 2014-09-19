/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.location.location;

import java.util.Objects;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
class Location {

    private String locationDescription;
    private final String locationID;
    private Boolean valid;

    public Location(String locationID) {

	this.locationID = locationID;
    }

    public String getLocationID() {
	return locationID;
    }

    public void setLocationDescription(String locationDescription) {
	this.locationDescription = locationDescription;
    }

    public String getLocationDescription() {
	return locationDescription;
    }

    public Boolean isValid() {
	return valid != null && valid;
    }

    public void setValid(Boolean valid) {
	this.valid = valid;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 61 * hash + Objects.hashCode(this.locationID);
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
	final Location other = (Location) obj;
	if (!Objects.equals(this.locationID, other.locationID)) {
	    return false;
	}
	return true;
    }

}
