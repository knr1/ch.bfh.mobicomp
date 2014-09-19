/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceAmbientLight;

import ch.quantasy.tinkerbus.service.device.core.TinkerforgeDeviceSetting;
import ch.quantasy.tinkerbus.service.device.threshold.CallbackThreshold;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeAmbientLightSetting extends TinkerforgeDeviceSetting<TinkerforgeAmbientLightSetting> {

    private Long debouncePeriod;
    private Long analogValueCallbackPeriod;
    private CallbackThreshold analogThreshold;
    private Long illuminanceValueCallbackPeriod;
    private CallbackThreshold illuminanceThreshold;

    public Long getDebouncePeriod() {
	return debouncePeriod;
    }

    public void setDebouncePeriod(Long debouncePeriod) {
	this.debouncePeriod = debouncePeriod;
    }

    public Long getAnalogValueCallbackPeriod() {
	return analogValueCallbackPeriod;
    }

    public void setAnalogValueCallbackPeriod(Long analogValueCallbackPeriod) {
	this.analogValueCallbackPeriod = analogValueCallbackPeriod;
    }

    public CallbackThreshold getAnalogThreshold() {
	return analogThreshold;
    }

    public void setAnalogThreshold(CallbackThreshold analogThreshold) {
	this.analogThreshold = analogThreshold;
    }

    public Long getIlluminanceValueCallbackPeriod() {
	return illuminanceValueCallbackPeriod;
    }

    public void setIlluminanceValueCallbackPeriod(Long illuminanceValueCallbackPeriod) {
	this.illuminanceValueCallbackPeriod = illuminanceValueCallbackPeriod;
    }

    public CallbackThreshold getIlluminanceThreshold() {
	return illuminanceThreshold;
    }

    public void setIlluminanceThreshold(CallbackThreshold illuminanceThreshold) {
	this.illuminanceThreshold = illuminanceThreshold;
    }

    @Override
    public String toString() {
	return "CurrentSettings{" + "debouncePeriod=" + debouncePeriod + ", analogValueCallbackPeriod=" + analogValueCallbackPeriod + ", analogThreshold=" + analogThreshold + ", illuminanceValueCallbackPeriod=" + illuminanceValueCallbackPeriod + ", illuminanceThreshold=" + illuminanceThreshold + '}';
    }

}
