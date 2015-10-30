/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.RemoteSwitch;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SwitchSocketCIntent extends AnIntent {

    public SwitchSocketCIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "switchSocketC");
	super.addDescription(RemoteSwitch.ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(RemoteSwitch.SYSTEM_CODE, Character.class, "JSON", "A", "...", "P");
	super.addDescription(RemoteSwitch.DEVICE_CODE, Short.class, "JSON", "1", "...", "16");
	super.addDescription(RemoteSwitch.SWITCH_TO, Short.class, "JSON", "0", "...", "1");

    }

    @Override
    public boolean isExecutable() {
	return isEnabled() && isSystemCodeInRange() && isDeviceCodeInRange() && isSwitchToInRange();
    }

    public boolean isEnabled() {
	return getContent(RemoteSwitch.ENABLED).getValue(Boolean.class);
    }

    private boolean isSystemCodeInRange() {
	char systemCode = getContent(RemoteSwitch.SYSTEM_CODE).getValue(Character.class);
	return (systemCode >= 'A' && systemCode <= 'P');
    }

    private boolean isDeviceCodeInRange() {
	short deviceCode = getContent(RemoteSwitch.DEVICE_CODE).getValue(Short.class);
	return (deviceCode >= 1 && deviceCode <= 16);
    }

    private boolean isSwitchToInRange() {
	short switchTo = getContent(RemoteSwitch.SWITCH_TO).getValue(Short.class);
	return (switchTo >= 0 || switchTo <= 1);
    }
}
