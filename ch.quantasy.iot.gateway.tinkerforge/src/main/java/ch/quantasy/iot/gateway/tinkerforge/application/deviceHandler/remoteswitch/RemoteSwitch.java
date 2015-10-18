/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch;

import ch.quantasy.iot.gateway.tinkerforge.application.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.event.DimSocketBEvent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.event.SwitchSocketAEvent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.event.SwitchSocketBEvent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.event.SwitchSocketCEvent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.intent.DimSocketBIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.intent.RepeatsIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.intent.SwitchSocketAIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.intent.SwitchSocketBIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.intent.SwitchSocketCIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.remoteswitch.status.RepeatsStatus;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RemoteSwitch extends ADeviceHandler<BrickletRemoteSwitch> implements BrickletRemoteSwitch.SwitchingDoneListener {

    public String getApplicationName() {
	return "RemoteSwitch";
    }

    public RemoteSwitch(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
    }

    @Override
    public Class[] getIntentClasses() {
	return new Class[]{RepeatsIntent.class, SwitchSocketAIntent.class, SwitchSocketBIntent.class, DimSocketBIntent.class, SwitchSocketCIntent.class};
    }

    @Override
    public Class[] getEventClasses() {
	return new Class[]{SwitchSocketAEvent.class, SwitchSocketBEvent.class, DimSocketBEvent.class, SwitchSocketCEvent.class};
    }

    @Override
    protected Class[] getStatusClasses() {
	return new Class[]{RepeatsStatus.class};
    }

    @Override
    public void switchingDone() {
	getEvent(SwitchSocketAEvent.class).updateSwitching(false);
	getEvent(SwitchSocketBEvent.class).updateSwitching(false);
	getEvent(DimSocketBEvent.class).updateSwitching(false);
	getEvent(SwitchSocketCEvent.class).updateSwitching(false);

    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addSwitchingDoneListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeSwitchingDoneListener(this);
    }

    /**
     * This method allows to describe the strategy of the DeviceHandler for any incoming intent. In this specific case
     * it simply dispatches every intent to the concrete execution.
     *
     * @param intent
     */
    @Override
    public void executeIntent(AnIntent intent) throws Throwable {
	if (intent instanceof RepeatsIntent) {
	    executeIntent((RepeatsIntent) intent);
	}
	if (intent instanceof SwitchSocketAIntent) {
	    executeIntent((SwitchSocketAIntent) intent);
	}
	if (intent instanceof SwitchSocketBIntent) {
	    executeIntent((SwitchSocketBIntent) intent);
	}
	if (intent instanceof DimSocketBIntent) {
	    executeIntent((DimSocketBIntent) intent);
	}
	if (intent instanceof SwitchSocketCIntent) {
	    executeIntent((SwitchSocketCIntent) intent);
	}
    }

    public void executeIntent(RepeatsIntent intent) throws TimeoutException, NotConnectedException {

	getDevice().setRepeats(intent.repeats);
	getStatus(RepeatsStatus.class).updateRepeats(getDevice().getRepeats());
    }

    public void executeIntent(SwitchSocketAIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().switchSocketA(intent.houseCode, intent.receiverCode, intent.switchTo);
	getEvent(SwitchSocketAEvent.class).updateIntent(intent);
    }

    public void executeIntent(SwitchSocketBIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().switchSocketB(intent.address, intent.unit, intent.switchTo);
	getEvent(SwitchSocketBEvent.class).updateIntent(intent);
    }

    public void executeIntent(DimSocketBIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().dimSocketB(intent.address, intent.unit, intent.dimValue);
	getEvent(DimSocketBEvent.class).updateIntent(intent);
    }

    public void executeIntent(SwitchSocketCIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().switchSocketC(intent.systemCode, intent.deviceCode, intent.switchTo);
	getEvent(SwitchSocketCEvent.class).updateIntent(intent);
    }
}
