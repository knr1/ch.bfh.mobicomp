/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.moisture.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.moisture.Moisture;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DebouncePeriodIntent extends AnIntent<Moisture> {

    public DebouncePeriodIntent(Moisture deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "debounce");
	super.addDescription(Moisture.PERIOD, Long.class, "JSON", "0", "...", "" + Long.MAX_VALUE);
    }

    @Override
    public boolean isExecutable() {
	return isPeriodInRange();
    }

    private boolean isPeriodInRange() {
	long period = getContent(Moisture.PERIOD).getValue(Long.class);
	return period >= 0;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}