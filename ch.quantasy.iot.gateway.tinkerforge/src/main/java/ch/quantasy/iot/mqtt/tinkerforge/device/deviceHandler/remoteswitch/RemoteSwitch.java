/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.event.DimSocketBEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.event.SwitchSocketAEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.event.SwitchSocketBEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.event.SwitchSocketCEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.intent.DimSocketBIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.intent.RepeatsIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.intent.SwitchSocketAIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.intent.SwitchSocketBIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.intent.SwitchSocketCIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.status.RepeatsStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RemoteSwitch extends ADeviceHandler<BrickletRemoteSwitch> implements BrickletRemoteSwitch.SwitchingDoneListener {

    public static final String SWITCHING = "switching";
    public static final String ADDRESS = "address";
    public static final String UNIT = "unit";
    public static final String DIM_VALUE = "dimValue";

    public static final String HOUSE_CODE = "houseCode";
    public static final String RECEIVER_CODE = "receiverCode";
    public static final String SWITCH_TO = "switchTo";

    public static final String ENABLED = "enabled";
    public static final String SYSTEM_CODE = "systemCode";
    public static final String DEVICE_CODE = "deviceCode";

    public static final String REPEATS = "repeats";

    public String getApplicationName() {
	return "RemoteSwitch";
    }

    public RemoteSwitch(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addIntentClass(RepeatsIntent.class, SwitchSocketAIntent.class, SwitchSocketBIntent.class, DimSocketBIntent.class, SwitchSocketCIntent.class);
	super.addStatusClass(RepeatsStatus.class);
	super.addEventClass(SwitchSocketAEvent.class, SwitchSocketBEvent.class, DimSocketBEvent.class, SwitchSocketCEvent.class);
    }

    @Override
    public void switchingDone() {
	synchronized (this) {
	    this.notifyAll();
	}
	IMqttToken token = null;
	while (token == null || !token.isComplete() || token.getException() == null) {
	    try {
		token = (getEvent(SwitchSocketAEvent.class).update(SWITCHING, false));
		token.waitForCompletion(100);
	    } catch (MqttException ex) {
		Logger.getLogger(RemoteSwitch.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	while (token == null || !token.isComplete() || token.getException() == null) {
	    try {
		token = (getEvent(SwitchSocketBEvent.class).update(SWITCHING, false));
		token.waitForCompletion(100);
	    } catch (MqttException ex) {
		Logger.getLogger(RemoteSwitch.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	while (token == null || !token.isComplete() || token.getException() == null) {
	    try {
		token = (getEvent(DimSocketBEvent.class).update(SWITCHING, false));
		token.waitForCompletion(100);
	    } catch (MqttException ex) {
		Logger.getLogger(RemoteSwitch.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	while (token == null || !token.isComplete() || token.getException() == null) {
	    try {
		token = (getEvent(SwitchSocketCEvent.class).update(SWITCHING, false));
		token.waitForCompletion(100);
	    } catch (MqttException ex) {
		Logger.getLogger(RemoteSwitch.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addSwitchingDoneListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeSwitchingDoneListener(this);
    }

    public void executeIntent(RepeatsIntent intent) throws TimeoutException, NotConnectedException {

	getDevice().setRepeats(intent.getContent(REPEATS).getValue(Short.class));
	getStatus(RepeatsStatus.class).update(REPEATS, getDevice().getRepeats());
    }

    public void executeIntent(SwitchSocketAIntent intent) throws TimeoutException, NotConnectedException {
	synchronized (this) {
	    while (getDevice().getSwitchingState() == BrickletRemoteSwitch.SWITCHING_STATE_BUSY) {
		try {
		    this.wait(1000);
		} catch (InterruptedException ex) {
		    //We have been interrupted (by the listener)
		}
	    }
	    getDevice().switchSocketA(intent.getContent(HOUSE_CODE).getValue(Short.class), intent.getContent(RECEIVER_CODE).getValue(Short.class), intent.getContent(SWITCH_TO).getValue(Short.class));
	}
	getEvent(SwitchSocketAEvent.class).update(intent);
    }

    public void executeIntent(SwitchSocketBIntent intent) throws TimeoutException, NotConnectedException {
	synchronized (this) {
	    while (getDevice().getSwitchingState() == BrickletRemoteSwitch.SWITCHING_STATE_BUSY) {
		try {
		    this.wait(1000);
		} catch (InterruptedException ex) {
		    //We have been interrupted (by the listener)
		}
	    }
	    getDevice().switchSocketB(intent.getContent(ADDRESS).getValue(Long.class), intent.getContent(UNIT).getValue(Short.class), intent.getContent(SWITCH_TO).getValue(Short.class));
	}
	getEvent(SwitchSocketBEvent.class).update(intent);
    }

    public void executeIntent(DimSocketBIntent intent) throws TimeoutException, NotConnectedException {
	synchronized (this) {
	    while (getDevice().getSwitchingState() == BrickletRemoteSwitch.SWITCHING_STATE_BUSY) {
		try {
		    this.wait(1000);
		} catch (InterruptedException ex) {
		    //We have been interrupted (by the listener)
		}
	    }
	    getDevice().dimSocketB(intent.getContent(ADDRESS).getValue(Long.class), intent.getContent(UNIT).getValue(Short.class), intent.getContent(DIM_VALUE).getValue(Short.class));
	}
	getEvent(DimSocketBEvent.class).update(intent);
    }

    public void executeIntent(SwitchSocketCIntent intent) throws TimeoutException, NotConnectedException {
	synchronized (this) {
	    while (getDevice().getSwitchingState() == BrickletRemoteSwitch.SWITCHING_STATE_BUSY) {
		try {
		    this.wait(1000);
		} catch (InterruptedException ex) {
		    //We have been interrupted (by the listener)
		}
	    }
	    getDevice().switchSocketC(intent.getContent(SYSTEM_CODE).getValue(Character.class), intent.getContent(DEVICE_CODE).getValue(Short.class), intent.getContent(SWITCH_TO).getValue(Short.class));
	}
	getEvent(SwitchSocketCEvent.class).update(intent);
    }
}
