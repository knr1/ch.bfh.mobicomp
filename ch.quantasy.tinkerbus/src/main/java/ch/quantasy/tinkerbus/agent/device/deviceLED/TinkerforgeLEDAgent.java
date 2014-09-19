/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.agent.device.deviceLED;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.service.device.deviceLED.TinkerforgeLEDEvent;
import ch.quantasy.tinkerbus.service.device.deviceLED.TinkerforgeLEDIntent;
import ch.quantasy.tinkerbus.service.device.deviceLED.TinkerforgeLEDService;
import ch.quantasy.tinkerbus.service.device.deviceLED.TinkerforgeLEDSetting;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeLEDAgent extends ATinkerforgeAgent {

    public static final String ID = "TinkerforgeLEDAgent";

    @Override
    protected void handleTinkerMessage(DefaultEvent message) {

	this.handleEvent(TinkerforgeLEDService.getTinkerforgeLEDEvent(message));
    }

    private short power;
    private String ledDeviceID;
    private short[][] frame;

    private void handleEvent(TinkerforgeLEDEvent event) {
	if (event == null) {
	    return;
	}
	//System.out.println("---> LED");
	if (event.isDiscovered() != null && event.isDiscovered()) {
	    System.out.println("LED-Stripe discovered");
	    this.ledDeviceID = event.getSenderID();
	    TinkerforgeLEDIntent intent = TinkerforgeLEDService.createIntent(this);
	    intent.getDeviceSetting().setChipType(TinkerforgeLEDSetting.DEFAULT_CHIP_TYPE);
	    intent.getDeviceSetting().setClockFrequencyOfICsInHz(2000000);
	    intent.getDeviceSetting().setFrameDurationInMilliseconds(20);
	    intent.getDeviceSetting().setNumberOfLEDs(50);
	    intent.addReceiverIDs(event.getSenderID());
	    System.out.println(intent);
	    publish(intent);
	    intent = TinkerforgeLEDService.createIntent(this);
	    intent.setRequestCurrentSetting(true);
	    intent.addReceiverIDs(event.getSenderID());
	    publish(intent);

	    frame = TinkerforgeLEDService.getFreshRGBLEDs(50);
	} else {
	    TinkerforgeLEDIntent intent = TinkerforgeLEDService.createIntent(this);
	    //intent.addReceiverIDs(ledDeviceID);
	    power++;
	    power %= 128;
	    for (int i = 0; i < frame.length; i++) {
		for (int j = 0; j < frame[i].length; j++) {
		    frame[i][j] = (short) (power * Math.random());
		}
	    }
	    intent.setFrame(frame);
	    publish(intent);
	}
	if (event.isLaging() != null && event.isLaging()) {
	    System.out.println("lagging");
	}

    }

    @Override
    public String getID() {
	return ID;
    }

}
