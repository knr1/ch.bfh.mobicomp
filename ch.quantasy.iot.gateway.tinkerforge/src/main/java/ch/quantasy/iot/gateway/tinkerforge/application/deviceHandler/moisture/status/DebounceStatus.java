/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.moisture.status;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.moisture.intent.DebouncePeriodIntent;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DebounceStatus extends AStatus {

    public long period;

    public DebounceStatus(ADeviceHandler deviceHandler, String statusTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, statusTopic, "debounce", mqttClient);
	super.addTopicDescription("period", "Long", "JSON", "0", "..", "" + Long.MAX_VALUE);
    }

    public void updateIntent(DebouncePeriodIntent intent) {
	updateDebounceState(intent.period);
    }

    public void updateDebounceState(long period) {
	if (this.period == period) {
	    return;
	} else {
	    this.period = period;
	    publishStatus("period", toJSONMQTTMessage(period));
	}
	return;
    }
}
