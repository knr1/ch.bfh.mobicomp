/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.stack.registration;

import ch.quantasy.messagebus.message.DefaultIntent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeStackRegistrationIntent extends DefaultIntent {

    private TinkerforgeStackAddress tinkerforgeStackAddress;
    private boolean requestRegisteredTinkerforgeStackServices;

    public TinkerforgeStackRegistrationIntent(Agent intentSender) {
	super(intentSender);
    }

    public TinkerforgeStackRegistrationIntent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
    }

    public TinkerforgeStackAddress getTinkerforgeStackAddress() {
	return tinkerforgeStackAddress;
    }

    public void setTinkerforgeStackAddress(TinkerforgeStackAddress tinkerforgeStackAddress) {
	this.tinkerforgeStackAddress = tinkerforgeStackAddress;
    }

    public boolean isRequestRegisteredTinkerforgeStackServices() {
	return requestRegisteredTinkerforgeStackServices;
    }

    public void setRequestRegisteredTinkerforgeStackServices(boolean requestRegisteredTinkerforgeStackServices) {
	this.requestRegisteredTinkerforgeStackServices = requestRegisteredTinkerforgeStackServices;
    }

    @Override
    public String toString() {
	return "TinkerforgeStackRegistrationIntent{" + "tinkerforgeStackAddress=" + tinkerforgeStackAddress + ", requestRegisteredTinkerforgeStackServices=" + requestRegisteredTinkerforgeStackServices + '}';
    }

}
