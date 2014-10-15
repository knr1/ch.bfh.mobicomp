/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.message;

import ch.quantasy.messagebus.message.implementation.AnIntent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ATinkerforgeDeviceIntent extends AnIntent implements TinkerforgeDeviceIntent {

    public ATinkerforgeDeviceIntent(TinkerforgeDeviceContent deviceContent, Agent intentSender) {
	super(intentSender);
	super.addContents(deviceContent);

    }

    public ATinkerforgeDeviceIntent(TinkerforgeDeviceContent deviceContent, Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
	super.addContents(deviceContent);
    }

    @Override
    public TinkerforgeDeviceContent getDeviceContent() {
	return (TinkerforgeDeviceContent) super.getContentByID(TinkerforgeDeviceContent.class);
    }

}
