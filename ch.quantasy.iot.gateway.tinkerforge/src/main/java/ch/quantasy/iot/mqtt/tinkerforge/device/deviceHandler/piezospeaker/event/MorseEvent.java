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
public class MorseEvent extends AnEvent {

    public MorseEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "morse", mqttClient);
	super.addDescription(PiezoSpeaker.ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(PiezoSpeaker.CODE, String.class, "JSON", ".", "-", " ", "unbounded");
	super.addDescription(PiezoSpeaker.FREQUENCY, Integer.class, "JSON", "585", "...", "7100");
    }
}
