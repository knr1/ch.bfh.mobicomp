/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.PiezoSpeaker;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.tilt.Tilt;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.BrickletTilt;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MQTTTinkerforgeStackHandler<D extends ADeviceHandler> extends AbstractTinkerforgeApplication {

    private final Map<String, ADeviceHandler> deviceHandlers;
    private String clientID;
    private final URI mqttURI;
    private MessageDigest digest;

    public MQTTTinkerforgeStackHandler(URI mqttURI) {
	this.mqttURI = mqttURI;
	this.deviceHandlers = new HashMap<>();
	try {
	    digest = MessageDigest.getInstance("SHA-256");
	} catch (NoSuchAlgorithmException ex) {
	    Logger.getLogger(MQTTTinkerforgeStackHandler.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public boolean equals(Object obj) {
	return this == obj;
    }

    @Override
    public int hashCode() {
	return 0;
    }

    public synchronized String digestIdentityString(String identityString) {
	synchronized (digest) {
	    return new BigInteger(identityString.getBytes(java.nio.charset.StandardCharsets.UTF_8)).mod(BigInteger.valueOf(Integer.MAX_VALUE)).toString(Character.MAX_RADIX);
	}
    }

    @Override
    public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent, Device device) {

	try {
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.PiezoSpeaker) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		String digestedIdentityString = null;
		digestedIdentityString = digestIdentityString(device.getIdentity().toString());
		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new PiezoSpeaker(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletPiezoSpeaker) device);

	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Tilt) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		String digestedIdentityString = null;
		digestedIdentityString = digestIdentityString(device.getIdentity().toString());
		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new Tilt(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletTilt) device);
	    }

	} catch (Throwable ex) {
	    Logger.getLogger(MQTTTinkerforgeStackHandler.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent, Device device) {
	if (TinkerforgeDevice.getDevice(device) != TinkerforgeDevice.PiezoSpeaker) {
	    return;
	}
	try {
	    String digestedIdentityString = digestIdentityString(device.getIdentity().toString());
	    ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
	    if (deviceHandler == null) {
		return;
	    }
	    deviceHandler.disableDevice(device);
	} catch (TimeoutException | NotConnectedException ex) {
	    Logger.getLogger(MQTTTinkerforgeStackHandler.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
}
