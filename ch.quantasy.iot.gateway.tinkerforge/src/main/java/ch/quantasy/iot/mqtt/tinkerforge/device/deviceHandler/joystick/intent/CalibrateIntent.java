/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.Joystick;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CalibrateIntent extends AnIntent<Joystick> {

    public CalibrateIntent(Joystick deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "calibrate");
	super.addDescription(Joystick.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    @Override
    public boolean isExecutable() {
	return super.getValue(Joystick.ENABLED, Boolean.class);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
