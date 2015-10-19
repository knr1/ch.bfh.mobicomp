/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.ledstrip.status;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.ledstrip.intent.ConfigIntent;
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

    public ConfigStatus(ADeviceHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "config", mqttClient);
	super.addTopicDescription("chipType", "Integer", "JSON", "2801", "2811", "2812");
	super.addTopicDescription("clockFrequencyOfICsInHz", "Long", "JSON", "10000", "...", "2000000");
	super.addTopicDescription("frameDurationInMilliseconds", "Integer", "JSON", "1", "...", "" + Integer.MAX_VALUE);
	super.addTopicDescription("numberOfLEDs", "Integer", "1", "...", "319");
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
	    publishStatus("chipType", toJSONMQTTMessage(chipType));
	}
    }

    public void updateClockFrequencyOfICsInHz(Long clockFrequencyOfICsInHz) {
	if (this.clockFrequencyOfICsInHz == clockFrequencyOfICsInHz) {
	    return;
	} else {
	    this.clockFrequencyOfICsInHz = clockFrequencyOfICsInHz;
	    publishStatus("clockFrequencyOfICsInHz", toJSONMQTTMessage(clockFrequencyOfICsInHz));
	}
	return;
    }

    public void updateFrameDurationInMilliseconds(int frameDurationInMilliseconds) {
	if (this.frameDurationInMilliseconds == frameDurationInMilliseconds) {
	    return;
	} else {
	    this.frameDurationInMilliseconds = frameDurationInMilliseconds;
	    publishStatus("frameDurationInMilliseconds", toJSONMQTTMessage(frameDurationInMilliseconds));
	}
	return;
    }

    public void updateNumberOfLEDs(int numberOfLEDs) {
	if (this.numberOfLEDs == numberOfLEDs) {
	    return;
	} else {
	    this.numberOfLEDs = numberOfLEDs;
	    publishStatus("numberOfLEDs", toJSONMQTTMessage(numberOfLEDs));
	}
	return;
    }
}
