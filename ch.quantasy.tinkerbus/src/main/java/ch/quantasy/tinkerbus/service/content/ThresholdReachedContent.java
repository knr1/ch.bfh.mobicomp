/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.content;

import ch.quantasy.tinkerbus.service.device.threshold.CallbackThreshold;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ThresholdReachedContent extends ThresholdContent {

    private final boolean thresholdReached;

    public ThresholdReachedContent(CallbackThreshold callbackThreshold, boolean thresholdReached) {
	super(callbackThreshold);
	this.thresholdReached = thresholdReached;
    }

    public boolean isThresholdReached() {
	return thresholdReached;
    }

}
