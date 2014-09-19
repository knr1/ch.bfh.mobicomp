/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.core;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.messagebus.worker.definition.Service;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeDeviceEvent<S extends TinkerforgeDeviceSetting> extends DefaultEvent {

    private S deviceSetting;

    public TinkerforgeDeviceEvent(Service eventSender) {
	super(eventSender);
    }

    public void setDeviceSetting(S deviceSetting) {
	this.deviceSetting = deviceSetting;
    }

    public S getDeviceSetting() {
	return deviceSetting;
    }
}
