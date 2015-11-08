/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLightV2.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ambientLightV2.AmbientLightV2;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ConfigIntent extends AnIntent {

    public ConfigIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "config");
	super.addDescription(AmbientLightV2.ILLUMINANCE_RANGE, Short.class, "JSON", "0,1,2,3,4,5,6");
	super.addDescription(AmbientLightV2.INTEGRATION_TIME, Short.class, "JSON", "0,1,2,3,4,5,6,7");
	super.addDescription(AmbientLightV2.ENABLED, Boolean.class, "JSON", "true", "false");
	//short illuminanceRange and short integrationTime
    }

    @Override
    public boolean isExecutable() {
	return isIlluminanceRangeInRange() && isIntegrationTimeInRange() && isEnabled();
    }

    private boolean isEnabled() {
	return getContent(AmbientLightV2.ENABLED).getValue(Boolean.class);
    }

    private boolean isIlluminanceRangeInRange() {
	short illuminanceRange = getContent(AmbientLightV2.ILLUMINANCE_RANGE).getValue(Short.class);
	return illuminanceRange >= 0 && illuminanceRange <= 6;
    }

    private boolean isIntegrationTimeInRange() {
	short integrationTime = getContent(AmbientLightV2.INTEGRATION_TIME).getValue(Short.class);
	return integrationTime >= 0 && integrationTime <= 7;
    }

}
