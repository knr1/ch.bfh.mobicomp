/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceLED;

import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerbus.service.device.core.TinkerforgeDeviceEvent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeLEDEvent extends TinkerforgeDeviceEvent<TinkerforgeLEDSetting> {

    private Boolean laging;

    public TinkerforgeLEDEvent(Service eventSender) {
	super(eventSender);
    }

    public Boolean isLaging() {
	return laging != null && laging;
    }

    public void setLaging(Boolean laging) {
	this.laging = laging;
    }

    @Override
    public String toString() {
	return super.toString() + " TinkerforgeLEDEvent{" + "laging=" + laging + '}';
    }

}
