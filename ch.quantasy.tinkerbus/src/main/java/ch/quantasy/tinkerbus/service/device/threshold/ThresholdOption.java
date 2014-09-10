/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.threshold;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public enum ThresholdOption {

    Off('x'), InsideMinMax('i'), OutsideMinMax('o'), LowerThanMin('<'), HigherThanMin('>');
    public final char option;

    private ThresholdOption(char option) {
	this.option = option;
    }

    public static CallbackThreshold createOffCallbackThreshold() {
	return new CallbackThreshold(Off.option, (short) 0, (short) 0);
    }

    public static CallbackThreshold createInsideCallbackThreshold(short min, short max) {
	return new CallbackThreshold(InsideMinMax.option, min, max);
    }

    public static CallbackThreshold createOutsideCallbackThreshold(short min, short max) {
	return new CallbackThreshold(OutsideMinMax.option, min, max);
    }

    public static CallbackThreshold createLowerThanCallbackThreshold(short value) {
	return new CallbackThreshold(LowerThanMin.option, value, (short) 0);
    }

    public static CallbackThreshold createHigherThanCallbackThreshold(short value) {
	return new CallbackThreshold(HigherThanMin.option, value, (short) 0);
    }

}
