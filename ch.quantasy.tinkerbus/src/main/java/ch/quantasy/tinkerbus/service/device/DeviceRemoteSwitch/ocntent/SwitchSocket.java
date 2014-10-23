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
public class SwitchSocket {

    public Socket socket;
    public final short houseCode;
    public final short receiverCode;
    public final short switchTo;

    public SwitchSocket(Socket socket, short houseCode, short receiverCode, short switchTo) {
	this.socket = socket;
	this.houseCode = houseCode;
	this.receiverCode = receiverCode;
	this.switchTo = switchTo;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 31 * hash + this.houseCode;
	hash = 31 * hash + this.receiverCode;
	hash = 31 * hash + this.switchTo;
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
	if (this.houseCode != other.houseCode) {
	    return false;
	}
	if (this.receiverCode != other.receiverCode) {
	    return false;
	}
	if (this.switchTo != other.switchTo) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "SwitchSocketA{" + "houseCode=" + houseCode + ", receiverCode=" + receiverCode + ", switchTo=" + switchTo + '}';
    }

}
