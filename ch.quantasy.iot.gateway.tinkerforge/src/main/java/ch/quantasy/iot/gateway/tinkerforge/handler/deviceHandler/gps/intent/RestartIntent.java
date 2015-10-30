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
public class RestartIntent extends AnIntent {

    public RestartIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "restart");
	super.addDescription(GPS.RESTART_TYPE, Short.class, "JSON", "0", "1", "2", "3");
    }

    @Override
    public boolean isExecutable() {
	return super.getValue(GPS.RESTART_TYPE, Short.class) <= 3;
    }

}
