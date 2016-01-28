/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.PiezoSpeaker;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MorseIntent extends AnIntent<PiezoSpeaker> {

    public MorseIntent(PiezoSpeaker deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "morse");
	super.addDescription(PiezoSpeaker.ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(PiezoSpeaker.CODE, String.class, "JSON", ".", "-", " ", "unbounded");
	super.addDescription(PiezoSpeaker.FREQUENCY, Integer.class, "JSON", "685", "...", "7100");
    }

    @Override
    public boolean isExecutable() {
	return isEnabled() && isCodeInRange() && isFrequencyInRange();
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

    private boolean isCodeInRange() {
	String code = getContent(PiezoSpeaker.CODE).getValue(String.class);
	return code != null && code != "";
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }
}
