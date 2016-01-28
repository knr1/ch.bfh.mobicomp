/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.motiondetector;

import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.motiondetector.event.MotionDetectedEvent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletMotionDetector;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MotionDetector extends ADeviceHandler<BrickletMotionDetector> implements BrickletMotionDetector.DetectionCycleEndedListener, BrickletMotionDetector.MotionDetectedListener {

    public static final String DETECTED = "detected";

    public String getApplicationName() {
	return "MotionDetector";
    }

    public MotionDetector(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addEventClass(MotionDetectedEvent.class);
    }

    public Class[] getIntentClasses() {
	return new Class[]{};
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addDetectionCycleEndedListener(this);
	getDevice().addMotionDetectedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeDetectionCycleEndedListener(this);
	getDevice().removeMotionDetectedListener(this);
    }

    @Override
    public void detectionCycleEnded() {
	getEvent(MotionDetectedEvent.class).update(DETECTED, false);
    }

    @Override
    public void motionDetected() {
	getEvent(MotionDetectedEvent.class).update(DETECTED, true);

    }

}
