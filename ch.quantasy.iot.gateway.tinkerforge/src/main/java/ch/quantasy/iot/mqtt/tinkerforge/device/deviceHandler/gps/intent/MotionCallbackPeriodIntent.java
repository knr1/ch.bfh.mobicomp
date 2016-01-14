/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.GPS;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MotionCallbackPeriodIntent extends AnIntent<GPS> {

    public MotionCallbackPeriodIntent(GPS deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "motionCallbackPeriod");
	super.addDescription(GPS.PERIOD, Long.class, "JSON", "0", "...", "" + Long.MAX_VALUE);
    }

    @Override
    public boolean isExecutable() {
	return super.getValue(GPS.PERIOD, Long.class) >= 0;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
