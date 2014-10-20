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
public class PortMonoflop {

    public final char port;
    public final short selectionMask;
    public final short valueMask;
    public final long time;

    public PortMonoflop(char port, short selectionMask, short valueMask, long time) {
	this.port = port;
	this.selectionMask = selectionMask;
	this.valueMask = valueMask;
	this.time = time;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 13 * hash + this.port;
	hash = 13 * hash + this.selectionMask;
	hash = 13 * hash + this.valueMask;
	hash = 13 * hash + (int) (this.time ^ (this.time >>> 32));
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
	final PortMonoflop other = (PortMonoflop) obj;
	if (this.port != other.port) {
	    return false;
	}
	if (this.selectionMask != other.selectionMask) {
	    return false;
	}
	if (this.valueMask != other.valueMask) {
	    return false;
	}
	if (this.time != other.time) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "PortMonoflop{" + "port=" + port + ", selectionMask=" + selectionMask + ", valueMask=" + valueMask + ", time=" + time + '}';
    }

}
