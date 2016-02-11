/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.DC;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CurrentVelocityPeriodIntent extends AnIntent<DC> {

    public CurrentVelocityPeriodIntent(DC deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "currentVelocityPeriod");
	super.addDescription(DC.PERIOD, Integer.class, "JSON", "0", "...", "" + Integer.MAX_VALUE);
    }

    @Override
    public boolean isExecutable() {
	return isPeriodInRange();
    }

    private boolean isPeriodInRange() {
	int period = getContent(DC.PERIOD).getValue(Integer.class);
	return period >= 0;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
