/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.agent.device.deviceAmbientLight;

import ch.quantasy.messagebus.message.definition.Content;
import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;
import ch.quantasy.tinkerbus.service.device.deviceAmbientLight.TinkerforgeAmbientLightService;
import ch.quantasy.tinkerbus.service.device.deviceAmbientLight.content.IlluminanceCallbackPeriodContent;
import ch.quantasy.tinkerbus.service.device.deviceAmbientLight.content.IlluminanceValueContent;
import ch.quantasy.tinkerbus.service.device.message.TinkerforgeDeviceEvent;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeAmbientLightAgent extends ATinkerforgeAgent {

    public static final String ID = "TinkerforgeAmbientLightAgent";

    /*private void handleEvent(TinkerforgeAmbientLightEvent event) {

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

     }*/
    @Override
    public String getID() {
	return ID;
    }

    private TinkerforgeDeviceContent ambientLightContent;

    private void handleTinkerforgeEvent(TinkerforgeDeviceEvent event) {
	System.out.println("TinkerforgeAmbientLightAgent: Event:" + event);
	TinkerforgeDeviceContent content = event.getDeviceContent();

	//Achtung: Same Object! Clone it!
	if (ambientLightContent == null) {
	    this.ambientLightContent = content;
	    Content newContent = new IlluminanceCallbackPeriodContent(500L);
	    Content oldContent = content.updateSetting(newContent);
	    if (!newContent.equals(oldContent)) {
		publish(TinkerforgeAmbientLightService.createIntent(content, this));
	    }
	} else {
	    System.out.println("Old-Set: " + ambientLightContent.getEmissions());
	    System.out.println("New-Set: " + content.getEmissions());

	    Set<Content> emissionContentChangeSet = ambientLightContent.updateEmissions(content);

	    System.out.println("EmissionChange:" + emissionContentChangeSet);
	}
	System.out.println("-->" + content.getEmissionContentByID(IlluminanceValueContent.class));
    }

    @Override
    protected void handleEvent(Event message) {
	if (message instanceof TinkerforgeDeviceEvent) {
	    handleTinkerforgeEvent((TinkerforgeDeviceEvent) message);
	}
    }

}
