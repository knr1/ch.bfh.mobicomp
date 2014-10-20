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
public class SelectedValues {

    public final char port;
    public final short selectionMask;
    public final short valueMask;

    public SelectedValues(char port, short selectionMask, short valueMask) {
	this.port = port;
	this.selectionMask = selectionMask;
	this.valueMask = valueMask;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 19 * hash + this.port;
	hash = 19 * hash + this.selectionMask;
	hash = 19 * hash + this.valueMask;
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
	final SelectedValues other = (SelectedValues) obj;
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
	return "SelectedValue{" + "port=" + port + ", selectionMask=" + selectionMask + ", valueMask=" + valueMask + '}';
    }

}
