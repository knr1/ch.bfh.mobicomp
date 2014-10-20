/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceIO16;

import ch.quantasy.messagebus.message.definition.Content;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;
import ch.quantasy.tinkerbus.service.device.core.ATinkerforgeDeviceService;
import ch.quantasy.tinkerbus.service.device.deviceAmbientLight.TinkerforgeAmbientLightService;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.DebouncePeriodContent;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.EdgeCountConfig;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.EdgeCountConfigContent;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.Interrupt;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.InterruptContent;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.MonoflopDone;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.MonoflopDoneContent;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.Port;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.PortConfiguration;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.PortConfigurationContent;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.PortContent;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.PortInterrupt;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.PortInterruptContent;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.PortMonoflop;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.PortMonoflopContent;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.SelectedValues;
import ch.quantasy.tinkerbus.service.device.deviceIO16.content.SelectedValuesContent;
import ch.quantasy.tinkerbus.service.device.deviceIO16.message.TinkerforgeIO16Event;
import ch.quantasy.tinkerbus.service.device.deviceIO16.message.TinkerforgeIO16Intent;
import ch.quantasy.tinkerbus.service.device.message.ATinkerforgeDeviceEvent;
import ch.quantasy.tinkerbus.service.device.message.ATinkerforgeDeviceIntent;
import com.tinkerforge.BrickletIO16;
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
public class TinkerforgeIO16Service extends ATinkerforgeDeviceService<BrickletIO16, TinkerforgeIO16Intent, TinkerforgeIO16Event> implements BrickletIO16.InterruptListener, BrickletIO16.MonoflopDoneListener {

    public TinkerforgeIO16Service(BrickletIO16 device, String deviceID) {
	super(device, deviceID);
    }

    @Override
    protected void updateListeners(BrickletIO16 device) {
	device.addInterruptListener(this);
	device.addMonoflopDoneListener(this);
    }

    @Override
    protected void updateSettingsOnDevice(BrickletIO16 device, Map<Class, Content> contentMap) {
	if (contentMap == null || contentMap.isEmpty() || device == null) {
	    return;
	}
	try {
	    {
		DebouncePeriodContent content = ((DebouncePeriodContent) (contentMap.get(DebouncePeriodContent.class)));
		if (content != null) {
		    Long value = content.getValue();
		    if (value != null) {
			device.setDebouncePeriod(value);
		    }
		}
	    }
	    {
		EdgeCountConfigContent content = ((EdgeCountConfigContent) (contentMap.get(EdgeCountConfigContent.class)));
		if (content != null) {
		    EdgeCountConfig value = content.getValue();
		    if (value != null) {
			device.setEdgeCountConfig(value.pin, value.edgeType, value.debounce);
		    }
		}
	    }
	    {
		PortContent content = ((PortContent) (contentMap.get(PortContent.class)));
		if (content != null) {
		    Port value = content.getValue();
		    if (value != null) {
			device.setPort(value.port, value.valueMask);
		    }
		}
	    }
	    {
		PortConfigurationContent content = ((PortConfigurationContent) (contentMap.get(PortConfigurationContent.class)));
		if (content != null) {
		    PortConfiguration value = content.getValue();
		    if (value != null) {
			device.setPortConfiguration(value.port, value.selectionMask, value.direction, value.value);
		    }
		}
	    }
	    {
		PortInterruptContent content = ((PortInterruptContent) (contentMap.get(PortInterruptContent.class)));
		if (content != null) {
		    PortInterrupt value = content.getValue();
		    if (value != null) {
			device.setPortInterrupt(value.port, value.interruptMask);
		    }
		}
	    }
	    {
		PortMonoflopContent content = ((PortMonoflopContent) (contentMap.get(PortMonoflopContent.class)));
		if (content != null) {
		    PortMonoflop value = content.getValue();
		    if (value != null) {
			device.setPortMonoflop(value.port, value.selectionMask, value.valueMask, value.time);
		    }
		}
	    }
	    {
		SelectedValuesContent content = ((SelectedValuesContent) (contentMap.get(SelectedValuesContent.class)));
		if (content != null) {
		    SelectedValues value = content.getValue();
		    if (value != null) {
			device.setSelectedValues(value.port, value.selectionMask, value.valueMask);
		    }
		}
	    }
	} catch (TimeoutException ex) {
	    Logger.getLogger(TinkerforgeAmbientLightService.class.getName()).log(Level.SEVERE, null, ex);
	} catch (NotConnectedException ex) {
	    Logger.getLogger(TinkerforgeAmbientLightService.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    @Override
    public void handleTinkerforgeIntent(TinkerforgeIO16Intent message) {
	//Nothing special so far
    }

    @Override
    public TinkerforgeIO16Event createEvent() {
	return new Event(getDeviceContent(), this);
    }

    @Override
    public void interrupt(char port, short interruptMask, short valueMask) {
	getDeviceContent().updateEmission(new InterruptContent(new Interrupt(port, interruptMask, valueMask)));
	TinkerforgeIO16Event event = createEvent();
	publish(event);
    }

    @Override
    public void monoflopDone(char port, short selectionMask, short valueMask) {
	getDeviceContent().updateEmission(new MonoflopDoneContent(new MonoflopDone(port, selectionMask, valueMask)));
	TinkerforgeIO16Event event = createEvent();
	publish(event);
    }

}

class Event extends ATinkerforgeDeviceEvent implements TinkerforgeIO16Event {

    public Event(TinkerforgeDeviceContent deviceContent, Service eventSender) {
	super(deviceContent, eventSender);
    }

}

class Intent extends ATinkerforgeDeviceIntent implements TinkerforgeIO16Intent {

    public Intent(TinkerforgeDeviceContent deviceContent, Agent intentSender) {
	super(deviceContent, intentSender);
    }

    public Intent(TinkerforgeDeviceContent deviceContent, Agent intentSender, String... intentReceivers) {
	super(deviceContent, intentSender, intentReceivers);
    }

}
