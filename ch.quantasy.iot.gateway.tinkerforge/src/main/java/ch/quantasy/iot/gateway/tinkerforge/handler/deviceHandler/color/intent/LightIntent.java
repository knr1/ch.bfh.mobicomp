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
public class LightIntent extends AnIntent {

    public LightIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "light");
	super.addDescription(Color.LIGHT_ON, Boolean.class, "JSON", "true", "false");

    }

    @Override
    public boolean isExecutable() {
	return getContent(Color.LIGHT_ON).rawValue != null;
    }

}
