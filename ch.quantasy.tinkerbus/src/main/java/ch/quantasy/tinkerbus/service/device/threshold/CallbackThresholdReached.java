/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.threshold;

import java.util.Objects;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CallbackThresholdReached {

    public final boolean thresholdReached;
    public final CallbackThreshold threshold;

    public CallbackThresholdReached(boolean thresholdReached, CallbackThreshold threshold) {
	this.thresholdReached = thresholdReached;
	this.threshold = threshold;
    }

    @Override
    public String toString() {
	return "CallbackThresholdReached{" + "thresholdReached=" + thresholdReached + ", threshold=" + threshold + '}';
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 59 * hash + (this.thresholdReached ? 1 : 0);
	hash = 59 * hash + Objects.hashCode(this.threshold);
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
	final CallbackThresholdReached other = (CallbackThresholdReached) obj;
	if (this.thresholdReached != other.thresholdReached) {
	    return false;
	}
	if (!Objects.equals(this.threshold, other.threshold)) {
	    return false;
	}
	return true;
    }

}
