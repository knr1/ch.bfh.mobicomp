/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceIO16.content;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class Port {

    public final char port;
    public final short valueMask;

    public Port(char port, short valueMask) {
	this.port = port;
	this.valueMask = valueMask;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 67 * hash + this.port;
	hash = 67 * hash + this.valueMask;
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
	final Port other = (Port) obj;
	if (this.port != other.port) {
	    return false;
	}
	if (this.valueMask != other.valueMask) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "Port{" + "port=" + port + ", valueMask=" + valueMask + '}';
    }

}
