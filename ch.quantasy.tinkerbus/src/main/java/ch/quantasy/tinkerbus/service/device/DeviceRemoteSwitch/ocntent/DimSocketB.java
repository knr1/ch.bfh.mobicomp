/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.DeviceRemoteSwitch.ocntent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DimSocketB {

    public final long address;
    public final short unit;
    public final short dimValue;

    public DimSocketB(long address, short unit, short dimValue) {
	this.address = address;
	this.unit = unit;
	this.dimValue = dimValue;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 17 * hash + (int) (this.address ^ (this.address >>> 32));
	hash = 17 * hash + this.unit;
	hash = 17 * hash + this.dimValue;
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
	final DimSocketB other = (DimSocketB) obj;
	if (this.address != other.address) {
	    return false;
	}
	if (this.unit != other.unit) {
	    return false;
	}
	if (this.dimValue != other.dimValue) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "DimContent{" + "address=" + address + ", unit=" + unit + ", dimValue=" + dimValue + '}';
    }

}
