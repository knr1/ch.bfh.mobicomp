/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ledstrip.status;

import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ledstrip.intent.ConfigIntent;
import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ConfigStatus extends AStatus {

    public int chipType;
    public Long clockFrequencyOfICsInHz;
    public int frameDurationInMilliseconds;
    public int numberOfLEDs;

    public ConfigStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "config", mqttClient);
	super.addDescription("chipType", "Integer", "JSON", "2801", "2811", "2812");
	super.addDescription("clockFrequencyOfICsInHz", "Long", "JSON", "10000", "...", "2000000");
	super.addDescription("frameDurationInMilliseconds", "Integer", "JSON", "1", "...", "" + Integer.MAX_VALUE);
	super.addDescription("numberOfLEDs", "Integer", "1", "...", "319");
    }

    public void updateConfig(ConfigIntent intent) {
	updateClockFrequencyOfICsInHz(intent.clockFrequencyOfICsInHz);
	updateFrameDurationInMilliseconds(intent.frameDurationInMilliseconds);
	updateNumberOfLEDs(intent.numberOfLEDs);
    }

    public void updateChipType(int chipType) {
	if (this.chipType == chipType) {
	    return;
	} else {
	    this.chipType = chipType;
	    publish("chipType", toJSONMQTTMessage(chipType));
	}
    }

    public void updateClockFrequencyOfICsInHz(Long clockFrequencyOfICsInHz) {
	if (this.clockFrequencyOfICsInHz == clockFrequencyOfICsInHz) {
	    return;
	} else {
	    this.clockFrequencyOfICsInHz = clockFrequencyOfICsInHz;
	    publish("clockFrequencyOfICsInHz", toJSONMQTTMessage(clockFrequencyOfICsInHz));
	}
	return;
    }

    public void updateFrameDurationInMilliseconds(int frameDurationInMilliseconds) {
	if (this.frameDurationInMilliseconds == frameDurationInMilliseconds) {
	    return;
	} else {
	    this.frameDurationInMilliseconds = frameDurationInMilliseconds;
	    publish("frameDurationInMilliseconds", toJSONMQTTMessage(frameDurationInMilliseconds));
	}
	return;
    }

    public void updateNumberOfLEDs(int numberOfLEDs) {
	if (this.numberOfLEDs == numberOfLEDs) {
	    return;
	} else {
	    this.numberOfLEDs = numberOfLEDs;
	    publish("numberOfLEDs", toJSONMQTTMessage(numberOfLEDs));
	}
	return;
    }
}
