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
public class AveragingStatus extends AStatus {

    public AveragingStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "averaging", mqttClient);
	super.addDescription(LaserRangeFinder.AVERAGE_DISTANCE_LENGTH, Short.class, "JSON", "0", "..", "30");
	super.addDescription(LaserRangeFinder.AVERAGE_VELOCITY_LENGTH, Short.class, "JSON", "0", "..", "30");
    }

}
