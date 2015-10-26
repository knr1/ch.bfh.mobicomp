/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.gateway.TFMQTTGateway;
import ch.quantasy.iot.gateway.tinkerforge.handler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.status.DeviceHandlerReadyStatus;
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

    private D device;

    private final MQTTTinkerforgeStackHandler stackApplication;

    public ADeviceHandler(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(mqttURI, TFMQTTGateway.TOPIC + "/" + stackAddress.hostName + "/" + stackAddress.port, identityString);
	super.addStatusClass(DeviceHandlerReadyStatus.class);
	this.stackApplication = stackApplication;
    }

    public void enableDevice(D device) {
	try {
	    if (!this.getIdentityString().equals(stackApplication.digestIdentityString(device.getIdentity().toString()))) {
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
    }

    public void disableDevice(Device device) {
	if (!TinkerforgeDevice.areEqual(this.device, device)) {
	    return;
	}
	removeDeviceListeners();
	this.device = null;

    }

    protected abstract void addDeviceListeners();

    protected abstract void removeDeviceListeners();

    protected D getDevice() {
	return this.device;
    }

}
