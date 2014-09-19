/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.core;

import ch.quantasy.messagebus.message.DefaultIntent;
import ch.quantasy.messagebus.worker.definition.Agent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class TinkerforgeDeviceIntent<S extends TinkerforgeDeviceSetting> extends DefaultIntent {

    private S deviceSetting;
    private Boolean requestCurrentSetting;

    public TinkerforgeDeviceIntent(Agent intentSender) {
	super(intentSender);
	deviceSetting = createDeviceSetting();
    }

    public TinkerforgeDeviceIntent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
    }

    public Boolean isRequestCurrentSetting() {
	return requestCurrentSetting != null && requestCurrentSetting;
    }

    public void setRequestCurrentSetting(Boolean requestCurrentSetting) {
	this.requestCurrentSetting = requestCurrentSetting;
    }

    public void setDeviceSetting(S deviceSetting) {
	this.deviceSetting = deviceSetting;
    }

    public S getDeviceSetting() {
	return deviceSetting;
    }

    @Override
    public String toString() {
	return "TinkerforgeDeviceIntent{" + "deviceSetting=" + deviceSetting + '}';
    }

    protected abstract S createDeviceSetting();

}
