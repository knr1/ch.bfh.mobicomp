/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.event;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.RemoteSwitch;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DimSocketBEvent extends AnEvent {

    public DimSocketBEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "dimSocketB", mqttClient);
	super.addDescription(RemoteSwitch.ADDRESS, Long.class, "JSON", "0", "...", "67108863");
	super.addDescription(RemoteSwitch.UNIT, Short.class, "JSON", "0", "...", "15");
	super.addDescription(RemoteSwitch.DIM_VALUE, Short.class, "JSON", "0", "...", "15");
	super.addDescription(RemoteSwitch.SWITCHING, Boolean.class, "JSON", "true", "false");
    }

//    public void updateIntent(DimSocketBIntent intent) {
//	//update(RemoteSwitch.SWITCHING, intent.getContent(RemoteSwitch.ENABLED).getValue(Boolean.class));
//	super.update(intent);
//    }
}
