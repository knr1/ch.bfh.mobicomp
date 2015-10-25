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
public class SwitchSocketBIntent extends AnIntent {

    public boolean enabled;
    public long address;
    public short unit;
    public short switchTo;

    public SwitchSocketBIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "switchSocketB");
	super.addDescription("enabled", "Boolean", "JSON", "true", "false");
	super.addDescription("address", "Long", "JSON", "0", "...", "67108863");
	super.addDescription("unit", "Short", "JSON", "0", "...", "15");
	super.addDescription("switchTo", "Short", "JSON", "0", "...", "1");

    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
	if (string.endsWith(getName() + "/address")) {
	    address = fromMQTTMessage(mm, Long.class);
	}
	if (string.endsWith(getName() + "/unit")) {
	    unit = fromMQTTMessage(mm, Short.class);
	}
	if (string.endsWith(getName() + "/switchTo")) {
	    switchTo = fromMQTTMessage(mm, Short.class);
	}

    }

    public boolean isExecutable() {
	return enabled && isHouseCodeInRange() && isReceiverCodeInRange() && isSwitchToInRange();
    }

    private boolean isHouseCodeInRange() {
	return (address >= 0 && address <= 67108863);
    }

    private boolean isReceiverCodeInRange() {
	return (unit >= 0 && unit <= 15);
    }

    private boolean isSwitchToInRange() {
	return (switchTo >= 0 || switchTo <= 1);
    }
}
