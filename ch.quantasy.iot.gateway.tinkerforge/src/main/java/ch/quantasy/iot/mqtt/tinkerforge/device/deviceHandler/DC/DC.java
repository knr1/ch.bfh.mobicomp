/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.event.CurrentVelocityEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.event.EmergencyShutdownEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.event.FullBrakeEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.event.UnderVoltageEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.event.VelocityReachedEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.intent.AccelerationIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.intent.CurrentVelocityPeriodIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.intent.DriveModeIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.intent.EnabledIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.intent.FullBrakeIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.intent.LEDIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.intent.MinimumVoltageIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.intent.PWMFrequencyIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.intent.VelocityIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.status.AccelerationStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.status.CurrentVelocityPeriodStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.status.DriveModeStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.status.EnabledStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.status.LEDStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.status.MinimumVoltageStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.status.PWMFrequencyStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.DC.status.VelocityStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickDC;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DC extends ADeviceHandler<BrickDC> implements BrickDC.CurrentVelocityListener, BrickDC.EmergencyShutdownListener, BrickDC.UnderVoltageListener, BrickDC.VelocityReachedListener {

    public static final String PERIOD = "period";

    public static final String VALUE = "value";

    public String getApplicationName() {
	return "DC";
    }

    public DC(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(AccelerationStatus.class, CurrentVelocityPeriodStatus.class, DriveModeStatus.class, EnabledStatus.class, LEDStatus.class, MinimumVoltageStatus.class, PWMFrequencyStatus.class, VelocityStatus.class);
	super.addEventClass(CurrentVelocityEvent.class, EmergencyShutdownEvent.class, FullBrakeEvent.class, UnderVoltageEvent.class, VelocityReachedEvent.class);
	super.addIntentClass(AccelerationIntent.class, CurrentVelocityPeriodIntent.class, DriveModeIntent.class, EnabledIntent.class, FullBrakeIntent.class, LEDIntent.class, MinimumVoltageIntent.class, PWMFrequencyIntent.class, VelocityIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addCurrentVelocityListener(this);
	getDevice().addEmergencyShutdownListener(this);
	getDevice().addUnderVoltageListener(this);
	getDevice().addVelocityReachedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeCurrentVelocityListener(this);
	getDevice().removeEmergencyShutdownListener(this);
	getDevice().removeUnderVoltageListener(this);
	getDevice().removeVelocityReachedListener(this);
    }

    public void executeIntent(LEDIntent intent) throws TimeoutException, NotConnectedException {

	boolean isLightOn = intent.getValue(VALUE, Boolean.class);
	if (isLightOn) {
	    getDevice().enableStatusLED();
	} else {
	    getDevice().disableStatusLED();
	}
	getStatus(LEDStatus.class).update(VALUE, getDevice().isStatusLEDEnabled());
    }

    public void executeIntent(EnabledIntent intent) throws TimeoutException, NotConnectedException {
	boolean inEnabled = intent.getValue(VALUE, Boolean.class);
	if (inEnabled) {
	    getDevice().enable();
	} else {
	    getDevice().disable();
	}
	getStatus(EnabledStatus.class).update(VALUE, getDevice().isEnabled());
    }

    public void executeIntent(FullBrakeIntent intent) throws TimeoutException, NotConnectedException {
	boolean fullBrake = intent.getValue(ENABLED, Boolean.class);
	if (fullBrake) {
	    getDevice().fullBrake();
	}
	getEvent(FullBrakeEvent.class).update(intent);
    }

    public void executeIntent(AccelerationIntent intent) throws TimeoutException, NotConnectedException {
	int acceleration = intent.getValue(DC.VALUE, Integer.class);
	getDevice().setAcceleration(acceleration);
	getStatus(AccelerationStatus.class).update(DC.VALUE, getDevice().getAcceleration());
    }

    public void executeIntent(CurrentVelocityPeriodIntent intent) throws TimeoutException, NotConnectedException {
	int period = intent.getValue(DC.PERIOD, Integer.class);
	getDevice().setCurrentVelocityPeriod(period);
	getStatus(CurrentVelocityPeriodStatus.class).update(PERIOD, getDevice().getCurrentVelocityPeriod());
    }

    public void executeIntent(DriveModeIntent intent) throws TimeoutException, NotConnectedException {
	short value = intent.getValue(VALUE, Short.class);
	getDevice().setDriveMode(value);
	getStatus(DriveModeStatus.class).update(VALUE, getDevice().getDriveMode());
    }

    public void executeIntent(MinimumVoltageIntent intent) throws TimeoutException, NotConnectedException {
	int value = intent.getValue(VALUE, Integer.class);
	getDevice().setMinimumVoltage(value);
	getStatus(MinimumVoltageStatus.class).update(VALUE, getDevice().getMinimumVoltage());
    }

    public void executeIntent(PWMFrequencyIntent intent) throws TimeoutException, NotConnectedException {
	int value = intent.getValue(DC.VALUE, Integer.class);
	getDevice().setPWMFrequency(value);
	getStatus(PWMFrequencyStatus.class).update(VALUE, getDevice().getPWMFrequency());
    }

    public void executeIntent(VelocityIntent intent) throws TimeoutException, NotConnectedException {
	short value = intent.getValue(DC.VALUE, Short.class);
	getDevice().setVelocity(value);
	getStatus(VelocityStatus.class).update(VALUE, getDevice().getVelocity());
    }

    @Override
    public void currentVelocity(short s) {
	getEvent(CurrentVelocityEvent.class).update(VALUE, s);
    }

    @Override
    public void emergencyShutdown() {
	getEvent(EmergencyShutdownEvent.class).update(VALUE, true);
    }

    @Override
    public void underVoltage(int i) {
	getEvent(UnderVoltageEvent.class).update(VALUE, i);
    }

    @Override
    public void velocityReached(short s) {
	getEvent(VelocityReachedEvent.class).update(VALUE, s);
    }

}
