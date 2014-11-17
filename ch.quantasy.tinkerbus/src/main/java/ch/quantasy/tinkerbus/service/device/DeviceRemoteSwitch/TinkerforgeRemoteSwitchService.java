/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.DeviceRemoteSwitch;

import ch.quantasy.messagebus.message.definition.Content;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerbus.service.device.DeviceRemoteSwitch.message.TinkerforgeRemoteSwitchEvent;
import ch.quantasy.tinkerbus.service.device.DeviceRemoteSwitch.message.TinkerforgeRemoteSwitchIntent;
import ch.quantasy.tinkerbus.service.device.DeviceRemoteSwitch.ocntent.DimSocketB;
import ch.quantasy.tinkerbus.service.device.DeviceRemoteSwitch.ocntent.DimSocketBContent;
import ch.quantasy.tinkerbus.service.device.DeviceRemoteSwitch.ocntent.RepeatsContent;
import ch.quantasy.tinkerbus.service.device.DeviceRemoteSwitch.ocntent.SwitchSocket;
import ch.quantasy.tinkerbus.service.device.DeviceRemoteSwitch.ocntent.SwitchSocketContent;
import ch.quantasy.tinkerbus.service.device.DeviceRemoteSwitch.ocntent.SwitchingDoneContent;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;
import ch.quantasy.tinkerbus.service.device.core.ATinkerforgeDeviceService;
import ch.quantasy.tinkerbus.service.device.message.ATinkerforgeDeviceEvent;
import ch.quantasy.tinkerbus.service.device.message.ATinkerforgeDeviceIntent;
import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reto
 *
 */
public class TinkerforgeRemoteSwitchService extends ATinkerforgeDeviceService<BrickletRemoteSwitch, TinkerforgeRemoteSwitchIntent, TinkerforgeRemoteSwitchEvent> implements BrickletRemoteSwitch.SwitchingDoneListener {

    private boolean isSwitching;

    public TinkerforgeRemoteSwitchService(BrickletRemoteSwitch device, String deviceID) {
	super(device, deviceID);
    }

    @Override
    protected void updateListeners(BrickletRemoteSwitch device) {
	device.addSwitchingDoneListener(this);
    }

    @Override
    protected void updateSettingsOnDevice(BrickletRemoteSwitch device, Map<Class, Content> contentMap) {
	if (contentMap == null || contentMap.isEmpty() || device == null) {
	    return;
	}
	try {
	    {
		DimSocketBContent content = ((DimSocketBContent) (contentMap.get(DimSocketBContent.class)));
		if (content != null) {
		    DimSocketB value = content.getValue();
		    if (value != null) {
			synchronized (this) {
			    while (this.isSwitching) {
				try {
				    wait(1000);
				} catch (InterruptedException ex) {
				    Logger.getLogger(TinkerforgeRemoteSwitchService.class.getName()).log(Level.SEVERE, null, ex);
				}
			    }
			    device.dimSocketB(value.address, value.unit, value.dimValue);
			    this.isSwitching = true;
			}
		    }
		}
	    }

	    {
		RepeatsContent content = ((RepeatsContent) (contentMap.get(RepeatsContent.class)));
		if (content != null) {
		    Short value = content.getValue();
		    if (value != null) {
			device.setRepeats(value);
		    }
		}
	    }

	    {
		SwitchSocketContent content = ((SwitchSocketContent) (contentMap.get(SwitchSocketContent.class)));
		if (content != null) {
		    SwitchSocket value = content.getValue();
		    if (value != null) {
			synchronized (this) {
			    while (this.isSwitching) {
				try {
				    wait(1000);
				} catch (InterruptedException ex) {
				    Logger.getLogger(TinkerforgeRemoteSwitchService.class.getName()).log(Level.SEVERE, null, ex);
				}
			    }

			    switch (value.socket) {
				case A:
				    device.switchSocketA(value.houseCode, value.receiverCode, value.switchTo);
				    this.isSwitching = true;
				    break;
				case B:
				    device.switchSocketB(value.houseCode, value.receiverCode, value.switchTo);
				    this.isSwitching = true;
				    break;
				case C:
				    device.switchSocketC((char) value.houseCode, value.receiverCode, value.switchTo);
				    this.isSwitching = true;
				    break;
				default:
				    break;
			    }
			}
		    }
		}
	    }
	} catch (TimeoutException ex) {
	    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
	} catch (NotConnectedException ex) {
	    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
	}

    }

    @Override
    public void handleTinkerforgeIntent(TinkerforgeRemoteSwitchIntent message) {
	//Nothing special so far
    }

    @Override
    public TinkerforgeRemoteSwitchEvent createEvent() {
	return new Event(getDeviceContent(), this);
    }

    public static TinkerforgeRemoteSwitchIntent createIntent(TinkerforgeDeviceContent content, Agent agent) {
	return new Intent(content, agent);
    }

    @Override
    public void switchingDone() {
	synchronized (this) {
	    notifyAll();
	    isSwitching = false;
	}
	getDeviceContent().updateEmission(new SwitchingDoneContent(true));
	TinkerforgeRemoteSwitchEvent event = createEvent();
	publish(event);

    }
}

class Event extends ATinkerforgeDeviceEvent implements TinkerforgeRemoteSwitchEvent {

    public Event(TinkerforgeDeviceContent deviceContent, Service eventSender) {
	super(deviceContent, eventSender);
    }

}

class Intent extends ATinkerforgeDeviceIntent implements TinkerforgeRemoteSwitchIntent {

    public Intent(TinkerforgeDeviceContent deviceContent, Agent intentSender) {
	super(deviceContent, intentSender);
    }

    public Intent(TinkerforgeDeviceContent deviceContent, Agent intentSender, String... intentReceivers) {
	super(deviceContent, intentSender, intentReceivers);
    }

}
