/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.stack.registration;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeService;
import ch.quantasy.tinkerbus.service.stack.TinkerforgeStackManager;
import ch.quantasy.tinkerbus.service.stack.TinkerforgeStackService;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeStackRegistrationService extends ATinkerforgeService<TinkerforgeStackRegistrationIntent, TinkerforgeStackRegistrationEvent> {

    public static final String ID = "ch.quantasy.tinkerbus.TinkerforgeStackRegistrationService";
    public static final String INTENT_REGISTER = "register";
    public static final String INTENT_LIST_REGISTERED_STACK_SERVICES = "listRegisteredStackServices";
    public static final String HOST_NAME = "host name";
    public static final String PORT = "port";
    public static final String TINKERFORGE_STACK_SERVICE_ID = "tinkerforgeStackServiceID";
    public static final String TINKERFORGE_STACK_SERVICE_IDS = "tinkerforgeStackServiceIDS";

    public static final String EVENT_STACK_REGISTERED = "registered";
    public static final String EVENT_STACK_ALREADY_REGISTERED = "already registered";
    public static final String EVENT_REGISTERED_STACK_SERVICES = "registered stack services";

    public static final String EVENT_STACK_REGISTRATION_EXCEPTION = "registration exception";
    public static final String EXCEPTION = "exception";

    private final Map<TinkerforgeStackAddress, TinkerforgeStackService> stackServices;

    public TinkerforgeStackRegistrationService() {
	stackServices = new HashMap<>();
    }

    @Override
    protected void handleTinkerMessage(TinkerforgeStackRegistrationIntent message) {
	register(message.getTinkerforgeStackAddress());

	if (message.isRequestRegisteredTinkerforgeStackServices()) {
	    TinkerforgeStackRegistrationEvent event = new TinkerforgeStackRegistrationEvent(this);
	    for (TinkerforgeStackService service : stackServices.values()) {
		event.addStackServiceID(service.getID());
	    }
	    publish(event);
	}
    }

    @SuppressWarnings({"BroadCatchBlock", "TooBroadCatch"})
    private void register(TinkerforgeStackAddress address) {
	if (address == null) {
	    return;
	}
	TinkerforgeStackRegistrationEvent event = null;
	try {
	    if (!stackServices.containsKey(address)) {
		TinkerforgeStackService stackService = new TinkerforgeStackService(new TinkerforgeStackManager(address));
		stackServices.put(address, stackService);
		event = createEvent();

		event.setRegistrationState(StackRegistrationState.Registered);
		event.addStackServiceID(stackService.getID());

	    } else {
		event = createEvent();
		event.setRegistrationState(StackRegistrationState.AlreadyRegistered);
		event.addStackServiceID(stackServices.get(address).getID());
	    }
	} catch (Throwable ex) {
	    event = createEvent();
	    event.setRegistrationState(StackRegistrationState.Exception);
	    event.setException(ex.toString());
	}
	event.setTinkerforgeStackAddress(address);
	super.publish(event);
    }

    @Override
    public String getID() {
	return ID;
    }

    @Override
    public TinkerforgeStackRegistrationEvent createEvent() {
	return new TinkerforgeStackRegistrationEvent(this);
    }

    public static TinkerforgeStackRegistrationEvent getTinkerforgeStackRegistrationEvent(DefaultEvent event) {
	if (event instanceof TinkerforgeStackRegistrationEvent) {
	    return (TinkerforgeStackRegistrationEvent) event;
	} else {
	    return null;
	}
    }

    public static TinkerforgeStackRegistrationIntent createIntent(Agent agent) {
	return new TinkerforgeStackRegistrationIntent(agent);
    }

}
