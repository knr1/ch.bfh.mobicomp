/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.content;

import ch.quantasy.messagebus.message.implementation.AContent;
import ch.quantasy.tinkerbus.service.device.threshold.CallbackThreshold;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ThresholdContent extends AContent {

    private final CallbackThreshold callbackThreshold;

    public ThresholdContent(CallbackThreshold callbackThreshold) {
	this.callbackThreshold = callbackThreshold;
    }

    public CallbackThreshold getCallbackThreshold() {
	return callbackThreshold;
    }

}
