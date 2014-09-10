/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.stack;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.service.stack.registration.StackRegistrationState;
import ch.quantasy.tinkerbus.service.stack.registration.TinkerforgeStackRegistrationEvent;
import ch.quantasy.tinkerbus.service.stack.registration.TinkerforgeStackRegistrationIntent;
import ch.quantasy.tinkerbus.service.stack.registration.TinkerforgeStackRegistrationService;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeStackAgent extends ATinkerforgeAgent {

    public static final String ID = "TinkerforgeStackAgent";

    @Override
    protected void handleTinkerMessage(DefaultEvent message) {
	handleStackService(TinkerforgeStackService.getTinkerforgeStackEvent(message));
	handleStackRegistrationService(TinkerforgeStackRegistrationService.getTinkerforgeStackRegistrationEvent(message));

    }

    private void handleStackRegistrationService(TinkerforgeStackRegistrationEvent event) {
	if (event == null) {
	    return;
	}
	System.out.println("Event: " + event);
	if (event.getRegistrationState() == StackRegistrationState.Registered) {
	    Set<String> stackServiceIDs = event.getStackServiceIDs();
	    TinkerforgeStackIntent intent = TinkerforgeStackService.createIntent(this);
	    for (String stackServiceID : stackServiceIDs) {
		intent.addReceiverIDs(stackServiceID);
	    }
	    intent.setStackIntentState(StackConnectionIntentState.Connect);;
	    System.out.println("Intent: " + intent);
	    publish(intent);
	}

    }

    private void handleStackService(TinkerforgeStackEvent event) {
	if (event == null) {
	    return;
	}
	System.out.println("Event: " + event);
    }

    @Override
    public String getID() {
	return ID;
    }

    public void register() {
	TinkerforgeStackRegistrationIntent intent = TinkerforgeStackRegistrationService.createIntent(this);
	intent.setTinkerforgeStackAddress(new TinkerforgeStackAddress("localhost", TinkerforgeStackAddress.DEFAULT_PORT));
	System.out.println("Intent: " + intent);
	publish(intent);
    }

}
