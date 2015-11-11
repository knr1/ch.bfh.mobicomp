/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.RemoteSwitch;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.intent.SwitchSocketCIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SwitchSocketCEvent extends AnEvent {

    public SwitchSocketCEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "switchSocketC", mqttClient);
	super.addDescription(RemoteSwitch.SYSTEM_CODE, Character.class, "JSON", "A", "...", "P");
	super.addDescription(RemoteSwitch.DEVICE_CODE, Short.class, "JSON", "1", "...", "16");
	super.addDescription(RemoteSwitch.SWITCH_TO, Short.class, "JSON", "0", "...", "1");
	super.addDescription(RemoteSwitch.SWITCHING, Boolean.class, "JSON", "true", "false");
    }

    public void updateIntent(SwitchSocketCIntent intent) {
	update(RemoteSwitch.SWITCHING, intent.getContent(RemoteSwitch.ENABLED).getValue(Boolean.class));
	super.update(intent);
    }
}
