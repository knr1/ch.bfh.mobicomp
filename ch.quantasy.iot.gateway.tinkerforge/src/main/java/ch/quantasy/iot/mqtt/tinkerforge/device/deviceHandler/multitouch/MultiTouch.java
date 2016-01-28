package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.multitouch;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.multitouch.event.CalibrateEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.multitouch.event.TouchEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.multitouch.intent.CalibrateIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.multitouch.intent.ElectrodeConfigIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.multitouch.intent.ElectrodeSensitivityIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.multitouch.status.ElectrodeConfigStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.multitouch.status.ElectrodeSensitivityStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletMultiTouch;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MultiTouch extends ADeviceHandler<BrickletMultiTouch> implements BrickletMultiTouch.TouchStateListener {

    public static final String ENABLED = "enabled";
    public static final String TOUCH_STATE = "touchState";
    public static final String SENSITIVITY = "sensitivity";

    public String getApplicationName() {
	return "MultiTouch";
    }

    public MultiTouch(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addIntentClass(CalibrateIntent.class, ElectrodeConfigIntent.class, ElectrodeSensitivityIntent.class);
	super.addEventClass(TouchEvent.class, CalibrateEvent.class);
	super.addStatusClass(ElectrodeConfigStatus.class, ElectrodeSensitivityStatus.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addTouchStateListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeTouchStateListener(this);
    }

    public void executeIntent(CalibrateIntent intent) throws TimeoutException, NotConnectedException {
	getEvent(CalibrateEvent.class).update(intent);
	getDevice().recalibrate();
	getEvent(CalibrateEvent.class).update(ENABLED, false);
    }

    public void executeIntent(ElectrodeConfigIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().setElectrodeConfig(intent.getValue(ENABLED, Integer.class));
	getStatus(ElectrodeConfigStatus.class).update(ENABLED, getDevice().getElectrodeConfig());
    }

    public void executeIntent(ElectrodeSensitivityIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().setElectrodeSensitivity(intent.getValue(SENSITIVITY, Short.class));
	getStatus(ElectrodeSensitivityStatus.class).update(SENSITIVITY, getDevice().getElectrodeSensitivity());
    }

    @Override
    public void touchState(int i) {
	getEvent(TouchEvent.class).update(TOUCH_STATE, i);
    }

}
