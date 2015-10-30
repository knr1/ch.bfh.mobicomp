/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.multitouch.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.multitouch.MultiTouch;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ElectrodeConfigIntent extends AnIntent {

    public ElectrodeConfigIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "electrodeConfig");
	super.addDescription(MultiTouch.ENABLED, Integer.class, "JSON", "0", "8191");
    }

    @Override
    public boolean isExecutable() {
	int config = super.getValue(MultiTouch.ENABLED, Integer.class);
	return config >= 0 && config <= 8191;
    }

}
