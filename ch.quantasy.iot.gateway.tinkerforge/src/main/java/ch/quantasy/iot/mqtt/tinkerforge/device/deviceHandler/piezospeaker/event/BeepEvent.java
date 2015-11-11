/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.PiezoSpeaker;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class BeepEvent extends AnEvent {

    public BeepEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "beep", mqttClient);
	super.addDescription(PiezoSpeaker.ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(PiezoSpeaker.DURATION, Long.class, "JSON", "1", "...", "" + Long.MAX_VALUE);
	super.addDescription(PiezoSpeaker.FREQUENCY, Integer.class, "JSON", "585", "...", "7100");
    }
}
