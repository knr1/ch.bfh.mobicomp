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
public class EnabledIntent extends AnIntent<DC> {

    public EnabledIntent(DC deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "enabled");
	super.addDescription(DC.VALUE, Boolean.class, "JSON", "true", "false");

    }

    @Override
    public boolean isExecutable() {
	return getContent(DC.VALUE).rawValue != null;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
