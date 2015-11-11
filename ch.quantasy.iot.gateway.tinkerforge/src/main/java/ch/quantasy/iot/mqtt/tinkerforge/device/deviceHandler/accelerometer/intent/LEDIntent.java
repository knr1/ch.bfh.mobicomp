/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.intent;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.Accelerometer;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LEDIntent extends AnIntent {

    public LEDIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "LEDon");
	super.addDescription(Accelerometer.VALUE, Boolean.class, "JSON", "true", "false");

    }

    @Override
    public boolean isExecutable() {
	return getContent(Accelerometer.VALUE).rawValue != null;
    }

}
