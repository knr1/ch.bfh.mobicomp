/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.motiondetector;

import ch.quantasy.iot.gateway.tinkerforge.handler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.motiondetector.event.MotionDetectedEvent;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletMotionDetector;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MotionDetector extends ADeviceHandler<BrickletMotionDetector> implements BrickletMotionDetector.DetectionCycleEndedListener, BrickletMotionDetector.MotionDetectedListener {

    public String getApplicationName() {
	return "MotionDetector";
    }

    public MotionDetector(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
    }

    public Class[] getIntentClasses() {
	return new Class[]{};
    }

    @Override
    protected Class[] getEventClasses() {
	return new Class[]{};
    }

    @Override
    protected Class[] getDeviceStatusClasses() {
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

    /**
     * This method allows to describe the strategy of the DeviceHandler for any incoming intent. In this specific case
     * it simply dispatches every intent to the concrete execution.
     *
     * @param intent
     */
    public void executeIntent(AnIntent intent) throws Throwable {
	//This Device does not have any specific intents.
    }

    @Override
    public void detectionCycleEnded() {
	getEvent(MotionDetectedEvent.class).updateDetected(false);
    }

    @Override
    public void motionDetected() {
	getEvent(MotionDetectedEvent.class).updateDetected(true);

    }

}
