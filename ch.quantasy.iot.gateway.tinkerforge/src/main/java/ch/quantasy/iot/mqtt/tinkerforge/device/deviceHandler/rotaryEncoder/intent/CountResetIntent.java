/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.intent;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.RotaryEncoder;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CountResetIntent extends AnIntent {

    public CountResetIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "countReset");
	super.addDescription(RotaryEncoder.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    @Override
    public boolean isExecutable() {
	return super.getValue(RotaryEncoder.ENABLED, Boolean.class);
    }
}
