/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.segmentDisplay4x7.intent;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.segmentDisplay4x7.SegmentDisplay4x7;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SegmentsIntent extends AnIntent {
//public void setSegments(short[] segments, short brightness, boolean colon)

    public SegmentsIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "segments");
	super.addDescription(SegmentDisplay4x7.ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(SegmentDisplay4x7.SEGMENTS, short[].class, "JSON", "0", "...", "127", "4");
	super.addDescription(SegmentDisplay4x7.BRIGHTNESS, Short.class, "JSON", "0", "...", "7");
	super.addDescription(SegmentDisplay4x7.COLON, Boolean.class, "JSON", "true", "false");
    }

    @Override
    public boolean isExecutable() {
	return isEnabled() && isBrightnessInRange() && isColonInRange() && isSegmentsInRange();
    }

    public boolean isEnabled() {
	try {
	    return getContent(SegmentDisplay4x7.ENABLED).getValue(Boolean.class);
	} catch (Throwable th) {
	    return false;
	}
    }

    private boolean isSegmentsInRange() {
	short[] values = getContent(SegmentDisplay4x7.SEGMENTS).getValue(short[].class);
	return values != null;
    }

    private boolean isColonInRange() {
	boolean value = getContent(SegmentDisplay4x7.COLON).getValue(Boolean.class);
	return value | true;
    }

    private boolean isBrightnessInRange() {
	short value = getContent(SegmentDisplay4x7.BRIGHTNESS).getValue(Short.class);
	return (value >= 0 && value <= 7);
    }

}
