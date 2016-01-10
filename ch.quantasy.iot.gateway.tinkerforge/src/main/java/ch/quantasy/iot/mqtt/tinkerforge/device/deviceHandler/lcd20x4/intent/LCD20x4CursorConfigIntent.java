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
public class LCD20x4CursorConfigIntent extends AnIntent {

    public LCD20x4CursorConfigIntent(AHandler deviceHandler, String topic) {
	super(deviceHandler, topic, "cursorConfig");
	super.addDescription(LCD20x4.CURSOR_ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(LCD20x4.BLINKING, Boolean.class, "JSON", "true", "false");
	super.addDescription(LCD20x4.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return getContent(LCD20x4.ENABLED).getValue(Boolean.class) && isCursorEnabledSet() && isBlinkingSet();
    }

    private boolean isCursorEnabledSet() {
	return getContent(LCD20x4.CURSOR_ENABLED) != null;
    }

    private boolean isBlinkingSet() {
	return getContent(LCD20x4.BLINKING) != null;
    }
}
