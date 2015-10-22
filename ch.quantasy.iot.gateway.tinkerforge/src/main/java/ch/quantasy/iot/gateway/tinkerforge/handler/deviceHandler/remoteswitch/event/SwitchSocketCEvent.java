/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.event;

import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.intent.SwitchSocketCIntent;
import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
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

    public SwitchSocketCEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "switchSocketC", mqttClient);
	super.addTopicDescription("systemCode", "Character", "JSON", "A", "...", "P");
	super.addTopicDescription("deviceCode", "Short", "JSON", "1", "...", "16");
	super.addTopicDescription("switchTo", "Short", "JSON", "0", "...", "1");
	super.addTopicDescription("switching", "Boolean", "JSON", "true", "false");
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
