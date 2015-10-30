/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.gateway.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.gateway.TFMQTTGateway;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class StackHandlerIntent extends AnIntent {

    //public TinkerforgeStackAddress stackAddress;
    public final Type stackAddressType = new TypeToken<TinkerforgeStackAddress>() {
    }.getType();

    public StackHandlerIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "stackHandler");
	super.addDescription(TFMQTTGateway.STACK_ADDRESS, TinkerforgeStackAddress.class, "JSON", "{\"hostName\":\"[String]\",\"port\":[Integer]}");

    }

    @Override
    public boolean isExecutable() {
	return getContent(TFMQTTGateway.STACK_ADDRESS).rawValue != null;
    }
}
