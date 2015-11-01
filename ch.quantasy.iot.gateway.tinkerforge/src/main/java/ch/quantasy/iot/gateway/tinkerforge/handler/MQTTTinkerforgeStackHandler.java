/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.barometer.Barometer;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceIR.DistanceIR;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.distanceUS.DistanceUS;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.dualrelay.DualRelay;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.gps.GPS;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.humidity.Humidity;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ledstrip.LedStrip;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.moisture.Moisture;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.motiondetector.MotionDetector;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.multitouch.MultiTouch;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.PiezoSpeaker;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.remoteswitch.RemoteSwitch;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.soundIntensity.SoundIntensity;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.tilt.Tilt;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.temperatureIR.TemperatureIR;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.BrickletDistanceUS;
import com.tinkerforge.BrickletDualRelay;
import com.tinkerforge.BrickletGPS;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.BrickletMoisture;
import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.BrickletMultiTouch;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.BrickletSoundIntensity;
import com.tinkerforge.BrickletTemperatureIR;
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
public class MQTTTinkerforgeStackHandler<D extends AHandler> extends AbstractTinkerforgeApplication {

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

    public synchronized String digestIdentityString(Device device) throws TimeoutException, NotConnectedException {
	synchronized (digest) {
	    return new BigInteger(("" + device.getIdentity().deviceIdentifier + device.getIdentity().uid).getBytes(java.nio.charset.StandardCharsets.UTF_8)).mod(BigInteger.valueOf(Integer.MAX_VALUE)).toString(Character.MAX_RADIX);
	}
    }

    @Override
    public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent, Device device) {

	try {
	    String digestedIdentityString = digestIdentityString(device);
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.LEDStrip) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new LedStrip(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletLEDStrip) device);

	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.MultiTouch) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new MultiTouch(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletMultiTouch) device);

	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.PiezoSpeaker) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new PiezoSpeaker(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletPiezoSpeaker) device);

	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.GPS) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new GPS(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletGPS) device);

	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Barometer) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new Barometer(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletBarometer) device);

	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.SoundIntensity) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new SoundIntensity(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletSoundIntensity) device);
	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Humidity) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new Humidity(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletHumidity) device);
	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Moisture) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new Moisture(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletMoisture) device);
	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.RemoteSwitch) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new RemoteSwitch(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletRemoteSwitch) device);
	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DistanceUS) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new DistanceUS(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletDistanceUS) device);
	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DistanceIR) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new DistanceIR(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletDistanceIR) device);
	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.MotionDetector) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new MotionDetector(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletMotionDetector) device);
	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DualRelay) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new DualRelay(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletDualRelay) device);
	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Tilt) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new Tilt(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletTilt) device);
	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.TemperatureIR) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new TemperatureIR(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletTemperatureIR) device);
	    }

	} catch (Throwable ex) {
	    Logger.getLogger(MQTTTinkerforgeStackHandler.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent, Device device) {
	try {
	    String digestedIdentityString = digestIdentityString(device);
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
