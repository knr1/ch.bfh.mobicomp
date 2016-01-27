/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.tinker.core.implementation;

import java.util.Objects;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeStackAddress {

    public static final int DEFAULT_PORT = 4223;
    public String hostName;
    public int port;

    public TinkerforgeStackAddress(String hostName, int port) {
	if ((hostName == null) || hostName.equals("")) {
	    throw new IllegalArgumentException();
	}
	if ((port < 1) || (port > 65535)) {
	    throw new IllegalArgumentException();
	}
	this.hostName = hostName;
	this.port = port;
    }

    public TinkerforgeStackAddress(String host) {
	this(host, DEFAULT_PORT);
    }

    public TinkerforgeStackAddress() {

    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 29 * hash + Objects.hashCode(this.hostName);
	hash = 29 * hash + this.port;
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
	final TinkerforgeStackAddress other = (TinkerforgeStackAddress) obj;
	if (!Objects.equals(this.hostName, other.hostName)) {
	    return false;
	}
	if (this.port != other.port) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "TinkerforgeStackAddress{" + "hostName=" + hostName + ", port=" + port + '}';
    }

}
