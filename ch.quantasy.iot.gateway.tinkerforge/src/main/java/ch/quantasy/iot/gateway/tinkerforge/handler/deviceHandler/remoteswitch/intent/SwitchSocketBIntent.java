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
public class SwitchSocketBIntent extends AnIntent {

    public SwitchSocketBIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "switchSocketB");
	super.addDescription(RemoteSwitch.ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(RemoteSwitch.ADDRESS, Long.class, "JSON", "0", "...", "67108863");
	super.addDescription(RemoteSwitch.UNIT, Short.class, "JSON", "0", "...", "15");
	super.addDescription(RemoteSwitch.SWITCH_TO, Short.class, "JSON", "0", "...", "1");

    }

    @Override
    public boolean isExecutable() {
	return isEnabled() && isHouseCodeInRange() && isReceiverCodeInRange() && isSwitchToInRange();
    }

    private boolean isEnabled() {
	return getContent(RemoteSwitch.ENABLED).getValue(Boolean.class);
    }

    private boolean isHouseCodeInRange() {
	long address = getContent(RemoteSwitch.ADDRESS).getValue(Long.class);

	return (address >= 0 && address <= 67108863);
    }

    private boolean isReceiverCodeInRange() {
	short unit = getContent(RemoteSwitch.UNIT).getValue(Short.class);
	return (unit >= 0 && unit <= 15);
    }

    private boolean isSwitchToInRange() {
	short switchTo = getContent(RemoteSwitch.SWITCH_TO).getValue(Short.class);
	return (switchTo >= 0 || switchTo <= 1);
    }
}
