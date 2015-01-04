/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome.agent;

import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.smarthome.remoteSwitch.content.Floor;
import ch.quantasy.smarthome.remoteSwitch.content.RemoteSwitchContent;
import ch.quantasy.smarthome.remoteSwitch.message.RemoteSwitch;
import ch.quantasy.smarthome.remoteSwitch.message.RemoteSwitchEvent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;
import ch.quantasy.tinkerbus.service.device.deviceRemoteSwitch.TinkerforgeRemoteSwitchService;
import ch.quantasy.tinkerbus.service.device.deviceRemoteSwitch.content.DimSocketB;
import ch.quantasy.tinkerbus.service.device.deviceRemoteSwitch.content.DimSocketBContent;
import ch.quantasy.tinkerbus.service.device.deviceRemoteSwitch.content.RepeatsContent;
import ch.quantasy.tinkerbus.service.device.deviceRemoteSwitch.content.Socket;
import ch.quantasy.tinkerbus.service.device.deviceRemoteSwitch.content.SwitchSocket;
import ch.quantasy.tinkerbus.service.device.deviceRemoteSwitch.content.SwitchSocketContent;
import ch.quantasy.tinkerbus.service.device.deviceRemoteSwitch.message.TinkerforgeRemoteSwitchEvent;
import ch.quantasy.tinkerbus.service.device.message.TinkerforgeDeviceEvent;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RemoteSwitchAgent extends ATinkerforgeAgent {

    public static final String ID = "TinkerforgeRemoteSwitchAgent";
    public static final String OBERGESCHOSS = "jKE6xithe";
    public static final String ERDGESCHOSS = "jKQ63mL8c";
    private Set<String> remoteSwitchServices;

    public RemoteSwitchAgent() {
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

    private void handleSmarthomeEvent(final RemoteSwitchEvent message) {
	RemoteSwitchContent content = (RemoteSwitchContent) (message.getContentByID(RemoteSwitchContent.class));
	if (content == null) {
	    return;
	}
	RemoteSwitch remoteSwitch = content.getValue();

	TinkerforgeDeviceContent deviceContent = new TinkerforgeDeviceContent();
	deviceContent.updateSetting(new RepeatsContent((short) 3));
	if (remoteSwitch.getDimValue() >= 0) {
	    deviceContent.updateSetting(new DimSocketBContent(new DimSocketB((long) remoteSwitch.getAddress(), (short) remoteSwitch.getUnit(), (short) remoteSwitch.getDimValue())));
	} else {
	    deviceContent.updateSetting(new SwitchSocketContent(new SwitchSocket(Socket.B, remoteSwitch.getAddress(), (short) remoteSwitch.getUnit(), remoteSwitch.isSwitchOn())));
	}

	if (remoteSwitch.getFloor() == Floor.EG && this.remoteSwitchServices.contains(ERDGESCHOSS)) {
	    this.publish(TinkerforgeRemoteSwitchService.createIntent(deviceContent, this, ERDGESCHOSS));
	}
	if (remoteSwitch.getFloor() == Floor.OG && this.remoteSwitchServices.contains(OBERGESCHOSS)) {
	    this.publish(TinkerforgeRemoteSwitchService.createIntent(deviceContent, this, OBERGESCHOSS));
	}

    }

    @Override
    protected void handleEvent(Event message
    ) {
	if (message instanceof TinkerforgeDeviceEvent) {
	    handleTinkerforgeEvent((TinkerforgeDeviceEvent) message);
	}
	if (message instanceof RemoteSwitchEvent) {
	    handleSmarthomeEvent((RemoteSwitchEvent) message);
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
