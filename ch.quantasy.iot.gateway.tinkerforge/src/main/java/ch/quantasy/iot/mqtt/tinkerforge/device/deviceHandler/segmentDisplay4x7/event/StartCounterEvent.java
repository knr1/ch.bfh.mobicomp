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
public class StartCounterEvent extends AnEvent {

    public StartCounterEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "counterStarted", mqttClient);
	super.addDescription(SegmentDisplay4x7.LENGTH, Long.class, "JSON", "1", "...", "" + Long.MAX_VALUE);
	super.addDescription(SegmentDisplay4x7.FROM, Short.class, "JSON", "-999", "...", "9999");
	super.addDescription(SegmentDisplay4x7.TO, Short.class, "JSON", "-999", "...", "9999");
	super.addDescription(SegmentDisplay4x7.INCREMENT, Short.class, "JSON", "-999", "...", "9999");
    }
}
