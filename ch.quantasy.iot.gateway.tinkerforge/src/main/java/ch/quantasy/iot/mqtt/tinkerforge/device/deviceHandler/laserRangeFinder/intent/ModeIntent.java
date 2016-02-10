/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.LaserRangeFinder;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ModeIntent extends AnIntent<LaserRangeFinder> {

    public ModeIntent(LaserRangeFinder deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "mode");
	super.addDescription(LaserRangeFinder.MODE, Short.class, "JSON", "0", "1", "2", "3", "4");
    }

    @Override
    public boolean isExecutable() {
	return isModeInRange();
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

    private boolean isModeInRange() {
	short mode = getContent(LaserRangeFinder.MODE).getValue(Short.class);
	return (mode >= 0 && mode <= 4);
    }

}
