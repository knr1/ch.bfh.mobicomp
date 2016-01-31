/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.DC;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DriveModeIntent extends AnIntent<DC> {

    public DriveModeIntent(DC deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "driveMode");
	super.addDescription(DC.VALUE, Short.class, "JSON", "0", "1");
    }

    @Override
    public boolean isExecutable() {
	return isPeriodInRange();
    }

    private boolean isPeriodInRange() {
	short value = getContent(DC.VALUE).getValue(Short.class);
	return value == 0 || value == 1;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
