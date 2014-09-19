/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.core;

import ch.quantasy.tinkerbus.service.device.deviceAmbientLight.TinkerforgeAmbientLightService;
import ch.quantasy.tinkerbus.service.device.deviceDC.TinkerforgeDCService;
import ch.quantasy.tinkerbus.service.device.deviceLED.TinkerforgeLEDService;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import com.tinkerforge.BrickDC;
import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.Device;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeDeviceServiceFactory {

    public static TinkerforgeDeviceService getDevice(Device device, String deviceID) {
	TinkerforgeDeviceService service = null;
	if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.AmbientLight) {
	    service = new TinkerforgeAmbientLightService((BrickletAmbientLight) device, deviceID);
	}
	if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DC) {
	    service = new TinkerforgeDCService((BrickDC) device, deviceID);
	}
	if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.LEDStrip) {
	    service = new TinkerforgeLEDService((BrickletLEDStrip) device, deviceID);
	}
	return service;
    }
}
