/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ledstrip.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ledstrip.LedStrip;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ConfigStatus extends AStatus {

    public ConfigStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "config", mqttClient);
	super.addDescription(LedStrip.CONFIG_CHIP_TYPE, Integer.class, "JSON", "2801", "2811", "2812");
	super.addDescription(LedStrip.CONFIG_CLOCK_FREQUENCY_OF_ICS_IN_HZ, Long.class, "JSON", "10000", "...", "2000000");
	super.addDescription(LedStrip.CONFIG_FRAME_DURATION_IN_MILLISECONDS, Integer.class, "JSON", "1", "...", "" + Integer.MAX_VALUE);
	super.addDescription(LedStrip.CONFIG_NUMBER_OF_LEDS, Integer.class, "1", "...", "319");
    }

    public void updateChipType(int chipType) {
	if (super.getContent(LedStrip.CONFIG_CHIP_TYPE).updateContent(chipType)) {
	    publish(LedStrip.CONFIG_CHIP_TYPE, toJSONMQTTMessage(chipType));
	}
    }
}
