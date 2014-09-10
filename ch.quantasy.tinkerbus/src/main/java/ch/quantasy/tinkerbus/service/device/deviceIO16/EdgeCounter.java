/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceIO16;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class EdgeCounter {

    public enum Pin {

	Pin0, Pin1;
    }

    public enum EdgeType {

	Falling, Rising;
    }

    private Pin pin;
    private EdgeType edgeType;
    private Short debounce;

    private Boolean resetOnRead;

    public Short getDebounce() {
	return debounce;
    }

    public void setDebounce(Short debounce) {
	this.debounce = debounce;
    }

    public Boolean isResetOnRead() {
	return resetOnRead;
    }

    public void setResetOnRead(Boolean resetOnRead) {
	this.resetOnRead = resetOnRead;
    }

    @Override
    public String toString() {
	return "EdgeCounter{" + "pin=" + pin + ", edgeType=" + edgeType + ", debounce=" + debounce + ", resetOnRead=" + resetOnRead + '}';
    }

}
