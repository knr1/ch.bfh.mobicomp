/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MultiTenantIntentHandler {

    private final Map<String, TenantIntent> tenantIntentMap;

    public MultiTenantIntentHandler() {
	this.tenantIntentMap = new HashMap<>();
    }

    public void processMessage(String string, MqttMessage mm) {
//	String tenant = string.replace(intent.getIntentTopic(), "");
//	tenant = tenant.replace(intent, tenant)
//	intent.getIntentTopic() //wenn DIESER tnenent im Spiel ist, dann zur Intent weiter
    }
}
