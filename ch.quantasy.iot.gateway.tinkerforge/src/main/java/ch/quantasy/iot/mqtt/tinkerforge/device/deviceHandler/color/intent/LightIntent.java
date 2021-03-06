/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.color.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.color.Color;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LightIntent extends AnIntent<Color> {

    public LightIntent(Color deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "light");
	super.addDescription(Color.LIGHT_ON, Boolean.class, "JSON", "true", "false");

    }

    @Override
    public boolean isExecutable() {
	return getContent(Color.LIGHT_ON).rawValue != null;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
