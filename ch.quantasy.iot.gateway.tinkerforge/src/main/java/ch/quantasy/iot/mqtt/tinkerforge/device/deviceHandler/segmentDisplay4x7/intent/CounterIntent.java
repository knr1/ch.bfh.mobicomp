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
public class CounterIntent extends AnIntent {

    public CounterIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "counter");
	super.addDescription(SegmentDisplay4x7.ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(SegmentDisplay4x7.LENGTH, Long.class, "JSON", "1", "...", "" + Long.MAX_VALUE);
	super.addDescription(SegmentDisplay4x7.FROM, Short.class, "JSON", "-999", "...", "9999");
	super.addDescription(SegmentDisplay4x7.TO, Short.class, "JSON", "-999", "...", "9999");
	super.addDescription(SegmentDisplay4x7.INCREMENT, Short.class, "JSON", "-999", "...", "9999");
    }

    public boolean isExecutable() {
	return isEnabled() && isToInRange() && isFromInRange() && isIncrementInRange();
    }

    public boolean isEnabled() {
	try {
	    return getContent(SegmentDisplay4x7.ENABLED).getValue(Boolean.class);
	} catch (Throwable th) {
	    return false;
	}
    }

    private boolean isToInRange() {
	short value = getContent(SegmentDisplay4x7.TO).getValue(Short.class);
	return (value >= -999 && value <= 9999);
    }

    private boolean isFromInRange() {
	short value = getContent(SegmentDisplay4x7.FROM).getValue(Short.class);
	return (value >= -999 && value <= 9999);
    }

    private boolean isIncrementInRange() {
	short value = getContent(SegmentDisplay4x7.INCREMENT).getValue(Short.class);
	return (value >= -999 && value <= 9999);
    }

    private boolean isLengthInRange() {
	long duration = getContent(SegmentDisplay4x7.LENGTH).getValue(Long.class);
	return duration > 0;
    }
}
