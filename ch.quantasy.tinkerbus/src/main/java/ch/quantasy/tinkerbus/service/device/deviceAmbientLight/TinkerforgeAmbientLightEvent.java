/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceAmbientLight;

import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerbus.service.device.core.TinkerforgeDeviceEvent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeAmbientLightEvent extends TinkerforgeDeviceEvent<TinkerforgeAmbientLightSetting> {

    private Boolean illuminanceReached;
    private Boolean analogValueReached;
    private Integer illuminanceValue;
    private Integer analogValue;
    private Integer debouncePeriod;

    public TinkerforgeAmbientLightEvent(Service eventSender) {
	super(eventSender);
    }

    public Boolean isIlluminanceReached() {
	return illuminanceReached;
    }

    public void setIlluminanceReached(Boolean illuminanceReached) {
	this.illuminanceReached = illuminanceReached;
    }

    public Boolean isAnalogValueReached() {
	return analogValueReached;
    }

    public void setAnalogValueReached(Boolean analogValueReached) {
	this.analogValueReached = analogValueReached;
    }

    public Integer getIlluminanceValue() {
	return illuminanceValue;
    }

    public void setIlluminanceValue(Integer illuminanceValue) {
	this.illuminanceValue = illuminanceValue;
    }

    public Integer getAnalogValue() {
	return analogValue;
    }

    public void setAnalogValue(Integer analogValue) {
	this.analogValue = analogValue;
    }

    public void setDebouncePeriod(Integer debouncePeriod) {
	this.debouncePeriod = debouncePeriod;
    }

    public Integer getDebouncePeriod() {
	return debouncePeriod;
    }

    @Override
    public String toString() {
	return super.toString() + " TinkerforgeAmbientLightEvent{" + "illuminanceReached=" + illuminanceReached + ", analogValueReached=" + analogValueReached + ", illuminanceValue=" + illuminanceValue + ", analogValue=" + analogValue + ", debouncePeriod=" + debouncePeriod + '}';
    }

}
