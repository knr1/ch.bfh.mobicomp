/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.LaserRangeFinder;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DistanceCallbackThresholdStatus extends AStatus {

    public DistanceCallbackThresholdStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "distanceCallbackThreshold", mqttClient);
	super.addDescription(LaserRangeFinder.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(LaserRangeFinder.THRESHOLD_MIN, Short.class, "JSON", "0", "...", "4000");
	super.addDescription(LaserRangeFinder.THRESHOLD_MAX, Short.class, "JSON", "0", "...", "4000");
    }
}
