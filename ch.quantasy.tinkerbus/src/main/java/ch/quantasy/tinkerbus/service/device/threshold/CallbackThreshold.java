/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.threshold;

import java.io.Serializable;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CallbackThreshold implements Serializable {

    private static final long serialVersionUID = 42L;

    public final char option;
    public final short min;
    public final short max;

    public CallbackThreshold(char option, short min, short max) {
	this.option = option;
	this.min = min;
	this.max = max;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 31 * hash + this.option;
	hash = 31 * hash + this.min;
	hash = 31 * hash + this.max;
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
	final CallbackThreshold other = (CallbackThreshold) obj;
	if (this.option != other.option) {
	    return false;
	}
	if (this.min != other.min) {
	    return false;
	}
	if (this.max != other.max) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "CallbackThreshold{" + "option=" + option + ", min=" + min + ", max=" + max + '}';
    }

}
