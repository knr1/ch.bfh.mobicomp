/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.segmentDisplay4x7.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.segmentDisplay4x7.SegmentDisplay4x7;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SegmentsEvent extends AnEvent {

    public SegmentsEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "segments", mqttClient);
	super.addDescription(SegmentDisplay4x7.ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(SegmentDisplay4x7.SEGMENTS, short[].class, "JSON", "0", "...", "127", "4");
	super.addDescription(SegmentDisplay4x7.BRIGHTNESS, Short.class, "JSON", "0", "...", "7");
	super.addDescription(SegmentDisplay4x7.COLON, Boolean.class, "JSON", "true", "false");
    }
}
