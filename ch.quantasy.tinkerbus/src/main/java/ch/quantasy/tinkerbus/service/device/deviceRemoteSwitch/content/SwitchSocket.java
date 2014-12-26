/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceRemoteSwitch.content;

import com.tinkerforge.BrickletRemoteSwitch;
import java.util.Objects;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SwitchSocket {

    public Socket socket;
    public final long houseCode;
    public final short receiverCode;
    public final boolean switchTo;

    public SwitchSocket(Socket socket, long houseCode, short receiverCode, boolean switchTo) {
	this.socket = socket;
	this.houseCode = houseCode;
	this.receiverCode = receiverCode;
	this.switchTo = switchTo;
    }

    public short getSwitchTo() {
	return this.switchTo ? BrickletRemoteSwitch.SWITCH_TO_ON : BrickletRemoteSwitch.SWITCH_TO_OFF;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 53 * hash + Objects.hashCode(this.socket);
	hash = 53 * hash + (int) (this.houseCode ^ (this.houseCode >>> 32));
	hash = 53 * hash + this.receiverCode;
	hash = 53 * hash + (this.switchTo ? 1 : 0);
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
	final SwitchSocket other = (SwitchSocket) obj;
	if (this.socket != other.socket) {
	    return false;
	}
	if (this.houseCode != other.houseCode) {
	    return false;
	}
	if (this.receiverCode != other.receiverCode) {
	    return false;
	}
	return this.switchTo == other.switchTo;
    }

    @Override
    public String toString() {
	return "SwitchSocket{" + "houseCode=" + houseCode + ", receiverCode=" + receiverCode + ", switchTo=" + switchTo + '}';
    }

}
