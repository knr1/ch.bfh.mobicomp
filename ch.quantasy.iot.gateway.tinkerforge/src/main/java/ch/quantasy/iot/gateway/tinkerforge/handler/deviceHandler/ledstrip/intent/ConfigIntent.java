/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ledstrip.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ledstrip.LedStrip;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ConfigIntent extends AnIntent {

    public ConfigIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "config");
	super.addDescription(LedStrip.CONFIG_CHIP_TYPE, Integer.class, "JSON", "2801", "2811", "2812");
	super.addDescription(LedStrip.CONFIG_ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(LedStrip.CONFIG_CLOCK_FREQUENCY_OF_ICS_IN_HZ, Long.class, "JSON", "10000", "...", "2000000");
	super.addDescription(LedStrip.CONFIG_FRAME_DURATION_IN_MILLISECONDS, Integer.class, "JSON", "1", "...", "" + Integer.MAX_VALUE);
	super.addDescription(LedStrip.CONFIG_NUMBER_OF_LEDS, Integer.class, "1", "...", "319");

    }

//    @Override
//    protected void update(String string, MqttMessage mm) throws Throwable {
//	if (string.endsWith(getName() + "/enabled")) {
//	    enabled = fromMQTTMessage(mm, Boolean.class);
//	}
//	if (string.endsWith(getName() + "/chipType")) {
//	    chipType = fromMQTTMessage(mm, Integer.class);
//	}
//	if (string.endsWith(getName() + "/clockFrequencyOfICsInHz")) {
//	    clockFrequencyOfICsInHz = fromMQTTMessage(mm, Long.class);
//	}
//	if (string.endsWith(getName() + "/frameDurationInMilliseconds")) {
//	    frameDurationInMilliseconds = fromMQTTMessage(mm, Integer.class);
//	}
//	if (string.endsWith(getName() + "/numberOfLEDs")) {
//	    numberOfLEDs = fromMQTTMessage(mm, Integer.class);
//	}
//
//    }
    @Override
    public boolean isExecutable() {
	return getTriple(LedStrip.CONFIG_ENABLED).getValue(Boolean.class) && isClockFrequencyOfICsInHzInRange() && isFrameDurationInMillisecondsInRange() && isNumberOfLEDsInRange();
    }

    private boolean isChipTypeInRange() {
	int chipType = getTriple(LedStrip.CONFIG_CHIP_TYPE).getValue(Integer.class);
	return (chipType == 2801 || chipType == 2811 || chipType == 2812);
    }

    private boolean isClockFrequencyOfICsInHzInRange() {
	long clockFrequencyOfICsInHz = getTriple(LedStrip.CONFIG_CLOCK_FREQUENCY_OF_ICS_IN_HZ).getValue(Long.class);
	return (clockFrequencyOfICsInHz >= 10000 && clockFrequencyOfICsInHz <= 2000000);
    }

    private boolean isFrameDurationInMillisecondsInRange() {
	int frameDurationInMilliseconds = getTriple(LedStrip.CONFIG_FRAME_DURATION_IN_MILLISECONDS).getValue(Integer.class);
	return (frameDurationInMilliseconds >= 1);
    }

    private boolean isNumberOfLEDsInRange() {
	int numberOfLEDs = getTriple(LedStrip.CONFIG_NUMBER_OF_LEDS).getValue(Integer.class);
	return (numberOfLEDs >= 1 && numberOfLEDs <= 319);
    }

}
