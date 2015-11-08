/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.accelerometer.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.accelerometer.Accelerometer;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ConfigIntent extends AnIntent {

    public ConfigIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "config");
	super.addDescription(Accelerometer.DATA_RATE, Short.class, "JSON", "0,1,2,3,4,5,6,7,8,9");
	super.addDescription(Accelerometer.FULL_SCALE, Short.class, "JSON", "0,1,2,3,4");
	super.addDescription(Accelerometer.FILTER_BANDWIDTH, Short.class, "JSON", "0,1,2,3");
	super.addDescription(Accelerometer.ENABLED, Boolean.class, "JSON", "true", "false");
	//short illuminanceRange and short integrationTime
    }

    @Override
    public boolean isExecutable() {
	return isDataRateInRange() && isFullScaleInRange() && isFilterBandwidthInRange() && isEnabled();
    }

    private boolean isEnabled() {
	return getContent(Accelerometer.ENABLED).getValue(Boolean.class);
    }

    private boolean isDataRateInRange() {
	short dataRate = getContent(Accelerometer.DATA_RATE).getValue(Short.class);
	return dataRate >= 0 && dataRate <= 9;
    }

    private boolean isFullScaleInRange() {
	short fullScale = getContent(Accelerometer.FULL_SCALE).getValue(Short.class);
	return fullScale >= 0 && fullScale <= 4;
    }

    private boolean isFilterBandwidthInRange() {
	short filterBandwidth = getContent(Accelerometer.FILTER_BANDWIDTH).getValue(Short.class);
	return filterBandwidth >= 0 && filterBandwidth <= 3;
    }

}
