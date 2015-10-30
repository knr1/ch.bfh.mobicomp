/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.RemoteSwitch;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.intent.SwitchSocketAIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SwitchSocketAEvent extends AnEvent {

    public SwitchSocketAEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "switchSocketA", mqttClient);
	super.addDescription(RemoteSwitch.HOUSE_CODE, Short.class, "JSON", "0", "...", "31");
	super.addDescription(RemoteSwitch.RECEIVER_CODE, Short.class, "JSON", "0", "...", "31");
	super.addDescription(RemoteSwitch.SWITCH_TO, Short.class, "JSON", "0", "...", "1");
	super.addDescription(RemoteSwitch.SWITCHING, Boolean.class, "JSON", "true", "false");
    }

    public void updateIntent(SwitchSocketAIntent intent) {
	update(RemoteSwitch.SWITCHING, intent.getContent(RemoteSwitch.ENABLED).getValue(Boolean.class));
	super.updateIntent(intent);
    }
}
