/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualButton.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualButton.DualButton;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DualLEDIntent extends AnIntent<DualButton> {

    public DualLEDIntent(DualButton deviceHandler, String topic) {
	super(deviceHandler, topic, "dualLED");
	super.addDescription(DualButton.LED_L, Short.class, "JSON", "0", "1", "2", "3");
	super.addDescription(DualButton.LED_R, Short.class, "JSON", "0", "1", "2", "3");
	super.addDescription(DualButton.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return getContent(DualButton.ENABLED).getValue(Boolean.class) && isLEDLInRange() && isLEDRInRange();
    }

    private boolean isLEDLInRange() {
	int led = getContent(DualButton.LED_L).getValue(Integer.class);
	return (led >= 0 || led <= 3);
    }

    private boolean isLEDRInRange() {
	int led = getContent(DualButton.LED_R).getValue(Integer.class);
	return (led >= 0 || led <= 3);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }
}
