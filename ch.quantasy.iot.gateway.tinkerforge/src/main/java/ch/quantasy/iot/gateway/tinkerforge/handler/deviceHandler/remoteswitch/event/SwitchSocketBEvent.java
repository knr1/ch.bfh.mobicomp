/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.RemoteSwitch;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.intent.SwitchSocketBIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SwitchSocketBEvent extends AnEvent {

    public SwitchSocketBEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "switchSocketB", mqttClient);
	super.addDescription(RemoteSwitch.ADDRESS, Long.class, "JSON", "0", "...", "67108863");
	super.addDescription(RemoteSwitch.UNIT, Short.class, "JSON", "0", "...", "15");
	super.addDescription(RemoteSwitch.SWITCH_TO, Short.class, "JSON", "0", "...", "1");
	super.addDescription(RemoteSwitch.SWITCHING, Boolean.class, "JSON", "true", "false");
    }

    public void updateIntent(SwitchSocketBIntent intent) {
	update(RemoteSwitch.SWITCHING, intent.getContent(RemoteSwitch.ENABLED).getValue(Boolean.class));
	super.updateIntent(intent);
    }
}
