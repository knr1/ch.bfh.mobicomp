/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.agent.device.deviceRemoteSwitch;

import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;
import ch.quantasy.tinkerbus.service.device.deviceRemoteSwitch.message.TinkerforgeRemoteSwitchEvent;
import ch.quantasy.tinkerbus.service.device.message.TinkerforgeDeviceEvent;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeRemoteSwitchAgent extends ATinkerforgeAgent {

    public static final String ID = "TinkerforgeRemoteSwitchAgent";
    public static final String OBERGESCHOSS = "jKE6xithe";
    public static final String ERDGESCHOSS = "jKQ63mL8c";
    private Set<String> remoteSwitchServices;

    public TinkerforgeRemoteSwitchAgent() {
	this.remoteSwitchServices = new HashSet<>();
    }

    private void handleTinkerforgeEvent(final TinkerforgeRemoteSwitchEvent event) {
	TinkerforgeDeviceContent content = event.getDeviceContent();
	if (!this.remoteSwitchServices.contains(event.getSenderID())) {
	    this.remoteSwitchServices.add(event.getSenderID());
	}
	System.out.println("Event: " + event);
	System.out.println("Content: " + content);

    }

    private void handleTinkerforgeEvent(final TinkerforgeDeviceEvent event) {
	TinkerforgeDeviceContent content = event.getDeviceContent();
	if (event instanceof TinkerforgeRemoteSwitchEvent) {
	    handleTinkerforgeEvent((TinkerforgeRemoteSwitchEvent) event);
	}
    }

    @Override
    protected void handleEvent(Event message) {
	if (message instanceof TinkerforgeDeviceEvent) {
	    handleTinkerforgeEvent((TinkerforgeDeviceEvent) message);
	}
    }

    @Override
    public String getID() {
	return ID;
    }

    @Override
    protected void finalize() throws Throwable {
	super.finalize();
	System.out.println("Shoot I am going down.");
    }

}
