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
public class VelocityIntent extends AnIntent<DC> {

    public VelocityIntent(DC deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "velocity");
	super.addDescription(DC.VALUE, Short.class, "JSON", "-32767", "...", "32767");
    }

    @Override
    public boolean isExecutable() {
	return isPeriodInRange();
    }

    private boolean isPeriodInRange() {
	int value = getContent(DC.VALUE).getValue(Integer.class);
	return value >= -32767 && value <= 32767;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
