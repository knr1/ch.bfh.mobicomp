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
public class Interrupt {

    public final char port;
    public final short interruptMask;
    public final short valueMask;

    public Interrupt(char port, short interruptMask, short valueMask) {
	this.port = port;
	this.interruptMask = interruptMask;
	this.valueMask = valueMask;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 97 * hash + this.port;
	hash = 97 * hash + this.interruptMask;
	hash = 97 * hash + this.valueMask;
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
	final Interrupt other = (Interrupt) obj;
	if (this.port != other.port) {
	    return false;
	}
	if (this.interruptMask != other.interruptMask) {
	    return false;
	}
	if (this.valueMask != other.valueMask) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "Interrupt{" + "port=" + port + ", interruptMask=" + interruptMask + ", valueMask=" + valueMask + '}';
    }

}
