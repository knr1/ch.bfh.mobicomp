/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.gateway;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.status.DeviceHandlerReadyStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.status.ManagedDeviceHandlersStatus;
import ch.quantasy.iot.mqtt.tinkerforge.gateway.intent.StackHandlerIntent;
import ch.quantasy.iot.mqtt.tinkerforge.gateway.status.ManagedStackAddresses;
import ch.quantasy.iot.mqtt.tinkerforge.gateway.status.ManagedStackHandlersStatus;
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
public class MQTT2TF extends AHandler {

    public static final String STACK_ADDRESS = "stackAddress";
    public static final String MANAGED_STACK_ADDRESSES = "stackAddresses";

    public static final String TOPIC = "iot/tf";
    private final TinkerforgeStackAgency agency;
    Type stackAddressSetType = new TypeToken<Set<TinkerforgeStackAddress>>() {
    }.getType();

    public MQTT2TF(String clientID, URI mqttURI) throws Throwable {
	super(mqttURI, TOPIC, clientID);

	this.agency = TinkerforgeStackAgency.getInstance();
	super.addIntentClass(StackHandlerIntent.class);
	super.addStatusClass(DeviceHandlerReadyStatus.class, ManagedStackHandlersStatus.class, ManagedDeviceHandlersStatus.class);
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
	if (!this.agency.containsStackAgent(stackAddress)) {
	    TinkerforgeStackAgent agent = this.agency.getStackAgent(stackAddress);
	    agent.addApplication(new MQTTTinkerforgeStackHandler(stackAddress, this));
	    publishAgents();
	}

    }

    public void publishAgents() {
	Set<TinkerforgeStackAddress> stackAddresses = TinkerforgeStackAgency.getInstance().getTinkerforgeStackAddress();

	getStatus(ManagedStackHandlersStatus.class).update(MANAGED_STACK_ADDRESSES, new ManagedStackAddresses(stackAddresses));
    }

    @Override
    public String getApplicationName() {
	return "MQTT2TF";
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
	final TinkerforgeStackAddress identifier = new TinkerforgeStackAddress("localhost");
	URI uri = URI.create("tcp://localhost:1883");
	//URI uri = URI.create("tcp://147.87.112.222:1883");
	//URI uri = URI.create("tcp://iot.eclipse.org:1883");

	if (args.length > 0) {
	    uri = URI.create(args[0]);
	} else {
	    System.out.printf("%s will be used as broker address.\n You can provide one as first argument such as: tcp://iot.eclipse.org:1883\n", uri);
	}
	MQTT2TF gw = new MQTT2TF("0", uri);
	gw.addNewAgent(identifier);
	System.in.read();
	gw.disconnectFromMQTT();
    }
}
