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
public class PortConfiguration {

    public final char port;
    public final short selectionMask;
    public final char direction;
    public final boolean value;

    public PortConfiguration(char port, short selectionMask, char direction, boolean value) {
	this.port = port;
	this.selectionMask = selectionMask;
	this.direction = direction;
	this.value = value;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 61 * hash + this.port;
	hash = 61 * hash + this.selectionMask;
	hash = 61 * hash + this.direction;
	hash = 61 * hash + (this.value ? 1 : 0);
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
	final PortConfiguration other = (PortConfiguration) obj;
	if (this.port != other.port) {
	    return false;
	}
	if (this.selectionMask != other.selectionMask) {
	    return false;
	}
	if (this.direction != other.direction) {
	    return false;
	}
	if (this.value != other.value) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "PortConfiguration{" + "port=" + port + ", selectionMask=" + selectionMask + ", direction=" + direction + ", value=" + value + '}';
    }

}
