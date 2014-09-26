/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceLED;

import ch.quantasy.tinkerbus.service.device.core.TinkerforgeDeviceSetting;
import com.tinkerforge.BrickletLEDStrip;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeLEDSetting extends TinkerforgeDeviceSetting<TinkerforgeLEDSetting> {

    public final static int NUMBER_OF_COLOR_CHANNELS = 3;

    public static final int DEFAULT_NUMBER_OF_LEDS = 40;

    // Frame duration in Milliseconds 20ms (1000ms / 50frames = 20ms per frame)
    public static final int DEFAULT_FRAME_DURATION_IN_MILLISECONDS = 20;

    // IC-refresh in Hz
    public static final int DEFAULT_CLOCK_FREQUENCY_OF_ICS_IN_HZ = 2000000;

    public static final int CHIP_TYPE_WS2801 = BrickletLEDStrip.CHIP_TYPE_WS2801;
    public static final int CHIP_TYPE_WS2812 = BrickletLEDStrip.CHIP_TYPE_WS2812;

    private Integer frameDurationInMilliseconds;
    private Integer clockFrequencyOfICsInHz;
    private Integer chipType;
    private Integer numberOfLEDs;
    private Boolean gammaCorrection;

    public void setNumberOfLEDs(Integer numberOfLEDs) {
	if ((numberOfLEDs < 0) || (numberOfLEDs > 320)) {
	    throw new IllegalArgumentException();
	}
	this.numberOfLEDs = numberOfLEDs;
    }

    public Integer getNumberOfLEDs() {
	return this.numberOfLEDs;
    }

    public void setFrameDurationInMilliseconds(
	    Integer frameDurationInMilliseconds) {
	if (frameDurationInMilliseconds < 1) {
	    throw new IllegalArgumentException();
	}
	this.frameDurationInMilliseconds = frameDurationInMilliseconds;
    }

    public Integer getFrameDurationInMilliseconds() {
	return frameDurationInMilliseconds;
    }

    public void setClockFrequencyOfICsInHz(Integer clockFrequencyOfICsInHz) {
	if (clockFrequencyOfICsInHz < 1) {
	    throw new IllegalArgumentException();
	}
	this.clockFrequencyOfICsInHz = clockFrequencyOfICsInHz;
    }

    public Integer getClockFrequencyOfICsInHz() {
	return clockFrequencyOfICsInHz;
    }

    public void setChipType(Integer chipType) {
	if (!(chipType == BrickletLEDStrip.CHIP_TYPE_WS2801
		|| chipType == BrickletLEDStrip.CHIP_TYPE_WS2811
		|| chipType == BrickletLEDStrip.CHIP_TYPE_WS2812)) {
	    throw new IllegalArgumentException();
	}

	this.chipType = chipType;
    }

    public Integer getChipType() {
	return chipType;
    }

    public void setGammaCorrection(Boolean gammaCorrection) {
	this.gammaCorrection = gammaCorrection;
    }

    public Boolean isGammaCorrection() {
	return gammaCorrection != null && gammaCorrection;
    }

    @Override
    public String toString() {
	return "TinkerforgeLEDSetting{" + "frameDurationInMilliseconds=" + frameDurationInMilliseconds + ", clockFrequencyOfICsInHz=" + clockFrequencyOfICsInHz + ", chipType=" + chipType + ", numberOfLEDs=" + numberOfLEDs + ", gammaCorrection=" + gammaCorrection + '}';
    }

}
