/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.linearPoti.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.linearPoti.LinearPoti;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AnalogCallbackPeriodIntent extends AnIntent {

    public AnalogCallbackPeriodIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "analogCallbackPeriod");
	super.addDescription(LinearPoti.PERIOD, Long.class, "JSON", "0", "...", "" + Long.MAX_VALUE);
    }

    @Override
    public boolean isExecutable() {
	return isPeriodInRange();
    }

    private boolean isPeriodInRange() {
	long period = getContent(LinearPoti.PERIOD).getValue(Long.class);
	return period >= 0;
    }

}
