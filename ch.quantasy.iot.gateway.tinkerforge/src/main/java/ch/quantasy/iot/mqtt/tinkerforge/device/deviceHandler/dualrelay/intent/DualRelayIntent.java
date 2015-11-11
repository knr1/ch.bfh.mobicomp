/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay.intent;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay.DualRelay;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DualRelayIntent extends AnIntent {

    public DualRelayIntent(AHandler deviceHandler, String topic) {
	super(deviceHandler, topic, "dualRelay");
	super.addDescription(DualRelay.DUALRELAY_RELAY1, Boolean.class, "JSON", "true", "false");
	super.addDescription(DualRelay.DUALRELAY_RELAY2, Boolean.class, "JSON", "true", "false");
	super.addDescription(DualRelay.DUALRELAY_ENABLED, Boolean.class, "JSON", "true", "false");
    }

    public boolean isExecutable() {
	try {
	    return getContent(DualRelay.DUALRELAY_ENABLED).getValue(Boolean.class);
	} catch (Throwable th) {
	    return false;
	}
    }
}
