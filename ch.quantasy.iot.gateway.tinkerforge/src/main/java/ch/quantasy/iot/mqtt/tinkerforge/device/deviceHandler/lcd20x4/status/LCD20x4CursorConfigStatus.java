/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.LCD20x4;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LCD20x4CursorConfigStatus extends AStatus {

    public LCD20x4CursorConfigStatus(AHandler deviceHandler, String topic, MqttAsyncClient mqttClient) {
	super(deviceHandler, topic, "cursorConfig", mqttClient);
	super.addDescription(LCD20x4.CURSOR_ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(LCD20x4.BLINKING, Boolean.class, "JSON", "true", "false");
	super.addDescription(LCD20x4.ENABLED, Boolean.class, "JSON", "true", "false");

    }
}
