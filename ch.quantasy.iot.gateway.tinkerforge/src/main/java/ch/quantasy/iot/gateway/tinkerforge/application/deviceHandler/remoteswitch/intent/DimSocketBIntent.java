/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.intent;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.AnIntent;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DimSocketBIntent extends AnIntent {

    public boolean enabled;
    public long address;
    public short unit;
    public short dimValue;

    public DimSocketBIntent(ADeviceHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "dimSocketB");
	super.addTopicDescription("enabled", "Boolean", "JSON", "true", "false");
	super.addTopicDescription("address", "Long", "JSON", "0", "...", "67108863");
	super.addTopicDescription("unit", "Short", "JSON", "0", "...", "15");
	super.addTopicDescription("dimValue", "Short", "JSON", "0", "...", "15");

    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getIntentName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
	if (string.endsWith(getIntentName() + "/address")) {
	    address = fromMQTTMessage(mm, Long.class);
	}
	if (string.endsWith(getIntentName() + "/unit")) {
	    unit = fromMQTTMessage(mm, Short.class);
	}
	if (string.endsWith(getIntentName() + "/dimValue")) {
	    dimValue = fromMQTTMessage(mm, Short.class);
	}

    }

    public boolean isExecutable() {
	return enabled && isHouseCodeInRange() && isReceiverCodeInRange() && isDimValue();
    }

    private boolean isHouseCodeInRange() {
	return (address >= 0 && address <= 67108863);
    }

    private boolean isReceiverCodeInRange() {
	return (unit >= 0 && unit <= 15);
    }

    private boolean isDimValue() {
	return (dimValue >= 0 || dimValue <= 15);
    }
}
