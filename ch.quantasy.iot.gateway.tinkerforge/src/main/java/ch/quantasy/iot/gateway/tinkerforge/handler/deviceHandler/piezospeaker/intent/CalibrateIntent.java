/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.PiezoSpeaker;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CalibrateIntent extends AnIntent {

    public CalibrateIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "calibrate");
	super.addDescription(PiezoSpeaker.ENABLED, Boolean.class, "JSON", "true", "false");
    }

    @Override
    public boolean isExecutable() {
	return super.getValue(PiezoSpeaker.ENABLED, Boolean.class);
    }

}
