/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay.status;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay.DualRelay;
import com.tinkerforge.BrickletDualRelay;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DualRelayStatus extends AStatus {

    public DualRelayStatus(AHandler deviceHandler, String topic, MqttAsyncClient mqttClient) {
	super(deviceHandler, topic, "dualRelay", mqttClient);
	super.addDescription(DualRelay.DUALRELAY_RELAY1, Boolean.class, "JSON", "true", "false");
	super.addDescription(DualRelay.DUALRELAY_RELAY2, Boolean.class, "JSON", "true", "false");
    }

    public void updateState(BrickletDualRelay.State state) {
	updateRelay1(state.relay1);
	updateRelay2(state.relay2);
    }

    public void updateRelay1(boolean relay) {
	if (super.getTriple(DualRelay.DUALRELAY_RELAY1).updateContent(relay)) {
	    publish(DualRelay.DUALRELAY_RELAY1, toJSONMQTTMessage(relay));
	}
    }

    public void updateRelay2(boolean relay) {
	if (super.getTriple(DualRelay.DUALRELAY_RELAY2).updateContent(relay)) {
	    publish(DualRelay.DUALRELAY_RELAY2, toJSONMQTTMessage(relay));
	}
    }
}
