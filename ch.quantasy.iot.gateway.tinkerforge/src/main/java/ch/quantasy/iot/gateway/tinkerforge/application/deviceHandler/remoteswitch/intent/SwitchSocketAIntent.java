/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.intent;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.base.message.AnIntent;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SwitchSocketAIntent extends AnIntent {

    public boolean enabled;
    public short houseCode;
    public short receiverCode;
    public short switchTo;

    public SwitchSocketAIntent(ADeviceHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "switchSocketA");
	super.addTopicDescription("enabled", "Boolean", "JSON", "true", "false");
	super.addTopicDescription("houseCode", "Short", "JSON", "0", "...", "31");
	super.addTopicDescription("receiverCode", "Short", "JSON", "0", "...", "31");
	super.addTopicDescription("switchTo", "Short", "JSON", "0", "...", "1");

    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getIntentName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
	if (string.endsWith(getIntentName() + "/houseCode")) {
	    houseCode = fromMQTTMessage(mm, Short.class);
	}
	if (string.endsWith(getIntentName() + "/receiverCode")) {
	    receiverCode = fromMQTTMessage(mm, Short.class);
	}
	if (string.endsWith(getIntentName() + "/switchTo")) {
	    switchTo = fromMQTTMessage(mm, Short.class);
	}

    }

    public boolean isExecutable() {
	return enabled && isHouseCodeInRange() && isReceiverCodeInRange() && isSwitchToInRange();
    }

    private boolean isHouseCodeInRange() {
	return (houseCode >= 0 && houseCode <= 31);
    }

    private boolean isReceiverCodeInRange() {
	return (receiverCode >= 0 && receiverCode <= 31);
    }

    private boolean isSwitchToInRange() {
	return (switchTo >= 0 || switchTo <= 1);
    }
}
