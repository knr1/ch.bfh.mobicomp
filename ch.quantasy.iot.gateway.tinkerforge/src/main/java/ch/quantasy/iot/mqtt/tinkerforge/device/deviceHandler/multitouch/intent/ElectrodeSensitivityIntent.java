/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.multitouch.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.multitouch.MultiTouch;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ElectrodeSensitivityIntent extends AnIntent<MultiTouch> {

    public ElectrodeSensitivityIntent(MultiTouch deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "electrodeSensitivity");
	super.addDescription(MultiTouch.SENSITIVITY, Short.class, "JSON", "5", "201");
    }

    @Override
    public boolean isExecutable() {
	short sensitivity = super.getValue(MultiTouch.SENSITIVITY, Short.class);
	return sensitivity >= 5 && sensitivity <= 201;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
