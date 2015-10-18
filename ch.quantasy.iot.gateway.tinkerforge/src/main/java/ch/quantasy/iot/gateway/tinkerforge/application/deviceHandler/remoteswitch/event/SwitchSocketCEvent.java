/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.event;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.intent.SwitchSocketCIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SwitchSocketCEvent extends AnEvent {

    public boolean switching;
    public boolean enabled;
    public char systemCode;
    public short deviceCode;
    public short switchTo;

    public SwitchSocketCEvent(ADeviceHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "switchSocketC", mqttClient);
	super.addTopicDefinition("systemCode", "Character", "JSON", "A", "...", "P");
	super.addTopicDefinition("deviceCode", "Short", "JSON", "1", "...", "16");
	super.addTopicDefinition("switchTo", "Short", "JSON", "0", "...", "1");
	super.addTopicDefinition("switching", "Boolean", "JSON", "true", "false");
    }

    public void updateIntent(SwitchSocketCIntent intent) {
	updateSwitching(intent.enabled);
	updateSystemCode(intent.systemCode);
	updateDeviceCode(intent.deviceCode);
	updateSwitchTo(intent.switchTo);
    }

    public void updateSystemCode(char systemCode) {
	if (this.systemCode == systemCode) {
	    return;
	} else {
	    this.systemCode = systemCode;
	    publishEvent("systemCode", toJSONMQTTMessage(systemCode));
	}

    }

    public void updateDeviceCode(short deviceCode) {
	if (this.deviceCode == deviceCode) {
	    return;
	} else {
	    this.deviceCode = deviceCode;
	    publishEvent("deviceCode", toJSONMQTTMessage(deviceCode));
	}
    }

    public void updateSwitchTo(short switchTo) {
	if (this.switchTo == switchTo) {
	    return;
	} else {
	    this.switchTo = switchTo;
	    publishEvent("switchTo", toJSONMQTTMessage(switchTo));
	}
    }

    public void updateSwitching(boolean switching) {
	if (this.switching == switching) {
	    return;
	} else {
	    this.switching = switching;
	    publishEvent("switching", toJSONMQTTMessage(switching));
	}
	return;
    }
}
