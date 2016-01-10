/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.tinkerforge.gateway.MQTT2TF;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.status.DeviceHandlerReadyStatus;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class ADeviceHandler<D extends Device> extends AHandler {

    public final static String ENABLED = "enabled";

    private D device;

    private final MQTTTinkerforgeStackHandler stackApplication;

    public ADeviceHandler(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(mqttURI, MQTT2TF.TOPIC + "/" + stackAddress.hostName + "/" + stackAddress.port, identityString);
	super.addStatusClass(DeviceHandlerReadyStatus.class);
	this.stackApplication = stackApplication;
    }

    public void enableDevice(D device) {
	try {
	    if (!this.getIdentityString().equals(stackApplication.digestIdentityString(device))) {
		return;
	    }
	} catch (TimeoutException | NotConnectedException ex) {
	    return;
	}
	if (this.device != null) {
	    return;
	}
	this.device = device;
	addDeviceListeners();
	getStatus(DeviceHandlerReadyStatus.class).updateEnabled(true);
    }

    public void disableDevice(Device device) {
	if (!TinkerforgeDevice.areEqual(this.device, device)) {
	    return;
	}
	removeDeviceListeners();
	this.device = null;
	getStatus(DeviceHandlerReadyStatus.class).updateEnabled(false);

    }

    protected abstract void addDeviceListeners();

    protected abstract void removeDeviceListeners();

    protected D getDevice() {
	return this.device;
    }

}
