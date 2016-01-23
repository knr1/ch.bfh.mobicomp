/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.CO2.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.CO2.CO2;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.barometer.Barometer;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.Humidity;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AirPressureCallbackPeriodIntent extends AnIntent<CO2> {

    public AirPressureCallbackPeriodIntent(CO2 deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "airPressureCallbackPeriod");
	super.addDescription(Barometer.PERIOD, Long.class, "JSON", "0", "...", "" + Long.MAX_VALUE);
    }

    @Override
    public boolean isExecutable() {
	return isPeriodInRange();
    }

    private boolean isPeriodInRange() {
	long period = getContent(Humidity.PERIOD).getValue(Long.class);
	return period >= 0;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
