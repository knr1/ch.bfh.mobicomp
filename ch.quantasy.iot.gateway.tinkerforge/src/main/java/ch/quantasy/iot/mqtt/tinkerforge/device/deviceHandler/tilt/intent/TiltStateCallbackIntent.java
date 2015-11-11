/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.tilt.intent;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.tilt.Tilt;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TiltStateCallbackIntent extends AnIntent {

    public TiltStateCallbackIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "callbackState");
	super.addDescription(Tilt.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	try {
	    return getContent(Tilt.ENABLED).getValue(Boolean.class);
	} catch (Throwable th) {
	    return false;
	}
    }

}
