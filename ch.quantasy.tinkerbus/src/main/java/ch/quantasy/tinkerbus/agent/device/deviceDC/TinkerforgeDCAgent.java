/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.agent.device.deviceDC;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.service.device.deviceDC.message.TinkerforgeDCEvent;
import ch.quantasy.tinkerbus.service.device.deviceDC.message.TinkerforgeDCIntent;
import ch.quantasy.tinkerbus.service.device.deviceDC.TinkerforgeDCService;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeDCAgent extends ATinkerforgeAgent {

    public static final String ID = "TinkerforgeDCAgent";

    @Override
    protected void handleTinkerMessage(DefaultEvent message) {

	this.handleEvent(TinkerforgeDCService.getTinkerforgeDCEvent(message));

    }

    private void handleEvent(TinkerforgeDCEvent event) {
	if (event == null) {
	    return;
	}
	if (event.isDiscovered() != null && event.isDiscovered()) {
	    System.out.println("DC discovered xxx");
	    TinkerforgeDCIntent intent = TinkerforgeDCService.createIntent(this);
	    intent.getDeviceSetting().setEnabled(true);
	    intent.getDeviceSetting().setMinimumVoltage(6000);
	    intent.getDeviceSetting().setVelocity((short) 15000);
	    intent.getDeviceSetting().setAcceleration(1600);
	    intent.getDeviceSetting().setPWMFrequency(20000);
	    intent.addReceiverIDs(event.getSenderID());
	    System.out.println("Intent: " + intent);

	    publish(intent);
	} else {
	    System.out.println("DC-Event: " + event);
	}
    }

    @Override
    public String getID() {
	return ID;
    }

}
