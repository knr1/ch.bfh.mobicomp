/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.soundIntensity.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.soundIntensity.SoundIntensity;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DebouncePeriodIntent extends AnIntent<SoundIntensity> {

    public DebouncePeriodIntent(SoundIntensity deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "debouncePeriod");
	super.addDescription(SoundIntensity.PERIOD, Long.class, "JSON", "0", "...", "" + Long.MAX_VALUE);
    }

    @Override
    public boolean isExecutable() {
	return isPeriodInRange();
    }

    private boolean isPeriodInRange() {
	long period = getContent(SoundIntensity.PERIOD).getValue(Long.class);
	return period >= 0;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
