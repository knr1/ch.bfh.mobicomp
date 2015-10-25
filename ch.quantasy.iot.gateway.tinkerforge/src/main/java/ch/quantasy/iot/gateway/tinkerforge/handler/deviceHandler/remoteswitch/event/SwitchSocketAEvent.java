/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.event;

import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.intent.SwitchSocketAIntent;
import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SwitchSocketAEvent extends AnEvent {

    public boolean switching;
    public short houseCode;
    public short receiverCode;
    public short switchTo;

    public SwitchSocketAEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "switchSocketA", mqttClient);
	super.addDescription("houseCode", "Short", "JSON", "0", "...", "31");
	super.addDescription("receiverCode", "Short", "JSON", "0", "...", "31");
	super.addDescription("switchTo", "Short", "JSON", "0", "...", "1");
	super.addDescription("switching", "Boolean", "JSON", "true", "false");
    }

    public void updateIntent(SwitchSocketAIntent intent) {
	updateSwitching(intent.enabled);
	updateHouseCode(intent.houseCode);
	updateReceiverCode(intent.receiverCode);
	updateSwitchTo(intent.switchTo);
    }

    public void updateHouseCode(short houseCode) {
	if (this.houseCode == houseCode) {
	    return;
	} else {
	    this.houseCode = houseCode;
	    publish("houseCode", toJSONMQTTMessage(houseCode));
	}

    }

    public void updateReceiverCode(short receiverCode) {
	if (this.receiverCode == receiverCode) {
	    return;
	} else {
	    this.receiverCode = receiverCode;
	    publish("receiverCode", toJSONMQTTMessage(receiverCode));
	}
    }

    public void updateSwitchTo(short switchTo) {
	if (this.switchTo == switchTo) {
	    return;
	} else {
	    this.switchTo = switchTo;
	    publish("switchTo", toJSONMQTTMessage(switchTo));
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
