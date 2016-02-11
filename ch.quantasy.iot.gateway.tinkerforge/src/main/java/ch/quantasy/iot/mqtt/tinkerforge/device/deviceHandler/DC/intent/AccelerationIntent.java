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
public class AccelerationIntent extends AnIntent<DC> {

    public AccelerationIntent(DC deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "acceleration");
	super.addDescription(DC.VALUE, Integer.class, "JSON", "0", "...", "" + Integer.MAX_VALUE);
    }

    @Override
    public boolean isExecutable() {
	return isPeriodInRange();
    }

    private boolean isPeriodInRange() {
	int period = getContent(DC.VALUE).getValue(Integer.class);
	return true;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
