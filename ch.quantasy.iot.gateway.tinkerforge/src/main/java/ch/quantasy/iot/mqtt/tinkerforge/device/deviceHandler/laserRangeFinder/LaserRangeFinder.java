/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.event.DistanceEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.event.DistanceReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.event.VelocityEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.event.VelocityReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.intent.AveragingIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.intent.DebouncePeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.intent.DistanceCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.intent.DistanceCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.intent.LaserEnableIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.intent.ModeIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.intent.VelocityCallbackPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.intent.VelocityCallbackThresholdIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.status.AveragingStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.status.DebounceStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.status.DistanceCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.status.DistanceCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.status.LaserEnableStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.status.ModeStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.status.VelocityCallbackPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.laserRangeFinder.status.VelocityCallbackThresholdStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletLaserRangeFinder;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LaserRangeFinder extends ADeviceHandler<BrickletLaserRangeFinder> implements BrickletLaserRangeFinder.DistanceListener, BrickletLaserRangeFinder.DistanceReachedListener, BrickletLaserRangeFinder.VelocityListener, BrickletLaserRangeFinder.VelocityReachedListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN = "min";
    public static final String THRESHOLD_MAX = "max";
    public static final String AVERAGE_DISTANCE_LENGTH = "distanceAverageLength";
    public static final String AVERAGE_VELOCITY_LENGTH = "velocityAverageLength";
    public static final String MODE = "mode";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "LaserRangeFinder";
    }

    public LaserRangeFinder(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(AveragingStatus.class, DebounceStatus.class, DistanceCallbackPeriodStatus.class, DistanceCallbackThresholdStatus.class, LaserEnableStatus.class, ModeStatus.class, VelocityCallbackPeriodStatus.class, VelocityCallbackThresholdStatus.class);
	super.addEventClass(DistanceEvent.class, DistanceReachedEvent.class, VelocityEvent.class, VelocityReachedEvent.class);
	super.addIntentClass(AveragingIntent.class, DebouncePeriodIntent.class, DistanceCallbackPeriodIntent.class, DistanceCallbackThresholdIntent.class, LaserEnableIntent.class, ModeIntent.class, VelocityCallbackPeriodIntent.class, VelocityCallbackThresholdIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addDistanceListener(this);
	getDevice().addDistanceReachedListener(this);
	getDevice().addVelocityListener(this);
	getDevice().addVelocityReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeDistanceListener(this);
	getDevice().removeDistanceReachedListener(this);
	getDevice().removeVelocityListener(this);
	getDevice().removeVelocityReachedListener(this);
    }

    public void executeIntent(ModeIntent intent) throws TimeoutException, NotConnectedException {
	short mode = intent.getValue(LaserRangeFinder.MODE, Short.class);
	getDevice().setMode(mode);
	getStatus(LaserEnableStatus.class).update(MODE, getDevice().getMode());
    }

    public void executeIntent(LaserEnableIntent intent) throws TimeoutException, NotConnectedException {
	boolean laserStatus = intent.getValue(LaserRangeFinder.ENABLED, Boolean.class);
	if (laserStatus) {
	    getDevice().enableLaser();
	} else {
	    getDevice().disableLaser();
	}
	getStatus(LaserEnableStatus.class).update(ENABLED, getDevice().isLaserEnabled());
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(LaserRangeFinder.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(LaserRangeFinder.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(DistanceCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(LaserRangeFinder.PERIOD, Long.class);
	getDevice().setDistanceCallbackPeriod(period);
	getStatus(DistanceCallbackPeriodStatus.class).update(PERIOD, getDevice().getDistanceCallbackPeriod());
    }

    public void executeIntent(VelocityCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(LaserRangeFinder.PERIOD, Long.class);
	getDevice().setVelocityCallbackPeriod(period);
	getStatus(VelocityCallbackThresholdStatus.class).update(PERIOD, getDevice().getVelocityCallbackPeriod());
    }

    public void executeIntent(DistanceCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(LaserRangeFinder.THRESHOLD_OPTION, Character.class);
	if (option == 's') {
	    option = '<';
	}
	if (option == 'g') {
	    option = '>';
	}
	int min = intent.getValue(LaserRangeFinder.THRESHOLD_MIN, Integer.class);
	int max = intent.getValue(LaserRangeFinder.THRESHOLD_MAX, Integer.class);
	getDevice().setDistanceCallbackThreshold(option, min, max);
	getStatus(DistanceCallbackThresholdStatus.class).update(intent);
    }

    public void executeIntent(VelocityCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(LaserRangeFinder.THRESHOLD_OPTION, Character.class);
	if (option == 's') {
	    option = '<';
	}
	if (option == 'g') {
	    option = '>';
	}
	short min = intent.getValue(LaserRangeFinder.THRESHOLD_MIN, Short.class);
	short max = intent.getValue(LaserRangeFinder.THRESHOLD_MAX, Short.class);
	getDevice().setVelocityCallbackThreshold(option, min, max);
	getStatus(VelocityCallbackThresholdStatus.class).update(intent);
    }

    public void executeIntent(AveragingIntent intent) throws TimeoutException, NotConnectedException {
	short averageDistance = intent.getContent(LaserRangeFinder.AVERAGE_DISTANCE_LENGTH).getValue(Short.class);
	short averageVelocity = intent.getContent(LaserRangeFinder.AVERAGE_VELOCITY_LENGTH).getValue(Short.class);

	getDevice().setMovingAverage(averageDistance, averageVelocity);
	BrickletLaserRangeFinder.MovingAverage averaging = getDevice().getMovingAverage();
	getStatus(AveragingStatus.class).update(LaserRangeFinder.AVERAGE_DISTANCE_LENGTH, averaging.distanceAverageLength);
	getStatus(AveragingStatus.class).update(LaserRangeFinder.AVERAGE_VELOCITY_LENGTH, averaging.velocityAverageLength);
    }

    @Override
    public void distance(int i) {
	getEvent(DistanceEvent.class).update(VALUE, i);
    }

    @Override
    public void distanceReached(int i) {
	getEvent(DistanceReachedEvent.class).update(VALUE, i);
    }

    @Override
    public void velocity(short s) {
	getEvent(VelocityEvent.class).update(VALUE, s);
    }

    @Override
    public void velocityReached(short s) {
	getEvent(VelocityReachedEvent.class).update(VALUE, s);
    }

}
