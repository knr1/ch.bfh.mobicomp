/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.LCD20x4;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LCD20x4WriteIntent extends AnIntent<LCD20x4> {

    public LCD20x4WriteIntent(LCD20x4 deviceHandler, String topic) {
	super(deviceHandler, topic, "write");
	super.addDescription(LCD20x4.LINE, Short.class, "JSON", "0,1,2,3");
	super.addDescription(LCD20x4.FROM, Short.class, "JSON", "0..19");
	super.addDescription(LCD20x4.TEXT, String.class, "JSON", "a..Z0..9");
	super.addDescription(LCD20x4.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return getContent(LCD20x4.ENABLED).getValue(Boolean.class) && isLineInRange() && isFromInRange() && isText();
    }

    private boolean isLineInRange() {
	Short value = getContent(LCD20x4.LINE).getValue(Short.class);
	if (value == null) {
	    return false;
	}
	if (value < 0 || value > 3) {
	    return false;
	}
	return true;
    }

    private boolean isFromInRange() {
	Short value = getContent(LCD20x4.FROM).getValue(Short.class);
	if (value == null) {
	    return false;
	}
	if (value < 0 || value > 19) {
	    return false;
	}
	return true;
    }

    private boolean isText() {
	String value = getContent(LCD20x4.TEXT).getValue(String.class);
	if (value == null) {
	    return false;
	}
	return true;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }
}
