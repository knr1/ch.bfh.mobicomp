/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceLED.content;

import ch.quantasy.tinkerbus.service.content.ValueContent;
import com.tinkerforge.BrickletLEDStrip;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ChipTypeContent extends ValueContent<Integer> {

    public static final int WS2801 = BrickletLEDStrip.CHIP_TYPE_WS2801;
    public static final int WS2811 = BrickletLEDStrip.CHIP_TYPE_WS2811;
    public static final int WS2812 = BrickletLEDStrip.CHIP_TYPE_WS2812;

    public ChipTypeContent(Integer value) {
	super(value);
    }

}
