/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.status;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.Barometer;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AveragingStatus extends AStatus {

    public AveragingStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "averaging", mqttClient);
	super.addDescription(Barometer.MOVING_AVERAGE_PRESSURE, Short.class, "JSON", "0", "..", "25");
	super.addDescription(Barometer.AVERAGE_PRESSURE, Short.class, "JSON", "0", "..", "10");
	super.addDescription(Barometer.AVERAGE_TEMPERATURE, Short.class, "JSON", "0", "..", "255");

    }

}
