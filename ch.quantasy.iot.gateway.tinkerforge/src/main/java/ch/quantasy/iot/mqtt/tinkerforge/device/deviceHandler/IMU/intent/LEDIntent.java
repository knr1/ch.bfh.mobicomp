/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.IMU;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LEDIntent extends AnIntent<IMU> {

    public LEDIntent(IMU deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "LEDon");
	super.addDescription(IMU.VALUE, Boolean.class, "JSON", "true", "false");

    }

    @Override
    public boolean isExecutable() {
	return getContent(IMU.VALUE).rawValue != null;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
