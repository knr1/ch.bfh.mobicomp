/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.intent;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.RemoteSwitch;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SwitchSocketAIntent extends AnIntent<RemoteSwitch> {

    public SwitchSocketAIntent(RemoteSwitch deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "switchSocketA");
	super.addDescription(RemoteSwitch.ENABLED, Boolean.class, "JSON", "true", "false");
	super.addDescription(RemoteSwitch.HOUSE_CODE, Short.class, "JSON", "0", "...", "31");
	super.addDescription(RemoteSwitch.RECEIVER_CODE, Short.class, "JSON", "0", "...", "31");
	super.addDescription(RemoteSwitch.SWITCH_TO, Short.class, "JSON", "0", "...", "1");

    }

    @Override
    public boolean isExecutable() {
	return isExecutable() && isHouseCodeInRange() && isReceiverCodeInRange() && isSwitchToInRange();
    }

    private boolean isHouseCodeInRange() {
	short houseCode = getContent(RemoteSwitch.HOUSE_CODE).getValue(Short.class);
	return (houseCode >= 0 && houseCode <= 31);
    }

    private boolean isReceiverCodeInRange() {
	short receiverCode = getContent(RemoteSwitch.RECEIVER_CODE).getValue(Short.class);

	return (receiverCode >= 0 && receiverCode <= 31);
    }

    private boolean isSwitchToInRange() {
	short switchTo = getContent(RemoteSwitch.SWITCH_TO).getValue(Short.class);
	return (switchTo >= 0 || switchTo <= 1);
    }

    @Override
    public void execute() throws Throwable {
	getDeviceHandler().executeIntent(this);
    }
}
