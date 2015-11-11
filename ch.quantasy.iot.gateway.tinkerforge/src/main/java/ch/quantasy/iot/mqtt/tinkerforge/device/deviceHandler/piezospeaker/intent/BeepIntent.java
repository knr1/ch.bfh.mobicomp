/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.intent;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.PiezoSpeaker;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class BeepIntent extends AnIntent {

    public BeepIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "beep");
	super.addDescription(PiezoSpeaker.ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(PiezoSpeaker.DURATION, Long.class, "JSON", "1", "...", "" + Long.MAX_VALUE);
	super.addDescription(PiezoSpeaker.FREQUENCY, Integer.class, "JSON", "585", "...", "7100");
    }

    public boolean isExecutable() {
	return isEnabled() && isDurationInRange() && isFrequencyInRange();
    }

    public boolean isEnabled() {
	try {
	    return getContent(PiezoSpeaker.ENABLED).getValue(Boolean.class);
	} catch (Throwable th) {
	    return false;
	}
    }

    private boolean isFrequencyInRange() {
	int frequency = getContent(PiezoSpeaker.FREQUENCY).getValue(Integer.class);
	return (frequency >= 685 && frequency <= 7100);
    }

    private boolean isDurationInRange() {
	long duration = getContent(PiezoSpeaker.DURATION).getValue(Long.class);
	return duration > 0;
    }
}
