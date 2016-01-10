/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.segmentDisplay4x7;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.segmentDisplay4x7.event.CounterFinishedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.segmentDisplay4x7.event.SegmentsEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.segmentDisplay4x7.event.StartCounterEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.segmentDisplay4x7.intent.CounterIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.segmentDisplay4x7.intent.SegmentsIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletSegmentDisplay4x7;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SegmentDisplay4x7 extends ADeviceHandler<BrickletSegmentDisplay4x7> implements BrickletSegmentDisplay4x7.CounterFinishedListener {

    public static final String LENGTH = "length";
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String INCREMENT = "increment";
    public static final String BRIGHTNESS = "brightness";
    public static final String COLON = "colon";
    public static final String SEGMENTS = "segments";

    public String getApplicationName() {
	return "SegmentDisplay4x7";
    }

    public SegmentDisplay4x7(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addIntentClass(CounterIntent.class, SegmentsIntent.class);
	super.addEventClass(StartCounterEvent.class, SegmentsEvent.class, CounterFinishedEvent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addCounterFinishedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeCounterFinishedListener(this);
    }

    /**
     * This method allows to describe the strategy of the DeviceHandler for any incoming intent. In this specific case
     * it simply dispatches every intent to the concrete execution.
     *
     * @param intent
     */
    public void executeIntent(AnIntent intent) throws Throwable {
	if (intent instanceof CounterIntent) {
	    executeIntent((CounterIntent) intent);
	}
	if (intent instanceof SegmentsIntent) {
	    executeIntent((SegmentsIntent) intent);
	}
    }

    public void executeIntent(CounterIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().startCounter(intent.getValue(FROM, Short.class), intent.getValue(TO, Short.class), intent.getValue(INCREMENT, Short.class), intent.getValue(LENGTH, Long.class));
	getEvent(StartCounterEvent.class).update(intent);
    }

    public void executeIntent(SegmentsIntent intent) throws TimeoutException, NotConnectedException {
	getDevice().setSegments(intent.getValue(SEGMENTS, short[].class), intent.getValue(BRIGHTNESS, Short.class), intent.getValue(COLON, Boolean.class));
	getEvent(SegmentsEvent.class).update(intent);
    }

    @Override
    public void counterFinished() {
	getEvent(CounterFinishedEvent.class).update(ENABLED, true);
    }

}
