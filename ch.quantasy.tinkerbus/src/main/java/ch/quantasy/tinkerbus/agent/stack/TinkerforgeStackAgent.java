/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.agent.stack;

import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.service.stack.TinkerforgeStackService;
import ch.quantasy.tinkerbus.service.stack.message.TinkerforgeStackIntent;
import ch.quantasy.tinkerbus.service.stack.registration.StackRegistrationState;
import ch.quantasy.tinkerbus.service.stack.registration.TinkerforgeStackRegistrationService;
import ch.quantasy.tinkerbus.service.stack.registration.content.StackRegistrationStateContent;
import ch.quantasy.tinkerbus.service.stack.registration.content.TinkerforgeStackServiceIDContent;
import ch.quantasy.tinkerbus.service.stack.registration.message.TinkerforgeStackRegistrationIntent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeStackAgent extends ATinkerforgeAgent {

    public static final String ID = "TinkerforgeStackAgent";

    @Override
    protected void handleEvent(Event message) {
	if (message == null) {
	    return;
	}

	handleStackService(message);
	handleStackRegistrationService(message);

    }

    private void handleStackRegistrationService(Event event) {
	StackRegistrationStateContent stateContent = (StackRegistrationStateContent) event.getContentByID(StackRegistrationStateContent.class);
	if (stateContent == null) {
	    return;
	}
	if (stateContent.getValue() == StackRegistrationState.Registered) {
	    TinkerforgeStackServiceIDContent stackServiceIDContent = (TinkerforgeStackServiceIDContent) event.getContentByID(TinkerforgeStackServiceIDContent.class);
	    if (stackServiceIDContent == null) {
		return;
	    }
	    TinkerforgeStackIntent intent = TinkerforgeStackService.connect(this, stackServiceIDContent.getValue());
	    System.out.println("Intent: " + intent);
	    publish(intent);
	}

    }

    private void handleStackService(Event event) {
	//Nothing special...
    }

    @Override
    public String getID() {
	return ID;
    }

    public void register() {
	TinkerforgeStackRegistrationIntent intent = TinkerforgeStackRegistrationService.register(this, new TinkerforgeStackAddress("obergeschoss", TinkerforgeStackAddress.DEFAULT_PORT));
	System.out.println("Intent: " + intent);
	publish(intent);
	intent = TinkerforgeStackRegistrationService.register(this, new TinkerforgeStackAddress("erdgeschoss", TinkerforgeStackAddress.DEFAULT_PORT));
	System.out.println("Intent: " + intent);
	publish(intent);
    }

}
