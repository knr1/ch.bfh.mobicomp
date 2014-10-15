/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.stack.registration;

import ch.quantasy.messagebus.message.implementation.AnEvent;
import ch.quantasy.messagebus.message.implementation.AnIntent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerbus.bus.ATinkerforgeService;
import ch.quantasy.tinkerbus.service.content.ThrowableContent;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeStackAddressContent;
import ch.quantasy.tinkerbus.service.stack.TinkerforgeStackManager;
import ch.quantasy.tinkerbus.service.stack.TinkerforgeStackService;
import ch.quantasy.tinkerbus.service.stack.registration.content.StackRegistrationStateContent;
import ch.quantasy.tinkerbus.service.stack.registration.content.TinkerforgeStackServiceIDContent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeStackRegistrationService extends ATinkerforgeService<TinkerforgeStackRegistrationIntent, TinkerforgeStackRegistrationEvent> {

    private final Map<TinkerforgeStackAddress, TinkerforgeStackService> stackServices;

    public TinkerforgeStackRegistrationService() {
	super();
	stackServices = new HashMap<>();
    }

    @Override
    protected void handleIntent(TinkerforgeStackRegistrationIntent message) {
	if (message == null) {
	    return;
	}
	register(message);
    }

    private void register(TinkerforgeStackRegistrationIntent intent) {
	if (!intent.containsContent()) {
	    return;
	}

	StackRegistrationStateContent state = (StackRegistrationStateContent) intent.getContentByID(StackRegistrationStateContent.class);
	if (state == null || state.getValue() != StackRegistrationState.Register) {
	    return;
	}

	TinkerforgeStackAddressContent addressContent = (TinkerforgeStackAddressContent) intent.getContentByID(TinkerforgeStackAddressContent.class);

	if (addressContent == null) {
	    return;
	}
	TinkerforgeStackRegistrationEvent event = createEvent();
	TinkerforgeStackAddress address = addressContent.getValue();
	if (this.stackServices.containsKey(address)) {
	    event.addContents(new StackRegistrationStateContent(StackRegistrationState.AlreadyRegistered));
	} else {

	    try {
		TinkerforgeStackManager manager = new TinkerforgeStackManager(address);
		TinkerforgeStackService stackService = new TinkerforgeStackService(manager);
		stackServices.put(address, stackService);
		event.addContents(new StackRegistrationStateContent(StackRegistrationState.Registered));
		event.addContents(new TinkerforgeStackServiceIDContent(stackService.getID()));

	    } catch (Throwable ex) {
		event.addContents(new StackRegistrationStateContent(StackRegistrationState.Exception));
		event.addContents(new ThrowableContent(ex));
	    }
	}
	event.addContents(new TinkerforgeStackAddressContent(address));
	super.publish(event);
    }

    @Override
    public TinkerforgeStackRegistrationEvent createEvent() {
	return new Event(this);
    }

    @Override
    public String getID() {
	return this.getClass().getName();
    }

    public static TinkerforgeStackRegistrationIntent register(Agent intentSender, TinkerforgeStackAddress address) {
	TinkerforgeStackRegistrationIntent intent = new Intent(intentSender);
	intent.addContents(new TinkerforgeStackAddressContent(address));
	intent.addContents(new StackRegistrationStateContent(StackRegistrationState.Register));
	return intent;
    }
}

class Intent extends AnIntent implements TinkerforgeStackRegistrationIntent {

    public Intent(Agent intentSender) {
	super(intentSender);
    }

    public Intent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
    }

}

class Event extends AnEvent implements TinkerforgeStackRegistrationEvent {

    public Event(Service eventSender) {
	super(eventSender);
    }

}
