/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.intent;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.LCD20x4;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LCD20x4BacklightIntent extends AnIntent {

    public LCD20x4BacklightIntent(AHandler deviceHandler, String topic) {
	super(deviceHandler, topic, "backlight");
	super.addDescription(LCD20x4.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return getContent(LCD20x4.ENABLED) != null;
    }
}
