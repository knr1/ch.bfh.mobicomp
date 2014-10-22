/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.dualRelay.content;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SelectedState {

    public final short relay;
    public final boolean state;

    public SelectedState(short relay, boolean state) {
	this.relay = relay;
	this.state = state;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 37 * hash + this.relay;
	hash = 37 * hash + (this.state ? 1 : 0);
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
	final SelectedState other = (SelectedState) obj;
	if (this.relay != other.relay) {
	    return false;
	}
	if (this.state != other.state) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "SelectedState{" + "relay=" + relay + ", state=" + state + '}';
    }

}
