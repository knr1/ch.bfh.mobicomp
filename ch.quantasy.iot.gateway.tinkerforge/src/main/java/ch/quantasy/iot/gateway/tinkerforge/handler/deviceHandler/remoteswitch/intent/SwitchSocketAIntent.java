/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
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

    public SwitchSocketAIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "switchSocketA");
	super.addDescription("enabled", "Boolean", "JSON", "true", "false");
	super.addDescription("houseCode", "Short", "JSON", "0", "...", "31");
	super.addDescription("receiverCode", "Short", "JSON", "0", "...", "31");
	super.addDescription("switchTo", "Short", "JSON", "0", "...", "1");

    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
	if (string.endsWith(getName() + "/houseCode")) {
	    houseCode = fromMQTTMessage(mm, Short.class);
	}
	if (string.endsWith(getName() + "/receiverCode")) {
	    receiverCode = fromMQTTMessage(mm, Short.class);
	}
	if (string.endsWith(getName() + "/switchTo")) {
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
