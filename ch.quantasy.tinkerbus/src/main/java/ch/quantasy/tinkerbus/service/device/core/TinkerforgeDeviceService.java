/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.core;

import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.messagebus.message.definition.Intent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeService;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import com.tinkerforge.Device;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <D>
 * @param <I>
 * @param <E>
 */
public abstract class TinkerforgeDeviceService<D extends Device, I extends Intent, E extends Event> extends ATinkerforgeService<I, E> {

    protected D device;
    private final String deviceID;
    private TinkerforgeDeviceContent deviceContent;

    public TinkerforgeDeviceService(D device, String deviceID) {
	this.deviceID = deviceID;
	updateDevice(device);
	E event = createEvent();
	publish(event);
    }

    protected TinkerforgeDeviceContent getDeviceContent() {
	return deviceContent;
    }

    protected abstract void updateSettingsOnDevice();

    public void updateDevice(D device) {
	if (!TinkerforgeDevice.areEqual(this.device, device)) {
	    this.device = device;
	    this.device.setResponseExpectedAll(true);
	    updateSettingsOnDevice();
	    updateListeners();
	}
    }

    @Override
    public String getID() {
	return deviceID;
    }

    protected abstract void updateListeners();

}
