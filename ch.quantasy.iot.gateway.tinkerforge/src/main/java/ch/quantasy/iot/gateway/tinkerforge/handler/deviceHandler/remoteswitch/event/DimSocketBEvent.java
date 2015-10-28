/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.intent.DimSocketBIntent;
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
	super.addDescription("address", Long.class, "JSON", "0", "...", "67108863");
	super.addDescription("unit", Short.class, "JSON", "0", "...", "15");
	super.addDescription("dimValue", Short.class, "JSON", "0", "...", "15");
	super.addDescription("switching", Boolean.class, "JSON", "true", "false");
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
	    publish("address", toJSONMQTTMessage(address));
	}

    }

    public void updateUnit(short unit) {
	if (this.unit == unit) {
	    return;
	} else {
	    this.unit = unit;
	    publish("unit", toJSONMQTTMessage(unit));
	}
    }

    public void updateDimValue(short dimValue) {
	if (this.dimValue == dimValue) {
	    return;
	} else {
	    this.dimValue = dimValue;
	    publish("dimValue", toJSONMQTTMessage(dimValue));
	}
    }

    public void updateSwitching(boolean switching) {
	if (this.switching == switching) {
	    return;
	} else {
	    this.switching = switching;
	    publish("switching", toJSONMQTTMessage(switching));
	}
	return;
    }
}
