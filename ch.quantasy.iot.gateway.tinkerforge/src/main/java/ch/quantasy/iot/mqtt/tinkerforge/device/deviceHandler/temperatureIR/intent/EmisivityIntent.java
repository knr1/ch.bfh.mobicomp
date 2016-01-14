/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.TemperatureIR;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class EmisivityIntent extends AnIntent<TemperatureIR> {

    public EmisivityIntent(TemperatureIR deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "Emisivity");
	super.addDescription(TemperatureIR.EMISSIVITY, Integer.class, "JSON", "0", "..", "65535");

    }

    @Override
    public boolean isExecutable() {
	return isEmissivityInRange();

    }

    private boolean isEmissivityInRange() {
	short average = getContent(TemperatureIR.EMISSIVITY).getValue(Short.class);
	return average >= 0 && average <= 65535;
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }

}
