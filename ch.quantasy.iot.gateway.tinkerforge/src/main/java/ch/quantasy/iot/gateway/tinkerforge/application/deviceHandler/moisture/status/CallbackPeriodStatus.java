/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.moisture.status;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.moisture.intent.CallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CallbackPeriodStatus extends AStatus {

    public long period;

    public CallbackPeriodStatus(AHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "callbackPeriod", mqttClient);
	super.addTopicDescription("period", "Long", "JSON", "0", "..", "" + Long.MAX_VALUE);
    }

    public void updateIntent(CallbackPeriodIntent intent) {
	updateCallbackPeriod(intent.period);
    }

    public void updateCallbackPeriod(long period) {
	if (this.period == period) {
	    return;
	} else {
	    this.period = period;
	    publishStatus("period", toJSONMQTTMessage(period));
	}
	return;
    }
}
