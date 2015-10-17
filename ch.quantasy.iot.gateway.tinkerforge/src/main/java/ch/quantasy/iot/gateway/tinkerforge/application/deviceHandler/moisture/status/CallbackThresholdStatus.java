/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.moisture.status;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.moisture.intent.CallbackThresholdIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CallbackThresholdStatus extends AStatus {

    public char option;
    public int min;
    public int max;

    public CallbackThresholdStatus(ADeviceHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "callbackThreshold", mqttClient);
	super.addStatusTopicDescription("option", "Character", "JSON", "x", "o", "i", "\\<", "\\>");
	super.addStatusTopicDescription("min", "Integer", "JSON", "0", "...", "4095");
	super.addStatusTopicDescription("max", "Integer", "JSON", "0", "...", "4095");
    }

    public void updateIntent(CallbackThresholdIntent intent) {
	updateCallbackOption(intent.option);
	updateCallbackMin(intent.max);
	updateCallbackMax(intent.min);
    }

    public void updateCallbackOption(char option) {
	if (this.option == option) {
	    return;
	} else {
	    this.option = option;
	    publishStatus("option", toJSONMQTTMessage(option));
	}
	return;
    }

    public void updateCallbackMin(int min) {
	if (this.min == min) {
	    return;
	} else {
	    this.min = min;
	    publishStatus("min", toJSONMQTTMessage(min));
	}
	return;
    }

    public void updateCallbackMax(int max) {
	if (this.max == max) {
	    return;
	} else {
	    this.max = max;
	    publishStatus("max", toJSONMQTTMessage(max));
	}
	return;
    }
}
