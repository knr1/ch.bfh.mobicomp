/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker;

import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.event.BeepEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.event.CalibrateEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.event.MorseEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.intent.BeepIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.intent.CalibrateIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.piezospeaker.intent.MorseIntent;
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

    /**
     * This method allows to describe the strategy of the DeviceHandler for any incoming intent. In this specific case
     * it simply dispatches every intent to the concrete execution.
     *
     * @param intent
     */
    public void executeIntent(AnIntent intent) throws Throwable {
	if (intent instanceof BeepIntent) {
	    executeIntent((BeepIntent) intent);
	}
	if (intent instanceof MorseIntent) {
	    executeIntent((MorseIntent) intent);
	}
	if (intent instanceof CalibrateIntent) {
	    executeIntent((CalibrateIntent) intent);
	}
    }

    public void executeIntent(BeepIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().beep(intent.getValue(DURATION, Long.class), intent.getValue(FREQUENCY, Integer.class));
	getEvent(BeepEvent.class).updateIntent(intent);
    }

    public void executeIntent(MorseIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().morseCode(intent.code, intent.frequency);

	getEvent(MorseEvent.class).updateIntent(intent);
    }

    public void executeIntent(CalibrateIntent intent) throws TimeoutException, NotConnectedException {
	getEvent(CalibrateEvent.class).updateIntent(intent);
	getDevice().calibrate();
	getEvent(CalibrateEvent.class).update(ENABLED, false);
    }

}
