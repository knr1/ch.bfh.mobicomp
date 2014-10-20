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
public class PortInterrupt {

    public final char port;
    public final short interruptMask;

    public PortInterrupt(char port, short interruptMask) {
	this.port = port;
	this.interruptMask = interruptMask;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 29 * hash + this.port;
	hash = 29 * hash + this.interruptMask;
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
	final PortInterrupt other = (PortInterrupt) obj;
	if (this.port != other.port) {
	    return false;
	}
	if (this.interruptMask != other.interruptMask) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "Interrupt{" + "port=" + port + ", interruptMask=" + interruptMask + '}';
    }

}
