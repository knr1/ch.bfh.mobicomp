/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TenantIntent {

    private final AnIntent intent;
    private final String tenant;

    public TenantIntent(AnIntent intent, String tenant) {
	this.intent = intent;
	this.tenant = tenant;
    }

    public void processMessage(String string, MqttMessage mm) {
//	intent.getIntentTopic() //wenn DIESER tnenent im Spiel ist, dann zur Intent weiter
    }

}
