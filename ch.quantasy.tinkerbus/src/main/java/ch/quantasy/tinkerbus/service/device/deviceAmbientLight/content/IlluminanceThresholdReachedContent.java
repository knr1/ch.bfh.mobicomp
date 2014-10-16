/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceAmbientLight.content;

import ch.quantasy.tinkerbus.service.content.ThresholdReachedContent;
import ch.quantasy.tinkerbus.service.device.threshold.CallbackThresholdReached;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class IlluminanceThresholdReachedContent extends ThresholdReachedContent {

    public IlluminanceThresholdReachedContent(CallbackThresholdReached value) {
	super(value);
    }

}
