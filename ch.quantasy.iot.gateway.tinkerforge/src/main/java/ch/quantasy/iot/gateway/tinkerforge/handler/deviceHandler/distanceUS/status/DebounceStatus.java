/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS.status;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS.DistanceUS;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DebounceStatus extends AStatus {

    public DebounceStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "debounce", mqttClient);
	super.addDescription(DistanceUS.PERIOD, Long.class, "JSON", "0", "..", "" + Long.MAX_VALUE);
    }

}