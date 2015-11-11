/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.color.status;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.color.Color;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ColorCallbackThresholdStatus extends AStatus {

    public ColorCallbackThresholdStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "colorCallbackThreshold", mqttClient);
	super.addDescription(Color.THRESHOLD_OPTION, Character.class, "JSON", "x", "o", "i", "\\<", "\\>");
	super.addDescription(Color.THRESHOLD_MIN_R, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MAX_R, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MIN_G, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MAX_G, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MIN_B, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MAX_B, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MIN_C, Integer.class, "JSON", "0", "...", "65535");
	super.addDescription(Color.THRESHOLD_MAX_C, Integer.class, "JSON", "0", "...", "65535");
    }
}
