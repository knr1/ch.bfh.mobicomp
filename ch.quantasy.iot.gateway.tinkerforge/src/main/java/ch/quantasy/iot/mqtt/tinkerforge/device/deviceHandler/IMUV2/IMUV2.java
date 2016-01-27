/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.event.AccelerationEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.event.AllDataEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.event.AngularVelocityEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.event.GravityVectorEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.event.LinearAccelerationEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.event.MagneticFieldEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.event.OrientationEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.event.QuaternionEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.intent.AccelerationPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.intent.AllDataPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.intent.AngularVelocityPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.intent.GravityPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.intent.LEDIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.intent.LinearAccelerationPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.intent.MagneticFieldPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.intent.OrientationPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.intent.QuaternionPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.intent.TemperaturePeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.status.AccelerationPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.status.AllDataPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.status.AngularVelocityPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.status.GravityPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.status.LEDStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.status.LinearAccelerationPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.status.MagneticFieldPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.status.OrientationPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.status.QuaternionPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMUV2.status.TemperaturePeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.color.event.TemperatureEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickIMUV2;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class IMUV2 extends ADeviceHandler<BrickIMUV2> implements BrickIMUV2.AccelerationListener, BrickIMUV2.AllDataListener, BrickIMUV2.AngularVelocityListener, BrickIMUV2.GravityVectorListener, BrickIMUV2.LinearAccelerationListener,
	BrickIMUV2.MagneticFieldListener, BrickIMUV2.OrientationListener, BrickIMUV2.QuaternionListener, BrickIMUV2.TemperatureListener {

    public static final String PERIOD = "period";
    public static final String ENABLEDs = "enabled";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "IMUV2";
    }

    public IMUV2(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(AccelerationPeriodStatus.class, AllDataPeriodStatus.class, AngularVelocityPeriodStatus.class, GravityPeriodStatus.class, LinearAccelerationPeriodStatus.class, MagneticFieldPeriodStatus.class, QuaternionPeriodStatus.class, TemperaturePeriodStatus.class, LEDStatus.class);
	super.addEventClass(AccelerationEvent.class, AllDataEvent.class, AngularVelocityEvent.class, GravityVectorEvent.class, LinearAccelerationEvent.class, MagneticFieldEvent.class, OrientationEvent.class, QuaternionEvent.class, TemperatureEvent.class);
	super.addIntentClass(AccelerationPeriodIntent.class, AllDataPeriodIntent.class, AngularVelocityPeriodIntent.class, GravityPeriodIntent.class, LinearAccelerationPeriodIntent.class, MagneticFieldPeriodIntent.class, OrientationPeriodIntent.class, QuaternionPeriodIntent.class, TemperaturePeriodIntent.class, LEDIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addAccelerationListener(this);
	getDevice().addAllDataListener(this);
	getDevice().addAngularVelocityListener(this);
	getDevice().addGravityVectorListener(this);
	getDevice().addLinearAccelerationListener(this);
	getDevice().addMagneticFieldListener(this);
	getDevice().addOrientationListener(this);
	getDevice().addQuaternionListener(this);
	getDevice().addTemperatureListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeAccelerationListener(this);
	getDevice().removeAllDataListener(this);
	getDevice().removeAngularVelocityListener(this);
	getDevice().removeGravityVectorListener(this);
	getDevice().removeLinearAccelerationListener(this);
	getDevice().removeMagneticFieldListener(this);
	getDevice().removeOrientationListener(this);
	getDevice().removeQuaternionListener(this);
	getDevice().removeTemperatureListener(this);

    }

    public void executeIntent(LEDIntent intent) throws TimeoutException, NotConnectedException {

	boolean isLightOn = intent.getValue(VALUE, Boolean.class);
	if (isLightOn) {
	    getDevice().ledsOn();
	} else {
	    getDevice().ledsOff();
	}
	getStatus(LEDStatus.class).update(VALUE, getDevice().areLedsOn());
    }

    public void executeIntent(AccelerationPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMUV2.PERIOD, Long.class);
	getDevice().setAccelerationPeriod(period);
	getStatus(AccelerationPeriodStatus.class).update(IMUV2.PERIOD, getDevice().getAccelerationPeriod());
    }

    public void executeIntent(AllDataPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMUV2.PERIOD, Long.class);
	getDevice().setAllDataPeriod(period);
	getStatus(AllDataPeriodStatus.class).update(IMUV2.PERIOD, getDevice().getAllDataPeriod());
    }

    public void executeIntent(AngularVelocityPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMUV2.PERIOD, Long.class);
	getDevice().setAngularVelocityPeriod(period);
	getStatus(AngularVelocityPeriodStatus.class).update(IMUV2.PERIOD, getDevice().getAngularVelocityPeriod());
    }

    public void executeIntent(GravityPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMUV2.PERIOD, Long.class);
	getDevice().setGravityVectorPeriod(period);
	getStatus(GravityPeriodStatus.class).update(IMUV2.PERIOD, getDevice().getGravityVectorPeriod());
    }

    public void executeIntent(LinearAccelerationPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMUV2.PERIOD, Long.class);
	getDevice().setLinearAccelerationPeriod(period);
	getStatus(LinearAccelerationPeriodStatus.class).update(IMUV2.PERIOD, getDevice().getLinearAccelerationPeriod());
    }

    public void executeIntent(MagneticFieldPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMUV2.PERIOD, Long.class);
	getDevice().setMagneticFieldPeriod(period);
	getStatus(MagneticFieldPeriodStatus.class).update(IMUV2.PERIOD, getDevice().getMagneticFieldPeriod());
    }

    public void executeIntent(OrientationPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMUV2.PERIOD, Long.class);
	getDevice().setOrientationPeriod(period);
	getStatus(OrientationPeriodStatus.class).update(IMUV2.PERIOD, getDevice().getOrientationPeriod());
    }

    public void executeIntent(QuaternionPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMUV2.PERIOD, Long.class);
	getDevice().setQuaternionPeriod(period);
	getStatus(QuaternionPeriodStatus.class).update(IMUV2.PERIOD, getDevice().getQuaternionPeriod());
    }

    public void executeIntent(TemperaturePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMUV2.PERIOD, Long.class);
	getDevice().setTemperaturePeriod(period);
	getStatus(TemperaturePeriodStatus.class).update(IMUV2.PERIOD, getDevice().getTemperaturePeriod());
    }

    @Override
    public void acceleration(short s, short s1, short s2) {
	getEvent(AccelerationEvent.class).update(VALUE, new Short[]{s, s1, s2});
    }

    @Override
    public void angularVelocity(short s, short s1, short s2) {
	getEvent(AngularVelocityEvent.class).update(VALUE, new Short[]{s, s1, s2});
    }

    @Override
    public void gravityVector(short s, short s1, short s2) {
	getEvent(GravityVectorEvent.class).update(VALUE, new Short[]{s, s1, s2});
    }

    @Override
    public void linearAcceleration(short s, short s1, short s2) {
	getEvent(LinearAccelerationEvent.class).update(VALUE, new Short[]{s, s1, s2});
    }

    @Override
    public void magneticField(short s, short s1, short s2) {
	getEvent(MagneticFieldEvent.class).update(VALUE, new Short[]{s, s1, s2});
    }

    @Override
    public void orientation(short s, short s1, short s2) {
	getEvent(OrientationEvent.class).update(VALUE, new Short[]{s, s1, s2});
    }

    @Override
    public void quaternion(short s, short s1, short s2, short s3) {
	getEvent(QuaternionEvent.class).update(VALUE, new Short[]{s, s1, s2, s3});
    }

    @Override
    public void temperature(byte b) {
	getEvent(TemperatureEvent.class).update(VALUE, b);
    }

    @Override
    public void allData(short[] shorts, short[] shorts1, short[] shorts2, short[] shorts3, short[] shorts4, short[] shorts5, short[] shorts6, byte b, short s) {
	getEvent(AllDataEvent.class).update(VALUE, new short[][]{shorts, shorts1, shorts2, shorts3, shorts4, shorts5, shorts6});
    }

}
