/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceDualRelay;

import ch.quantasy.messagebus.message.definition.Content;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;
import ch.quantasy.tinkerbus.service.device.core.ATinkerforgeDeviceService;
import ch.quantasy.tinkerbus.service.device.deviceDualRelay.content.Monoflop;
import ch.quantasy.tinkerbus.service.device.deviceDualRelay.content.MonoflopContent;
import ch.quantasy.tinkerbus.service.device.deviceDualRelay.content.MonoflopDone;
import ch.quantasy.tinkerbus.service.device.deviceDualRelay.content.MonoflopDoneContent;
import ch.quantasy.tinkerbus.service.device.deviceDualRelay.content.SelectedState;
import ch.quantasy.tinkerbus.service.device.deviceDualRelay.content.SelectedStateContent;
import ch.quantasy.tinkerbus.service.device.deviceDualRelay.content.State;
import ch.quantasy.tinkerbus.service.device.deviceDualRelay.content.StateContent;
import ch.quantasy.tinkerbus.service.device.deviceDualRelay.message.TinkerforgeDualRelayEvent;
import ch.quantasy.tinkerbus.service.device.deviceDualRelay.message.TinkerforgeDualRelayIntent;
import ch.quantasy.tinkerbus.service.device.message.ATinkerforgeDeviceEvent;
import ch.quantasy.tinkerbus.service.device.message.ATinkerforgeDeviceIntent;
import com.tinkerforge.BrickletDualRelay;
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
public class TinkerforgeDualRelayService extends ATinkerforgeDeviceService<BrickletDualRelay, TinkerforgeDualRelayIntent, TinkerforgeDualRelayEvent> implements BrickletDualRelay.MonoflopDoneListener {

    public TinkerforgeDualRelayService(BrickletDualRelay device, String deviceID) {
	super(device, deviceID);
    }

    @Override
    protected void updateListeners(BrickletDualRelay device) {
	device.addMonoflopDoneListener(this);
    }

    @Override
    protected void updateSettingsOnDevice(BrickletDualRelay device, Map<Class, Content> contentMap) {
	if (contentMap == null || contentMap.isEmpty() || device == null) {
	    return;
	}
	try {
	    {
		MonoflopContent content = ((MonoflopContent) (contentMap.get(MonoflopContent.class)));
		if (content != null) {
		    Monoflop value = content.getValue();
		    if (value != null) {
			device.setMonoflop(value.relay, value.state, value.time);
		    }
		}
	    }
	    {
		SelectedStateContent content = ((SelectedStateContent) (contentMap.get(SelectedStateContent.class)));
		if (content != null) {
		    SelectedState value = content.getValue();
		    if (value != null) {
			device.setSelectedState(value.relay, value.state);
		    }
		}
	    }
	    {
		StateContent content = ((StateContent) (contentMap.get(StateContent.class)));
		if (content != null) {
		    State value = content.getValue();
		    if (value != null) {
			device.setState(value.relay1, value.relay2);
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
    public void handleTinkerforgeIntent(TinkerforgeDualRelayIntent message) {
	//Nothing special so far
    }

    @Override
    public TinkerforgeDualRelayEvent createEvent() {
	return new Event(getDeviceContent(), this);
    }

    @Override
    public void monoflopDone(short relay, boolean state) {
	getDeviceContent().updateEmission(new MonoflopDoneContent(new MonoflopDone(relay, state)));
	TinkerforgeDualRelayEvent event = createEvent();
	publish(event);
    }

    public static TinkerforgeDualRelayIntent createIntent(TinkerforgeDeviceContent content, Agent agent) {
	return new Intent(content, agent);
    }
}

class Event extends ATinkerforgeDeviceEvent implements TinkerforgeDualRelayEvent {

    public Event(TinkerforgeDeviceContent deviceContent, Service eventSender) {
	super(deviceContent, eventSender);
    }

}

class Intent extends ATinkerforgeDeviceIntent implements TinkerforgeDualRelayIntent {

    public Intent(TinkerforgeDeviceContent deviceContent, Agent intentSender) {
	super(deviceContent, intentSender);
    }

    public Intent(TinkerforgeDeviceContent deviceContent, Agent intentSender, String... intentReceivers) {
	super(deviceContent, intentSender, intentReceivers);
    }

}
