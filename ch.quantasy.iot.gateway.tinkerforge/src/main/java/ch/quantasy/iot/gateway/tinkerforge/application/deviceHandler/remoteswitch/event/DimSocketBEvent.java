/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.event;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.intent.DimSocketBIntent;
import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DimSocketBEvent extends AnEvent {

    public boolean switching;
    public long address;
    public short unit;
    public short dimValue;

    public DimSocketBEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "dimSocketB", mqttClient);
	super.addTopicDefinition("address", "Long", "JSON", "0", "...", "67108863");
	super.addTopicDefinition("unit", "Short", "JSON", "0", "...", "15");
	super.addTopicDefinition("dimValue", "Short", "JSON", "0", "...", "15");
	super.addTopicDefinition("switching", "Boolean", "JSON", "true", "false");
    }

    public void updateIntent(DimSocketBIntent intent) {
	updateSwitching(intent.enabled);
	updateAddressCode(intent.address);
	updateUnit(intent.unit);
	updateDimValue(intent.dimValue);
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

    public void updateDimValue(short dimValue) {
	if (this.dimValue == dimValue) {
	    return;
	} else {
	    this.dimValue = dimValue;
	    publishEvent("dimValue", toJSONMQTTMessage(dimValue));
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
