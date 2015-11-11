/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.color.intent;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.color.Color;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ColorCallbackThresholdIntent extends AnIntent {

    public ColorCallbackThresholdIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "colorCallbackThreshold");
	super.addDescription(Color.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(Color.THRESHOLD_MIN_R, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MAX_R, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MIN_G, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MAX_G, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MIN_B, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MAX_B, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MIN_C, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MAX_C, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.ENABLED, Boolean.class, "JSON", "true", "false");
    }
    //    char option, int minR, int maxR, int minG, int maxG, int minB, int maxB, int minC, int maxC

    public boolean isExecutable() {
	return isEnabled() && isOptionInRange() && isMaxRInRange() && isMaxRInRange() && isMaxGInRange() && isMaxGInRange() && isMaxBInRange() && isMaxBInRange() && isMaxCInRange() && isMaxCInRange();
    }

    private boolean isEnabled() {
	return getContent(Color.ENABLED).getValue(Boolean.class);
    }

    private boolean isOptionInRange() {
	char option = getContent(Color.THRESHOLD_OPTION).getValue(Character.class);
	switch (option) {
	    case 'x':
	    case 'o':
	    case 'i':
	    case '<': //<
	    case '>': //>
		return true;
	}
	return false;
    }

    private boolean isMinRInRange() {
	int min = getContent(Color.THRESHOLD_MIN_R).getValue(Integer.class);
	return (min <= 65535 && min >= 0);
    }

    private boolean isMaxRInRange() {
	int max = getContent(Color.THRESHOLD_MAX_R).getValue(Integer.class);
	return (max <= 65535 && max >= 0);
    }

    private boolean isMinGInRange() {
	int min = getContent(Color.THRESHOLD_MIN_G).getValue(Integer.class);
	return (min <= 65535 && min >= 0);
    }

    private boolean isMaxGInRange() {
	int max = getContent(Color.THRESHOLD_MAX_G).getValue(Integer.class);
	return (max <= 65535 && max >= 0);
    }

    private boolean isMinBInRange() {
	int min = getContent(Color.THRESHOLD_MIN_B).getValue(Integer.class);
	return (min <= 65535 && min >= 0);
    }

    private boolean isMaxBInRange() {
	int max = getContent(Color.THRESHOLD_MAX_B).getValue(Integer.class);
	return (max <= 65535 && max >= 0);
    }

    private boolean isMinCInRange() {
	int min = getContent(Color.THRESHOLD_MIN_C).getValue(Integer.class);
	return (min <= 65535 && min >= 0);
    }

    private boolean isMaxCInRange() {
	int max = getContent(Color.THRESHOLD_MAX_C).getValue(Integer.class);
	return (max <= 65535 && max >= 0);
    }

}
