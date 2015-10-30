/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.gps.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.gps.GPS;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CoordinatesCallbackPeriodIntent extends AnIntent {

    public CoordinatesCallbackPeriodIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "coordinatesCallbackPeriod");
	super.addDescription(GPS.PERIOD, Long.class, "JSON", "0", "...", "" + Long.MAX_VALUE);
    }

    @Override
    public boolean isExecutable() {
	return super.getValue(GPS.PERIOD, Long.class) >= 0;
    }

}
