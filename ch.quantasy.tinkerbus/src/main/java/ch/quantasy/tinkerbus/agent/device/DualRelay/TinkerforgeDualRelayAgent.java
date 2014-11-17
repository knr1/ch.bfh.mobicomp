/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.agent.device.DualRelay;

import ch.quantasy.messagebus.message.definition.Content;
import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;
import ch.quantasy.tinkerbus.service.device.dualRelay.TinkerforgeDualRelayService;
import ch.quantasy.tinkerbus.service.device.dualRelay.content.Monoflop;
import ch.quantasy.tinkerbus.service.device.dualRelay.content.MonoflopContent;
import ch.quantasy.tinkerbus.service.device.dualRelay.content.MonoflopDoneContent;
import ch.quantasy.tinkerbus.service.device.dualRelay.content.SelectedState;
import ch.quantasy.tinkerbus.service.device.dualRelay.content.SelectedStateContent;
import ch.quantasy.tinkerbus.service.device.dualRelay.message.TinkerforgeDualRelayEvent;
import ch.quantasy.tinkerbus.service.device.message.TinkerforgeDeviceEvent;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeDualRelayAgent extends ATinkerforgeAgent {

    private TinkerforgeDeviceContent content;

    protected void handleTinkerforgeEvent(TinkerforgeDeviceEvent event) {
	if (event instanceof TinkerforgeDualRelayEvent) {
	    System.out.println("TinkerforgeDualRelayAgent: Event:" + event);
	    TinkerforgeDeviceContent content = event.getDeviceContent();

	    //Achtung: Same Object! Clone it!
	    if (this.content == null) {
		this.content = content;
		Content newContent = new MonoflopContent(new Monoflop((short) 1, true, 5000));
		Content oldContent = this.content.updateSetting(newContent);
		newContent = new SelectedStateContent(new SelectedState((short) 2, true));
		oldContent = this.content.updateSetting(newContent);

		//if (!newContent.equals(oldContent)) {
		publish(TinkerforgeDualRelayService.createIntent(content, this));
		System.out.println("Published");
		//}
	    } else {
		System.out.println("Old-Set: " + content.getEmissions());
		System.out.println("New-Set: " + content.getEmissions());

		Set<Content> emissionContentChangeSet = content.updateEmissions(content);

		System.out.println("EmissionChange:" + emissionContentChangeSet);
	    }
	    System.out.println("-->" + content.getEmissionContentByID(MonoflopDoneContent.class));
	}
    }

    @Override
    protected void handleEvent(Event message) {
	System.out.println("ATinkerforgeAgent: Event: " + message);
	if (message instanceof TinkerforgeDeviceEvent) {
	    handleTinkerforgeEvent((TinkerforgeDeviceEvent) message);
	}
    }

    @Override
    public String getID() {
	return "DualRelaisAgent";
    }

}
