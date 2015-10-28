/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.gateway.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class StackHandlerIntent extends AnIntent {

    public static final String STACK_ADDRESS = "stackAddress";

    //public TinkerforgeStackAddress stackAddress;
    public final Type stackAddressType = new TypeToken<TinkerforgeStackAddress>() {
    }.getType();

    public StackHandlerIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "stackHandler");
	super.addDescription(STACK_ADDRESS, TinkerforgeStackAddress.class, "JSON", "{\"hostName\":\"[String]\",\"port\":[Integer]}");

    }

//    @Override
//    protected void update(String string, MqttMessage mm) throws Throwable {
//	if (string.endsWith(getName() + "/stackAddress")) {
//	    stackAddress = fromMQTTMessage(mm, TinkerforgeStackAddress.class);
//	}
//
//    }
    //TODO: Geht nicht, da in der Map ein leading /  (Das ist aber falsch)
    @Override
    public boolean isExecutable() {
	return getTriple(STACK_ADDRESS).rawValue != null;
    }
}
