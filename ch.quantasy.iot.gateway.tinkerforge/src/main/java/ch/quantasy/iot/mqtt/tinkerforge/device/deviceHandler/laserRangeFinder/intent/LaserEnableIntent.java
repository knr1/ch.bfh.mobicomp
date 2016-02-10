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
public class LaserEnableIntent extends AnIntent<LaserRangeFinder> {

    public LaserEnableIntent(LaserRangeFinder deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "laser");
	super.addDescription(LaserRangeFinder.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    @Override
    public boolean isExecutable() {
	return getContent(LaserRangeFinder.ENABLED).rawValue != null;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
