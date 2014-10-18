/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.core;

import ch.quantasy.messagebus.message.definition.Content;
import ch.quantasy.tinkerbus.bus.ATinkerforgeService;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;
import ch.quantasy.tinkerbus.service.device.message.TinkerforgeDeviceEvent;
import ch.quantasy.tinkerbus.service.device.message.TinkerforgeDeviceIntent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import com.tinkerforge.Device;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <D>
 * @param <I>
 * @param <E>
 */
public abstract class ATinkerforgeDeviceService<D extends Device, I extends TinkerforgeDeviceIntent, E extends TinkerforgeDeviceEvent> extends ATinkerforgeService<I, E> {

    protected D device;
    private final String deviceID;
    private final TinkerforgeDeviceContent deviceContent;

    public ATinkerforgeDeviceService(D device, String deviceID) {
	this.deviceID = deviceID;
	this.deviceContent = new TinkerforgeDeviceContent();
	updateDevice(device);
	E event = createEvent();
	publish(event);
    }

    protected TinkerforgeDeviceContent getDeviceContent() {
	return deviceContent;
    }

    public void updateSettings(TinkerforgeDeviceContent content) {
	Set<Content> changeSet = deviceContent.updateSettings(content);
	if (!changeSet.isEmpty()) {
	    Map<Class, Content> changeMap = new HashMap<>();
	    for (Content c : changeSet) {
		changeMap.put(c.getClass(), c);
	    }
	    updateSettingsOnDevice(device, changeMap);
	    E event = createEvent();
	    publish(event);
	}
    }

    public void updateDevice(D device) {
	if (!TinkerforgeDevice.areEqual(this.device, device)) {
	    this.device = device;
	    this.device.setResponseExpectedAll(true);
	    if (deviceContent != null) {
		updateSettingsOnDevice(device, deviceContent.getSettings());
	    }
	    updateListeners(device);
	}
    }

    @Override
    public void handleIntent(I intent) {
	if (intent == null) {
	    return;
	}
	TinkerforgeDeviceContent content = intent.getDeviceContent();
	updateSettings(content);
	handleTinkerforgeIntent(intent);
    }

    @Override
    public String getID() {
	return deviceID;
    }

    protected abstract void updateListeners(D device);

    protected abstract void updateSettingsOnDevice(D device, Map<Class, Content> deviceContent);

    public abstract void handleTinkerforgeIntent(I message);

}
