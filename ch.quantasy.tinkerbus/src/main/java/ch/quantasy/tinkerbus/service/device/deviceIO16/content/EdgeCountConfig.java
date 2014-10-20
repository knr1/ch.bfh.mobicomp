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
public class EdgeCountConfig {

    public final short pin;
    public final short edgeType;
    public final short debounce;

    public EdgeCountConfig(short pin, short edgeType, short debounce) {
	this.pin = pin;
	this.edgeType = edgeType;
	this.debounce = debounce;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 89 * hash + this.pin;
	hash = 89 * hash + this.edgeType;
	hash = 89 * hash + this.debounce;
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
	final EdgeCountConfig other = (EdgeCountConfig) obj;
	if (this.pin != other.pin) {
	    return false;
	}
	if (this.edgeType != other.edgeType) {
	    return false;
	}
	if (this.debounce != other.debounce) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "EdgeCountConfig{" + "pin=" + pin + ", edgeType=" + edgeType + ", debounce=" + debounce + '}';
    }

}
