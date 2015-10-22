/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.gateway;

import ch.quantasy.iot.gateway.tinkerforge.handler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.status.DeviceHandlerReadyStatus;
import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.gateway.intent.StackHandlerIntent;
import ch.quantasy.iot.gateway.tinkerforge.gateway.status.ManagedStackAddresses;
import ch.quantasy.iot.gateway.tinkerforge.gateway.status.ManagedStackHandlersStatus;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TFMQTTGateway extends AHandler {

    public static final String TOPIC = "IOT/Gateway/TF";
    private final TinkerforgeStackAgency agency;
    Type stackAddressSetType = new TypeToken<Set<TinkerforgeStackAddress>>() {
    }.getType();

    public TFMQTTGateway(String clientID, URI mqttURI) throws Throwable {
	super(mqttURI, TOPIC, clientID);

	this.agency = TinkerforgeStackAgency.getInstance();
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

	getStatus(ManagedStackHandlersStatus.class).updateManagedStackHandlers(new ManagedStackAddresses(stackAddresses));
    }

    @Override
    public String getApplicationName() {
	return "TFMQTTGateway";
    }

    @Override
    protected Class[] getIntentClasses() {
	return new Class[]{StackHandlerIntent.class};
    }

    @Override
    protected Class[] getEventClasses() {
	return new Class[]{};
    }

    @Override
    protected Class[] getStatusClasses() {
	return new Class[]{DeviceHandlerReadyStatus.class, ManagedStackHandlersStatus.class};
    }

    @Override
    public void executeIntent(AnIntent intent) throws Throwable {
	if (intent instanceof StackHandlerIntent) {
	    executeIntent((StackHandlerIntent) intent);
	}
    }

    public void executeIntent(StackHandlerIntent intent) throws Throwable {
	try {
	    addNewAgent(new TinkerforgeStackAddress(intent.stackAddress.hostName, intent.stackAddress.port));
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    public static void main(String[] args) throws Throwable {

	Gson g = new Gson();
	TinkerforgeStackAddress a = g.fromJson("{\"hostName\":\"EG\",\"port\":1234}", TinkerforgeStackAddress.class);
	System.out.println(a.toString());
	final TinkerforgeStackAddress identifier = new TinkerforgeStackAddress(
		"localhost");
	TFMQTTGateway gw = new TFMQTTGateway("TFMQTTGateway", URI.create("tcp://localhost:1883"));
	gw.addNewAgent(identifier);
	System.in.read();
	gw.disconnectFromMQTT();

    }
}
