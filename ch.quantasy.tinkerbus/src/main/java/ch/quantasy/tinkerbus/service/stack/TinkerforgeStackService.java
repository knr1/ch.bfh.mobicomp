/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.stack;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeService;
import ch.quantasy.tinkerbus.service.device.core.TinkerforgeDeviceService;
import ch.quantasy.tinkerbus.service.device.core.TinkerforgeDeviceServiceFactory;
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

    public final static String STACK_ADDRESS = "tinkerforge.stack.address";
    public final static String CONNECTED = "tinkerforge.stack.connected";
    public final static String EXCEPTION = "tinkerforge.stack.exception";

    private final TinkerforgeStackManager stackManager;
    private final Map<String, TinkerforgeDeviceService> deviceServices;

    public TinkerforgeStackService(TinkerforgeStackManager manager) {
	this.deviceServices = new HashMap<>();
	this.stackManager = manager;
	this.stackManager.setStackService(this);
    }

    @Override
    protected void handleTinkerMessage(TinkerforgeStackIntent message) {
	if (message == null) {
	    return;
	}
	StackConnectionIntentState state = message.getStackIntentState();
	if (state == null) {
	    return;
	}
	if (state == StackConnectionIntentState.Connect) {
	    this.stackManager.connect();
	}
	if (state == StackConnectionIntentState.Disconnect) {
	    this.stackManager.disconnect();
	}
	if (state == StackConnectionIntentState.Status) {
	    TinkerforgeStackEvent event = createEvent();
	    if (this.stackManager.isConnected()) {
		event.setStackConnectionEventState(StackConnectionEventState.Connected);
	    } else {
		event.setStackConnectionEventState(StackConnectionEventState.Disconnected);
	    }
	    Exception exception = this.stackManager.getActualConnectionException();
	    System.out.println(exception);
	    if (exception != null) {
		event.setException(exception.toString());
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
	event.setStackConnectionEventState(StackConnectionEventState.Connected);
	event.setStackAddress(stackManager.getStackAddress());
	publish(event);
    }

    public void disconnected() {
	TinkerforgeStackEvent event = createEvent();
	event.setStackConnectionEventState(StackConnectionEventState.Disconnected);
	event.setStackAddress(stackManager.getStackAddress());
	publish(event);
    }

    public void deviceConnected(Device device) {
	Identity identity;
	try {
	    identity = device.getIdentity();
	    String deviceID = identity.uid + identity.connectedUid;
	    TinkerforgeDeviceService deviceService = deviceServices.get(deviceID);
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
	return new TinkerforgeStackEvent(this);
    }

    public static TinkerforgeStackEvent getTinkerforgeStackEvent(DefaultEvent event) {
	if (event instanceof TinkerforgeStackEvent) {
	    return (TinkerforgeStackEvent) event;
	} else {
	    return null;
	}
    }

    public static TinkerforgeStackIntent createIntent(Agent agent) {
	return new TinkerforgeStackIntent(agent);
    }

}
