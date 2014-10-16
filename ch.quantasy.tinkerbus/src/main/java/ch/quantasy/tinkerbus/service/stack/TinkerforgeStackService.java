/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.stack;

import ch.quantasy.messagebus.message.implementation.AnEvent;
import ch.quantasy.messagebus.message.implementation.AnIntent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerbus.bus.ATinkerforgeService;
import ch.quantasy.tinkerbus.service.content.ThrowableContent;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeStackAddressContent;
import ch.quantasy.tinkerbus.service.device.core.ATinkerforgeDeviceService;
import ch.quantasy.tinkerbus.service.device.core.TinkerforgeDeviceServiceFactory;
import ch.quantasy.tinkerbus.service.stack.content.StackConnectionStateContent;
import com.tinkerforge.Device;
import com.tinkerforge.Device.Identity;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeStackService extends ATinkerforgeService<TinkerforgeStackIntent, TinkerforgeStackEvent> {

    private final TinkerforgeStackManager stackManager;
    private final Map<String, ATinkerforgeDeviceService> deviceServices;

    public TinkerforgeStackService(TinkerforgeStackManager manager) {
	this.deviceServices = new HashMap<>();
	this.stackManager = manager;
	this.stackManager.setStackService(this);
    }

    @Override
    protected void handleIntent(TinkerforgeStackIntent message) {
	if (message == null) {
	    return;
	}
	StackConnectionStateContent state = (StackConnectionStateContent) message.getContentByID(StackConnectionStateContent.class);
	if (state == null) {
	    return;
	}
	if (state.getValue() == StackConnectionState.Connect) {
	    this.stackManager.connect();
	}
	if (state.getValue() == StackConnectionState.Disconnect) {
	    this.stackManager.disconnect();
	}
	if (state.getValue() == StackConnectionState.Status) {
	    TinkerforgeStackEvent event = createEvent();
	    if (this.stackManager.isConnected()) {
		event.addContents(new StackConnectionStateContent(StackConnectionState.Connected));
	    } else {
		event.addContents(new StackConnectionStateContent(StackConnectionState.Disconnected));
	    }
	    Exception exception = this.stackManager.getActualConnectionException();
	    System.out.println(exception);
	    if (exception != null) {
		event.addContents(new ThrowableContent(exception));
	    }
	    publish(event);
	}
    }

    @Override
    public String getID() {
	return stackManager.getStackAddress().toString();
    }

    public void connected() {
	TinkerforgeStackEvent event = createEvent();
	event.addContents(new StackConnectionStateContent(StackConnectionState.Connected));
	event.addContents(new TinkerforgeStackAddressContent(stackManager.getStackAddress()));
	publish(event);
    }

    public void disconnected() {
	TinkerforgeStackEvent event = createEvent();
	event.addContents(new StackConnectionStateContent(StackConnectionState.Disconnected));
	event.addContents(new TinkerforgeStackAddressContent(stackManager.getStackAddress()));
	publish(event);
    }

    public void deviceConnected(Device device) {
	Identity identity;
	try {
	    identity = device.getIdentity();
	    String deviceID = identity.uid + identity.connectedUid;
	    ATinkerforgeDeviceService deviceService = deviceServices.get(deviceID);
	    if (deviceService == null) {
		deviceService = TinkerforgeDeviceServiceFactory.getDevice(device, deviceID);
		deviceServices.put(deviceID, deviceService);
	    } else {
		deviceService.updateDevice(device);
	    }

	} catch (TimeoutException ex) {
	    Logger.getLogger(TinkerforgeStackService.class.getName()).log(Level.SEVERE, null, ex);
	} catch (NotConnectedException ex) {
	    Logger.getLogger(TinkerforgeStackService.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public void deviceReConnected(Device device) {
	deviceConnected(device);
    }

    public void deviceDisconnected(Device device) {
	//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TinkerforgeStackEvent createEvent() {
	return new Event(this);
    }

    public static TinkerforgeStackIntent connect(Agent intentSender, String... intentReceivers) {
	TinkerforgeStackIntent intent = new Intent(intentSender, intentReceivers);
	intent.addContents(new StackConnectionStateContent(StackConnectionState.Connect));
	return intent;
    }

    public static TinkerforgeStackIntent disconnect(Agent intentSender, String... intentReceivers) {
	TinkerforgeStackIntent intent = new Intent(intentSender, intentReceivers);
	intent.addContents(new StackConnectionStateContent(StackConnectionState.Disconnect));
	return intent;
    }

    public static TinkerforgeStackIntent connectionStatus(Agent intentSender, String... intentReceivers) {
	TinkerforgeStackIntent intent = new Intent(intentSender, intentReceivers);
	intent.addContents(new StackConnectionStateContent(StackConnectionState.Status));
	return intent;
    }
}

class Intent extends AnIntent implements TinkerforgeStackIntent {

    public Intent(Agent intentSender) {
	super(intentSender);
    }

    public Intent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
    }

}

class Event extends AnEvent implements TinkerforgeStackEvent {

    public Event(Service eventSender) {
	super(eventSender);
    }

}
