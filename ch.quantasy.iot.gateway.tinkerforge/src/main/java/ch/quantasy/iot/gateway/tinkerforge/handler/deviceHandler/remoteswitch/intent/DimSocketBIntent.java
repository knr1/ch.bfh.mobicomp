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
public class DimSocketBIntent extends AnIntent {

    public boolean enabled;
    public long address;
    public short unit;
    public short dimValue;

    public DimSocketBIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "dimSocketB");
	super.addDescription("enabled", Boolean.class, "JSON", "true", "false");
	super.addDescription("address", Long.class, "JSON", "0", "...", "67108863");
	super.addDescription("unit", Short.class, "JSON", "0", "...", "15");
	super.addDescription("dimValue", Short.class, "JSON", "0", "...", "15");

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
	if (string.endsWith(getName() + "/dimValue")) {
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
