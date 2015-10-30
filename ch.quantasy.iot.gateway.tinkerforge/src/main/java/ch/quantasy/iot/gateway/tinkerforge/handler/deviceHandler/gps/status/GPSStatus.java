/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.gps.status;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.gps.GPS;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class GPSStatus extends AStatus {

    public GPSStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "GPSStatus", mqttClient);
	super.addDescription(GPS.FIX, Short.class, "JSON", "0", "...", "" + Short.MAX_VALUE);
	super.addDescription(GPS.SATELLITES_IN_VIEW, Short.class, "JSON", "0", "...", "" + Short.MAX_VALUE);
	super.addDescription(GPS.SATELLITES_IN_USE, Short.class, "JSON", "0", "...", "" + Short.MAX_VALUE);

    }

}