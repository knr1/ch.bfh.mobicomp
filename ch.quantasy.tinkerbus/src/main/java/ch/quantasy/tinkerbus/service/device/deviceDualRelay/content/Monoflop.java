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
public class Monoflop {

    public final short relay;
    public final boolean state;
    public final long time;

    public Monoflop(short relay, boolean state, long time) {
	this.relay = relay;
	this.state = state;
	this.time = time;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 17 * hash + this.relay;
	hash = 17 * hash + (this.state ? 1 : 0);
	hash = 17 * hash + (int) (this.time ^ (this.time >>> 32));
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
	final Monoflop other = (Monoflop) obj;
	if (this.relay != other.relay) {
	    return false;
	}
	if (this.state != other.state) {
	    return false;
	}
	if (this.time != other.time) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "Monoflop{" + "relay=" + relay + ", state=" + state + ", time=" + time + '}';
    }

}
