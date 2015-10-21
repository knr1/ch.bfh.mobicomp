/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.gateway.status;

import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ManagedStackAddresses {

    public Set<TinkerforgeStackAddress> tinkerforgeStackAddressList;

    public ManagedStackAddresses(Set<TinkerforgeStackAddress> tinkerforgeStackAddressList) {
	this.tinkerforgeStackAddressList = tinkerforgeStackAddressList;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 29 * hash + Objects.hashCode(this.tinkerforgeStackAddressList);
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
	final ManagedStackAddresses other = (ManagedStackAddresses) obj;
	if (!Objects.equals(this.tinkerforgeStackAddressList, other.tinkerforgeStackAddressList)) {
	    return false;
	}
	return true;
    }

}
