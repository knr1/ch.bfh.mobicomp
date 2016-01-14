/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.IMUV2;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LEDIntent extends AnIntent<IMUV2> {

    public LEDIntent(IMUV2 deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "LEDon");
	super.addDescription(IMUV2.VALUE, Boolean.class, "JSON", "true", "false");

    }

    @Override
    public boolean isExecutable() {
	return getContent(IMUV2.VALUE).rawValue != null;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
