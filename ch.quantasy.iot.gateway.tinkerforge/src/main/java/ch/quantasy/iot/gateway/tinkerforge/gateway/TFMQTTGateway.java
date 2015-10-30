/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.gateway;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.gateway.intent.StackHandlerIntent;
import ch.quantasy.iot.gateway.tinkerforge.gateway.status.ManagedStackAddresses;
import ch.quantasy.iot.gateway.tinkerforge.gateway.status.ManagedStackHandlersStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.status.DeviceHandlerReadyStatus;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TFMQTTGateway extends AHandler {

    public static final String STACK_ADDRESS = "stackAddress";
    public static final String MANAGED_STACK_ADDRESSES = "stackAddresses";

    public static final String TOPIC = "IOT/Gateway/TF";
    private final TinkerforgeStackAgency agency;
    Type stackAddressSetType = new TypeToken<Set<TinkerforgeStackAddress>>() {
    }.getType();

    public TFMQTTGateway(String clientID, URI mqttURI) throws Throwable {
	super(mqttURI, TOPIC, clientID);

	this.agency = TinkerforgeStackAgency.getInstance();
	super.addIntentClass(StackHandlerIntent.class);
	super.addStatusClass(DeviceHandlerReadyStatus.class, ManagedStackHandlersStatus.class);
    }

    public void addNewAgents(Set<TinkerforgeStackAddress> stackAddresses) {
	if (stackAddresses == null || stackAddresses.isEmpty()) {
	    return;
	}
	for (TinkerforgeStackAddress address : stackAddresses) {
	    addNewAgent(address);
	}
    }

    public void addNewAgent(TinkerforgeStackAddress stackAddress) {
	TinkerforgeStackAgent agent = this.agency.getStackAgent(stackAddress);
	agent.addApplication(new MQTTTinkerforgeStackHandler(getMqttURI()));
	publishAgents();

    }

    public void publishAgents() {
	Set<TinkerforgeStackAddress> stackAddresses = TinkerforgeStackAgency.getInstance().getTinkerforgeStackAddress();

	getStatus(ManagedStackHandlersStatus.class).update(MANAGED_STACK_ADDRESSES, new ManagedStackAddresses(stackAddresses));
    }

    @Override
    public String getApplicationName() {
	return "TFMQTTGateway";
    }

    @Override
    public void executeIntent(AnIntent intent) throws Throwable {
	if (intent instanceof StackHandlerIntent) {
	    executeIntent((StackHandlerIntent) intent);
	}
    }

    public void executeIntent(StackHandlerIntent intent) throws Throwable {
	try {
	    TinkerforgeStackAddress address = intent.getContent(STACK_ADDRESS).getValue(TinkerforgeStackAddress.class);
	    addNewAgent(address);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    public static void main(String[] args) throws Throwable {
	final TinkerforgeStackAddress identifier = new TinkerforgeStackAddress(
		"localhost");
	TFMQTTGateway gw = new TFMQTTGateway("TFMQTTGateway", URI.create("tcp://localhost:1883"));
	gw.addNewAgent(identifier);
	System.in.read();
	gw.disconnectFromMQTT();
    }
}
