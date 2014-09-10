/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.core;

import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeService;
import ch.quantasy.tinkerbus.bus.ServiceDiscoveryIntent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import com.tinkerforge.Device;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <D>
 * @param <S>
 * @param <I>
 * @param <E>
 */
public abstract class TinkerforgeDeviceService<D extends Device, S extends TinkerforgeDeviceSetting, I extends TinkerforgeDeviceIntent<S>, E extends TinkerforgeDeviceEvent<S>> extends ATinkerforgeService<I, E> {

    protected D device;
    protected S currentSetting;
    private final String deviceID;

    public TinkerforgeDeviceService(D device, String deviceID) {
	this.deviceID = deviceID;
	updateDevice(device);
	E event = createServiceDiscoveryEvent();
	publish(event);
    }

    public void updateDevice(D device) {
	if (!TinkerforgeDevice.areEqual(this.device, device)) {
	    this.device = device;
	    this.device.setResponseExpectedAll(true);
	    updateListeners();
	}
	updateDeviceSetting(currentSetting);
    }

    @Override
    public String getID() {
	return deviceID;
    }

    protected abstract void updateListeners();

    protected abstract void updateDeviceSetting(S setting);

    protected abstract S updateCurrentSetting(S newSetting);

    //Do we really need that?
    //public static ServiceDiscoveryEvent getEventForDeviceServiceDiscovered(DefaultEvent event) {
    //if (event instanceof ServiceDiscoveryEvent) {
    //  return (ServiceDiscoveryEvent) event;
    //}
    //return null;
    //}
    public static ServiceDiscoveryIntent getIntentForDeviceServiceDiscovered(ATinkerforgeAgent sender) {
	return new ServiceDiscoveryIntent(sender);
    }

}
