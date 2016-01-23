/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.event.AccelerationEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.event.AllDataEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.event.AngularVelocityEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.event.GravityVectorEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.event.MagneticFieldEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.event.OrientationEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.event.QuaternionEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.intent.AccelerationPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.intent.AllDataPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.intent.AngularVelocityPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.intent.ConvergenceSpeedIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.intent.LEDIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.intent.MagneticFieldPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.intent.OrientationCalculationIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.intent.OrientationPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.intent.QuaternionPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.status.AccelerationPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.status.AllDataPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.status.AngularVelocityPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.status.ConvergenceSpeedStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.status.LEDStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.status.MagneticFieldPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.status.OrientationCalculationStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.status.OrientationPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.IMU.status.QuaternionPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickIMU;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class IMU extends ADeviceHandler<BrickIMU> implements BrickIMU.AccelerationListener, BrickIMU.AllDataListener, BrickIMU.AngularVelocityListener, BrickIMU.MagneticFieldListener, BrickIMU.OrientationListener, BrickIMU.QuaternionListener {

    public static final String PERIOD = "period";
    public static final String ENABLEDs = "enabled";

    public static final String X = "x";
    public static final String Y = "y";
    public static final String Z = "z";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "IMU";
    }

    public IMU(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(AccelerationPeriodStatus.class, AllDataPeriodStatus.class, AngularVelocityPeriodStatus.class, MagneticFieldPeriodStatus.class, QuaternionPeriodStatus.class, LEDStatus.class);
	super.addEventClass(AccelerationEvent.class, AllDataEvent.class, AngularVelocityEvent.class, GravityVectorEvent.class, MagneticFieldEvent.class, OrientationEvent.class, QuaternionEvent.class);
	super.addIntentClass(AccelerationPeriodIntent.class, AllDataPeriodIntent.class, AngularVelocityPeriodIntent.class, MagneticFieldPeriodIntent.class, OrientationPeriodIntent.class, QuaternionPeriodIntent.class, LEDIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addAccelerationListener(this);
	getDevice().addAllDataListener(this);
	getDevice().addAngularVelocityListener(this);

	getDevice().addMagneticFieldListener(this);
	getDevice().addOrientationListener(this);
	getDevice().addQuaternionListener(this);

    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeAccelerationListener(this);
	getDevice().removeAllDataListener(this);
	getDevice().removeAngularVelocityListener(this);

	getDevice().removeMagneticFieldListener(this);
	getDevice().removeOrientationListener(this);
	getDevice().removeQuaternionListener(this);

    }

    public void executeIntent(ConvergenceSpeedIntent intent) throws TimeoutException, NotConnectedException {

	int convergenceSpeed = intent.getValue(VALUE, Integer.class);
	getDevice().setConvergenceSpeed(convergenceSpeed);
	getStatus(ConvergenceSpeedStatus.class).update(VALUE, getDevice().getConvergenceSpeed());
    }

    public void executeIntent(OrientationCalculationIntent intent) throws TimeoutException, NotConnectedException {

	boolean isCalculationOn = intent.getValue(VALUE, Boolean.class);
	if (isCalculationOn) {
	    getDevice().orientationCalculationOn();
	} else {
	    getDevice().orientationCalculationOn();
	}
	getStatus(OrientationCalculationStatus.class).update(VALUE, getDevice().isOrientationCalculationOn());
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
	long period = intent.getValue(IMU.PERIOD, Long.class);
	getDevice().setAccelerationPeriod(period);
	getStatus(AccelerationPeriodStatus.class).update(IMU.PERIOD, getDevice().getAccelerationPeriod());
    }

    public void executeIntent(AllDataPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMU.PERIOD, Long.class);
	getDevice().setAllDataPeriod(period);
	getStatus(AllDataPeriodStatus.class).update(IMU.PERIOD, getDevice().getAllDataPeriod());
    }

    public void executeIntent(AngularVelocityPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMU.PERIOD, Long.class);
	getDevice().setAngularVelocityPeriod(period);
	getStatus(AngularVelocityPeriodStatus.class).update(IMU.PERIOD, getDevice().getAngularVelocityPeriod());
    }

    public void executeIntent(MagneticFieldPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMU.PERIOD, Long.class);
	getDevice().setMagneticFieldPeriod(period);
	getStatus(MagneticFieldPeriodStatus.class).update(IMU.PERIOD, getDevice().getMagneticFieldPeriod());
    }

    public void executeIntent(OrientationPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMU.PERIOD, Long.class);
	getDevice().setOrientationPeriod(period);
	getStatus(OrientationPeriodStatus.class).update(IMU.PERIOD, getDevice().getOrientationPeriod());
    }

    public void executeIntent(QuaternionPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(IMU.PERIOD, Long.class);
	getDevice().setQuaternionPeriod(period);
	getStatus(QuaternionPeriodStatus.class).update(IMU.PERIOD, getDevice().getQuaternionPeriod());
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
    public void magneticField(short s, short s1, short s2) {
	getEvent(MagneticFieldEvent.class).update(VALUE, new Short[]{s, s1, s2});
    }

    @Override
    public void orientation(short s, short s1, short s2) {
	getEvent(OrientationEvent.class).update(VALUE, new Short[]{s, s1, s2});
    }

    @Override
    public void allData(short s, short s1, short s2, short s3, short s4, short s5, short s6, short s7, short s8, short s9) {
	getEvent(AllDataEvent.class).update(VALUE, new Short[]{s, s1, s2, s3, s4, s5, s6, s7, s8, s9});
    }

    @Override
    public void quaternion(float f, float f1, float f2, float f3) {
	getEvent(QuaternionEvent.class).update(VALUE, new Float[]{f, f1, f2, f3});
    }

}
