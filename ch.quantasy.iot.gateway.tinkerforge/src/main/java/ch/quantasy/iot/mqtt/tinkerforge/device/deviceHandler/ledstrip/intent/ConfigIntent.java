/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ledstrip.intent;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ledstrip.LedStrip;

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

    @Override
    public boolean isExecutable() {
	return getContent(LedStrip.CONFIG_ENABLED).getValue(Boolean.class) && isClockFrequencyOfICsInHzInRange() && isFrameDurationInMillisecondsInRange() && isNumberOfLEDsInRange();
    }

    private boolean isChipTypeInRange() {
	int chipType = getContent(LedStrip.CONFIG_CHIP_TYPE).getValue(Integer.class);
	return (chipType == 2801 || chipType == 2811 || chipType == 2812);
    }

    private boolean isClockFrequencyOfICsInHzInRange() {
	long clockFrequencyOfICsInHz = getContent(LedStrip.CONFIG_CLOCK_FREQUENCY_OF_ICS_IN_HZ).getValue(Long.class);
	return (clockFrequencyOfICsInHz >= 10000 && clockFrequencyOfICsInHz <= 2000000);
    }

    private boolean isFrameDurationInMillisecondsInRange() {
	int frameDurationInMilliseconds = getContent(LedStrip.CONFIG_FRAME_DURATION_IN_MILLISECONDS).getValue(Integer.class);
	return (frameDurationInMilliseconds >= 1);
    }

    private boolean isNumberOfLEDsInRange() {
	int numberOfLEDs = getContent(LedStrip.CONFIG_NUMBER_OF_LEDS).getValue(Integer.class);
	return (numberOfLEDs >= 1 && numberOfLEDs <= 319);
    }

}
