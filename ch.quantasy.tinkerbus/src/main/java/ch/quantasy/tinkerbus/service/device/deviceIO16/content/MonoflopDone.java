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
public class MonoflopDone {

    public final char port;
    public final short selectionMask;
    public final short valueMask;

    public MonoflopDone(char port, short selectionMask, short valueMask) {
	this.port = port;
	this.selectionMask = selectionMask;
	this.valueMask = valueMask;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 97 * hash + this.port;
	hash = 97 * hash + this.selectionMask;
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
	final MonoflopDone other = (MonoflopDone) obj;
	if (this.port != other.port) {
	    return false;
	}
	if (this.selectionMask != other.selectionMask) {
	    return false;
	}
	if (this.valueMask != other.valueMask) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "MonoflopDone{" + "port=" + port + ", selectionMask=" + selectionMask + ", valueMask=" + valueMask + '}';
    }

}
