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
public class MonoflopDone {

    public final short relay;
    public final boolean state;

    public MonoflopDone(short relay, boolean state) {
	this.relay = relay;
	this.state = state;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 29 * hash + this.relay;
	hash = 29 * hash + (this.state ? 1 : 0);
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
	return "MonoflopDone{" + "relay=" + relay + ", state=" + state + '}';
    }

}
