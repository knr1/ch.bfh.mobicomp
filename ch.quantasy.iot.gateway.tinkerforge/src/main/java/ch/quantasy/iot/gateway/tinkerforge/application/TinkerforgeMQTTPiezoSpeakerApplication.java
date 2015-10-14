/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application;

import ch.quantasy.iot.gateway.tinkerforge.TFMQTTGateway;
import ch.quantasy.iot.gateway.tinkerforge.application.device.piezospeaker.PiezoSpeaker;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import com.tinkerforge.BrickletPiezoSpeaker;
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
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeMQTTPiezoSpeakerApplication extends AbstractTinkerforgeApplication {

    private final Map<String, PiezoSpeaker> deviceHandlers;
    public static final String APPLICATION_TOPIC = "/PiezoSpeaker";
    public static final String APPLICATION_DEFINITION = TFMQTTGateway.TOPIC + "/Definition" + APPLICATION_TOPIC;
    private String clientID;
    private final URI mqttURI;
    private MessageDigest digest;

    public TinkerforgeMQTTPiezoSpeakerApplication(URI mqttURI) {
	this.mqttURI = mqttURI;
	this.deviceHandlers = new HashMap<>();
	try {
	    digest = MessageDigest.getInstance("SHA-256");
	} catch (NoSuchAlgorithmException ex) {
	    Logger.getLogger(TinkerforgeMQTTPiezoSpeakerApplication.class.getName()).log(Level.SEVERE, null, ex);
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

    @Override
    public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent, Device device) {
	if (TinkerforgeDevice.getDevice(device) != TinkerforgeDevice.PiezoSpeaker) {
	    return;
	}
	System.out.println("Connected: " + tinkerforgeStackAgent + " " + device);
	try {
	    String identityString = null;
	    synchronized (digest) {
		identityString = new BigInteger(digest.digest(device.getIdentity().toString().getBytes(java.nio.charset.StandardCharsets.UTF_8))).toString(Character.MAX_RADIX);
	    }
	    PiezoSpeaker deviceHandler = this.deviceHandlers.get(identityString);
	    if (deviceHandler == null) {
		deviceHandler = new PiezoSpeaker(mqttURI, tinkerforgeStackAgent.getStackAddress(), identityString);
		deviceHandlers.put(deviceHandler.getIdentityString(), deviceHandler);
	    }
	    deviceHandler.enableDevice((BrickletPiezoSpeaker) device);
	} catch (TimeoutException | NotConnectedException | MqttException ex) {
	    Logger.getLogger(TinkerforgeMQTTPiezoSpeakerApplication.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    @Override
    public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent, Device device) {
	if (TinkerforgeDevice.getDevice(device) != TinkerforgeDevice.PiezoSpeaker) {
	    return;
	}
	try {
	    String identityString = device.getIdentity().toString();
	    PiezoSpeaker deviceHandler = this.deviceHandlers.get(identityString);
	    if (deviceHandler == null) {
		return;
	    }
	    deviceHandler.disableDevice((BrickletPiezoSpeaker) device);
	} catch (TimeoutException | NotConnectedException ex) {
	    Logger.getLogger(TinkerforgeMQTTPiezoSpeakerApplication.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
}
