/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualButton.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualButton.DualButton;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class StateChangedEvent extends AnEvent {

    public StateChangedEvent(AHandler deviceHandler, String topic, MqttAsyncClient mqttClient) {
	super(deviceHandler, topic, "dualButton", mqttClient);
	super.addDescription(DualButton.BUTTON1, Short.class, "JSON", "0", "1");
	super.addDescription(DualButton.BUTTON2, Short.class, "JSON", "0", "1");
	super.addDescription(DualButton.LED_L, Short.class, "JSON", "0", "1", "2", "3");
	super.addDescription(DualButton.LED_R, Short.class, "JSON", "0", "1", "2", "3");
    }
}
