/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.Color.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.Color.Color;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class IlluminanceCallbackPeriodIntent extends AnIntent {

    public IlluminanceCallbackPeriodIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "illuminanceCallbackPeriod");
	super.addDescription(Color.PERIOD, Long.class, "JSON", "0", "...", "" + Long.MAX_VALUE);
    }

    @Override
    public boolean isExecutable() {
	return isPeriodInRange();
    }

    private boolean isPeriodInRange() {
	long period = getContent(Color.PERIOD).getValue(Long.class);
	return period >= 0;
    }

}
