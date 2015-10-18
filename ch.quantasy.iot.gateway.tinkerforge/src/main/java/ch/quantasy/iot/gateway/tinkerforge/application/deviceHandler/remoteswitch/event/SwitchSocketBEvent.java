/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.event;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.intent.SwitchSocketBIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SwitchSocketBEvent extends AnEvent {

    public boolean switching;
    public long address;
    public short unit;
    public short switchTo;

    public SwitchSocketBEvent(ADeviceHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "switchSocketB", mqttClient);
	super.addTopicDefinition("address", "Long", "JSON", "0", "...", "67108863");
	super.addTopicDefinition("unit", "Short", "JSON", "0", "...", "15");
	super.addTopicDefinition("switchTo", "Short", "JSON", "0", "...", "1");
	super.addTopicDefinition("switching", "Boolean", "JSON", "true", "false");
    }

    public void updateIntent(SwitchSocketBIntent intent) {
	updateSwitching(intent.enabled);
	updateAddressCode(intent.address);
	updateUnit(intent.unit);
	updateSwitchTo(intent.switchTo);
    }

    public void updateAddressCode(long address) {
	if (this.address == address) {
	    return;
	} else {
	    this.address = address;
	    publishEvent("address", toJSONMQTTMessage(address));
	}

    }

    public void updateUnit(short unit) {
	if (this.unit == unit) {
	    return;
	} else {
	    this.unit = unit;
	    publishEvent("unit", toJSONMQTTMessage(unit));
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
