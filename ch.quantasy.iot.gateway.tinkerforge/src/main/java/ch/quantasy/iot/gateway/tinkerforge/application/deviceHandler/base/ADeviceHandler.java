/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base;

import ch.quantasy.iot.gateway.tinkerforge.TFMQTTGateway;
import ch.quantasy.iot.gateway.tinkerforge.application.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.status.DeviceHandlerReadyStatus;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class ADeviceHandler<D extends Device> extends AHandler {

    private D device;

    private final MQTTTinkerforgeStackHandler stackApplication;

    public ADeviceHandler(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(mqttURI, TFMQTTGateway.TOPIC + "/" + stackAddress.hostName + "/" + stackAddress.port, identityString);
	this.stackApplication = stackApplication;
	//this.deviceBaseTopic = TFMQTTGateway.TOPIC + "/" + stackAddress.hostName + "/" + stackAddress.port + "/" + getApplicationName() + "/" + identityString;
    }

    @Override
    protected final Class[] getStatusClasses() {
	ArrayList<Class> statusClassList = new ArrayList<>(Arrays.asList(getDeviceStatusClasses()));
	statusClassList.add(DeviceHandlerReadyStatus.class);
	return statusClassList.toArray(new Class[0]);
    }

    protected abstract Class[] getDeviceStatusClasses();

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
