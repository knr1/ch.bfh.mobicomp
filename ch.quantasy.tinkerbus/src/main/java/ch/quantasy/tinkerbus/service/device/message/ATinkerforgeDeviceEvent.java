/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.message;

import ch.quantasy.messagebus.message.implementation.AnEvent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ATinkerforgeDeviceEvent extends AnEvent implements TinkerforgeDeviceEvent {

    public ATinkerforgeDeviceEvent(TinkerforgeDeviceContent deviceContent, Service eventSender) {
	super(eventSender);
	super.addContents(deviceContent);
    }

    @Override
    public TinkerforgeDeviceContent getDeviceContent() {
	return (TinkerforgeDeviceContent) super.getContentByID(TinkerforgeDeviceContent.class);
    }

}
