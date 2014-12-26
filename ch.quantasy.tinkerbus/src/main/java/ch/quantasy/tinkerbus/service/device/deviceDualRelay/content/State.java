/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceDualRelay.content;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class State {

    public final boolean relay1;
    public final boolean relay2;

    public State(boolean relay1, boolean relay2) {
	this.relay1 = relay1;
	this.relay2 = relay2;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 97 * hash + (this.relay1 ? 1 : 0);
	hash = 97 * hash + (this.relay2 ? 1 : 0);
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
	final State other = (State) obj;
	if (this.relay1 != other.relay1) {
	    return false;
	}
	if (this.relay2 != other.relay2) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "State{" + "relay1=" + relay1 + ", relay2=" + relay2 + '}';
    }

}
