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
public class SwitchSocketCIntent extends AnIntent {

    public boolean enabled;
    public char systemCode;
    public short deviceCode;
    public short switchTo;

    public SwitchSocketCIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "switchSocketC");
	super.addDescription("enabled", Boolean.class, "JSON", "true", "false");
	super.addDescription("systemCode", Character.class, "JSON", "A", "...", "P");
	super.addDescription("deviceCode", Short.class, "JSON", "1", "...", "16");
	super.addDescription("switchTo", Short.class, "JSON", "0", "...", "1");

    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
	if (string.endsWith(getName() + "/systemCode")) {
	    systemCode = fromMQTTMessage(mm, Character.class);
	}
	if (string.endsWith(getName() + "/deviceCode")) {
	    deviceCode = fromMQTTMessage(mm, Short.class);
	}
	if (string.endsWith(getName() + "/switchTo")) {
	    switchTo = fromMQTTMessage(mm, Short.class);
	}

    }

    public boolean isExecutable() {
	return enabled && isSystemCodeInRange() && isDeviceCodeInRange() && isSwitchToInRange();
    }

    private boolean isSystemCodeInRange() {
	return (this.systemCode >= 'A' && this.systemCode <= 'P');
    }

    private boolean isDeviceCodeInRange() {
	return (deviceCode >= 1 && deviceCode <= 16);
    }

    private boolean isSwitchToInRange() {
	return (switchTo >= 0 || switchTo <= 1);
    }
}
