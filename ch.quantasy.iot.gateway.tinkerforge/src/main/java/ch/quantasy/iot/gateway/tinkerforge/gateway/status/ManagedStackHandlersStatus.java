/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.gateway.status;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import java.util.Objects;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ManagedStackHandlersStatus extends AStatus {

    public ManagedStackAddresses managedStackAddresses;

    public ManagedStackHandlersStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "managedStackHandlers", mqttClient);
	super.addTopicDescription("addresses", "String", "JSON");
    }

    public void updateManagedStackHandlers(ManagedStackAddresses managedStackHandlers) {
	if (Objects.equals(this.managedStackAddresses, managedStackHandlers)) {
	    return;
	} else {
	    this.managedStackAddresses = managedStackHandlers;
	    publishStatus("adddresses", toJSONMQTTMessage(managedStackHandlers));
	}
	return;
    }
}
