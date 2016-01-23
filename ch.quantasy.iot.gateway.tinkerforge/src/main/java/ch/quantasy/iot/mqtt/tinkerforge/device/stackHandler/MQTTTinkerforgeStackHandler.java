/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler;

import ch.quantasy.iot.mqtt.base.AHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.IMU;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.IMUV2;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.accelerometer.Accelerometer;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ambientLight.AmbientLight;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ambientLightV2.AmbientLightV2;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.barometer.Barometer;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.color.Color;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.distanceIR.DistanceIR;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.distanceUS.DistanceUS;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualButton.DualButton;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.dualrelay.DualRelay;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.gps.GPS;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.humidity.Humidity;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.joystick.Joystick;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.LCD20x4;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ledstrip.LedStrip;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.linearPoti.LinearPoti;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.moisture.Moisture;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.motiondetector.MotionDetector;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.multitouch.MultiTouch;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.PiezoSpeaker;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.remoteswitch.RemoteSwitch;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.rotaryEncoder.RotaryEncoder;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.segmentDisplay4x7.SegmentDisplay4x7;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.soundIntensity.SoundIntensity;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.temperatureIR.TemperatureIR;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.tilt.Tilt;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.status.ManagedDeviceHandlersStatus;
import ch.quantasy.iot.mqtt.tinkerforge.gateway.MQTT2TF;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickIMU;
import com.tinkerforge.BrickIMUV2;
import com.tinkerforge.BrickletAccelerometer;
import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.BrickletAmbientLightV2;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.BrickletColor;
import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.BrickletDistanceUS;
import com.tinkerforge.BrickletDualButton;
import com.tinkerforge.BrickletDualRelay;
import com.tinkerforge.BrickletGPS;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletJoystick;
import com.tinkerforge.BrickletLCD20x4;
import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.BrickletLinearPoti;
import com.tinkerforge.BrickletMoisture;
import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.BrickletMultiTouch;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.BrickletRotaryEncoder;
import com.tinkerforge.BrickletSegmentDisplay4x7;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MQTTTinkerforgeStackHandler<D extends AHandler> extends AbstractTinkerforgeApplication {

    public final static String MANAGED_DEVICE_HANDLERS = "managedDeviceHandlers";
    private final Map<String, ADeviceHandler> deviceHandlers;
    private final URI mqttURI;
    private MessageDigest digest;
    private MQTT2TF gateway;
    private TinkerforgeStackAddress stackAddress;

    public MQTTTinkerforgeStackHandler(TinkerforgeStackAddress stackAddress, MQTT2TF gateway) {
	this.stackAddress = stackAddress;
	this.gateway = gateway;
	this.mqttURI = gateway.getMqttURI();
	this.deviceHandlers = new HashMap<>();
	try {
	    digest = MessageDigest.getInstance("SHA-256");
	} catch (NoSuchAlgorithmException ex) {
	    Logger.getLogger(MQTTTinkerforgeStackHandler.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 79 * hash + Objects.hashCode(this.gateway);
	hash = 79 * hash + Objects.hashCode(this.stackAddress);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final MQTTTinkerforgeStackHandler<?> other = (MQTTTinkerforgeStackHandler<?>) obj;
	if (!Objects.equals(this.gateway, other.gateway)) {
	    return false;
	}
	if (!Objects.equals(this.stackAddress, other.stackAddress)) {
	    return false;
	}
	return true;
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
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.IMUV2) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new IMUV2(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickIMUV2) device);

	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.IMU) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new IMU(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickIMU) device);

	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.SegmentDisplay4x7) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new SegmentDisplay4x7(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletSegmentDisplay4x7) device);

	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.DualButton) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new DualButton(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletDualButton) device);

	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.AmbientLight) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new AmbientLight(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletAmbientLight) device);

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
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.RotaryEncoder) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new RotaryEncoder(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletRotaryEncoder) device);

	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.AmbientLightV2) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new AmbientLightV2(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletAmbientLightV2) device);

	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Accelerometer) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new Accelerometer(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletAccelerometer) device);

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
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Color) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new Color(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletColor) device);

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
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.LCD20x4) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new LCD20x4(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletLCD20x4) device);
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
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.LinearPoti) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new LinearPoti(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletLinearPoti) device);
	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.LinearPoti) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new LinearPoti(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletLinearPoti) device);
	    }
	    if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Joystick) {
		System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);

		ADeviceHandler deviceHandler = this.deviceHandlers.get(digestedIdentityString);
		if (deviceHandler == null) {
		    deviceHandler = new Joystick(this, mqttURI, tinkerforgeStackAgent.getStackAddress(), digestedIdentityString);
		    deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
		}
		deviceHandler.enableDevice((BrickletJoystick) device);
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
	    List<String> managedDeviceHandlerList = new ArrayList<>();
	    for (ADeviceHandler deviceHandler : this.deviceHandlers.values()) {
		managedDeviceHandlerList.add(this.stackAddress.hostName + "/" + this.stackAddress.port + "/" + deviceHandler.getApplicationName() + "/" + deviceHandler.getIdentityString());
	    }
	    gateway.getStatus(ManagedDeviceHandlersStatus.class).update(MANAGED_DEVICE_HANDLERS, managedDeviceHandlerList);

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
