/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.Color;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ColorConfigIntent extends AnIntent {

    public ColorConfigIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "config");
	super.addDescription(Color.GAIN, short.class, "JSON", "0", "1", "2", "3");
	super.addDescription(Color.INTEGRATION_TIME, short.class, "JSON", "0", "1", "2", "3", "4");

	super.addDescription(Color.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	return isEnabled() && isGainInRange() && isIntegrationTimeInRange();
    }

    private boolean isEnabled() {
	return getContent(Color.ENABLED).getValue(Boolean.class);
    }

    private boolean isGainInRange() {
	short gain = getContent(Color.GAIN).getValue(Short.class);
	return (gain <= 3 && gain >= 0);
    }

    private boolean isIntegrationTimeInRange() {
	short integrationTime = getContent(Color.GAIN).getValue(Short.class);
	return (integrationTime <= 4 && integrationTime >= 0);
    }

}
