/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.agent.device.deviceAmbientLight;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.service.device.deviceAmbientLight.TinkerforgeAmbientLightEvent;
import ch.quantasy.tinkerbus.service.device.deviceAmbientLight.TinkerforgeAmbientLightIntent;
import ch.quantasy.tinkerbus.service.device.deviceAmbientLight.TinkerforgeAmbientLightService;
import ch.quantasy.tinkerbus.service.device.threshold.ThresholdOption;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeAmbientLightAgent extends ATinkerforgeAgent {

    public static final String ID = "TinkerforgeAmbientLightAgent";

    @Override
    protected void handleTinkerMessage(DefaultEvent message) {

	this.handleEvent(TinkerforgeAmbientLightService.getTinkerforgeAmbientLightEvent(message));

    }

    private void handleEvent(TinkerforgeAmbientLightEvent event) {

	if (event == null) {
	    return;
	}
	if (event.isDiscovered() != null && event.isDiscovered()) {
	    System.out.println("AmbientLight discovered");
	    TinkerforgeAmbientLightIntent intent = TinkerforgeAmbientLightService.createIntent(this);
	    intent.getDeviceSetting().setAnalogValueCallbackPeriod(5000L);
	    intent.getDeviceSetting().setIlluminanceValueCallbackPeriod(5000L);
	    intent.getDeviceSetting().setIlluminanceThreshold(ThresholdOption.createHigherThanCallbackThreshold((short) 50));
	    intent.getDeviceSetting().setDebouncePeriod(2000L);
	    intent.addReceiverIDs(event.getSenderID());
	    System.out.println("Intent: " + intent);

	    publish(intent);
	} else {
	    Integer illuminence = event.getIlluminanceValue();
	    if (illuminence != null) {
		//System.out.println("Interpretation Illuminence:" + illuminence);
	    }
	    Integer analogValue = event.getAnalogValue();
	    if (analogValue != null) {
		System.out.println(analogValue);
	    }
	    if (event.isIlluminanceReached()) {
		System.out.println("IlluminenceReached: " + event.getIlluminanceValue());
	    }
	}

    }

    @Override
    public String getID() {
	return ID;
    }

}
