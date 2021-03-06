/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.event.BeepEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.event.CalibrateEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.event.MorseEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.intent.BeepIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.intent.CalibrateIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.piezospeaker.intent.MorseIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class PiezoSpeaker extends ADeviceHandler<BrickletPiezoSpeaker> implements BrickletPiezoSpeaker.MorseCodeFinishedListener, BrickletPiezoSpeaker.BeepFinishedListener {

    public static final String ENABLED = "enabled";
    public static final String DURATION = "duration";
    public static final String FREQUENCY = "frequency";

    public static final String CODE = "code";

    public String getApplicationName() {
	return "PiezoSpeaker";
    }

    public PiezoSpeaker(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addIntentClass(MorseIntent.class, BeepIntent.class, CalibrateIntent.class);
	super.addEventClass(BeepEvent.class, MorseEvent.class, CalibrateEvent.class);
    }

    @Override
    public void morseCodeFinished() {
	getEvent(MorseEvent.class).update(ENABLED, false);
    }

    @Override
    public void beepFinished() {
	getEvent(BeepEvent.class).update(ENABLED, false);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addBeepFinishedListener(this);
	getDevice().addMorseCodeFinishedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeBeepFinishedListener(this);
	getDevice().removeMorseCodeFinishedListener(this);
    }

    public void executeIntent(BeepIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().beep(intent.getValue(DURATION, Long.class), intent.getValue(FREQUENCY, Integer.class));
	getEvent(BeepEvent.class).update(intent);
    }

    public void executeIntent(MorseIntent intent) throws TimeoutException, NotConnectedException {
	int frequency = intent.getValue(FREQUENCY, Integer.class);
	String code = intent.getValue(CODE, String.class);
	getDevice().morseCode(code, frequency);

	getEvent(MorseEvent.class).update(intent);
    }

    public void executeIntent(CalibrateIntent intent) throws TimeoutException, NotConnectedException {
	getEvent(CalibrateEvent.class).update(intent);
	getDevice().calibrate();
	getEvent(CalibrateEvent.class).update(ENABLED, false);
    }

}
