/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.gateway.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.gateway.MQTT2TFGateway;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ManagedStackHandlersStatus extends AStatus {

    public ManagedStackHandlersStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "managedStackHandlers", mqttClient);
	super.addDescription(MQTT2TFGateway.MANAGED_STACK_ADDRESSES, ManagedStackAddresses.class, "JSON");
    }

}
