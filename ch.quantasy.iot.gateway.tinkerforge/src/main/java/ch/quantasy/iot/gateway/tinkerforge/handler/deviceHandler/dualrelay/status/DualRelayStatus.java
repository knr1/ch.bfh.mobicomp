/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay.status;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import com.tinkerforge.BrickletDualRelay;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DualRelayStatus extends AStatus {

    public boolean relay1;
    public boolean relay2;

    public DualRelayStatus(AHandler deviceHandler, String topic, MqttAsyncClient mqttClient) {
	super(deviceHandler, topic, "dualRelay", mqttClient);
	super.addTopicDescription("relay1", "Boolean", "JSON", "true", "false");
	super.addTopicDescription("relay2", "Boolean", "JSON", "true", "false");
    }

    public void updateState(BrickletDualRelay.State state) {
	updateRelay1(state.relay1);
	updateRelay2(state.relay2);
    }

    public void updateRelay1(boolean relay) {
	if (this.relay1 == relay) {
	    return;
	} else {
	    this.relay1 = relay;
	    publishStatus("relay1", toJSONMQTTMessage(relay1));
	}
    }

    public void updateRelay2(boolean relay) {
	if (this.relay2 == relay) {
	    return;
	} else {
	    this.relay2 = relay;
	    publishStatus("relay2", toJSONMQTTMessage(relay2));
	}
    }
}
