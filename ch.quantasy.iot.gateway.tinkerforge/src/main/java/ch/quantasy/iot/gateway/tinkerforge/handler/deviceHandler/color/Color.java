/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color;

import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.event.ColorEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.event.ColorReachedEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.event.IlluminanceEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.event.TemperatureEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.intent.ColorCallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.intent.ColorCallbackThresholdIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.intent.ColorConfigIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.intent.ColorTemperatureCallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.intent.DebouncePeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.intent.IlluminanceCallbackPeriodIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.intent.LightIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.status.ColorCallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.status.ColorCallbackThresholdStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.status.ColorConfigStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.status.ColorTemperatureCallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.status.DebounceStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.status.IlluminanceCallbackPeriodStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.color.status.LightStatus;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.base.ADeviceHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletColor;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class Color extends ADeviceHandler<BrickletColor> implements BrickletColor.ColorListener, BrickletColor.ColorReachedListener, BrickletColor.ColorTemperatureListener, BrickletColor.IlluminanceListener {

    public static final String PERIOD = "period";
    public static final String THRESHOLD_OPTION = "option";
    public static final String THRESHOLD_MIN_R = "minR";
    public static final String THRESHOLD_MAX_R = "maxR";
    public static final String THRESHOLD_MIN_G = "minG";
    public static final String THRESHOLD_MAX_G = "maxG";
    public static final String THRESHOLD_MIN_B = "minB";
    public static final String THRESHOLD_MAX_B = "maxB";
    public static final String THRESHOLD_MIN_C = "minC";
    public static final String THRESHOLD_MAX_C = "maxC";
    public static final String RED = "red";
    public static final String GREEN = "green";
    public static final String BLUE = "blue";
    public static final String CLEAR = "clear";
    public static final String TEMPERATURE = "temperature";
    public static final String ILLUMINANCE = "illuminance";

    public static final String GAIN = "gain";
    public static final String INTEGRATION_TIME = "integrationTime";

    public static final String ENABLED = "enabled";
    public static final String LIGHT_ON = "lightOn";
    public static final String VALUE = "value";

    public String getApplicationName() {
	return "Color";
    }

    public Color(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addStatusClass(ColorConfigStatus.class, ColorCallbackPeriodStatus.class, ColorCallbackThresholdStatus.class, IlluminanceCallbackPeriodStatus.class, ColorTemperatureCallbackPeriodStatus.class, DebounceStatus.class, LightStatus.class);
	super.addEventClass(ColorEvent.class, TemperatureEvent.class, IlluminanceEvent.class, ColorReachedEvent.class);
	super.addIntentClass(ColorConfigIntent.class, ColorCallbackPeriodIntent.class, ColorCallbackThresholdIntent.class, IlluminanceCallbackPeriodIntent.class, ColorTemperatureCallbackPeriodIntent.class, DebouncePeriodIntent.class, LightIntent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addColorListener(this);
	getDevice().addColorReachedListener(this);
	getDevice().addColorTemperatureListener(this);
	getDevice().addIlluminanceListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeColorListener(this);
	getDevice().removeColorReachedListener(this);
	getDevice().removeColorTemperatureListener(this);
	getDevice().removeIlluminanceListener(this);
    }

    /**
     * This method allows to describe the strategy of the DeviceHandler for any incoming intent. In this specific case
     * it simply dispatches every intent to the concrete execution.
     *
     * @param intent
     */
    public void executeIntent(AnIntent intent) throws Throwable {
	if (intent instanceof DebouncePeriodIntent) {
	    executeIntent((DebouncePeriodIntent) intent);
	}
	if (intent instanceof ColorCallbackPeriodIntent) {
	    executeIntent((ColorCallbackPeriodIntent) intent);
	}
	if (intent instanceof IlluminanceCallbackPeriodIntent) {
	    executeIntent((IlluminanceCallbackPeriodIntent) intent);
	}
	if (intent instanceof ColorTemperatureCallbackPeriodIntent) {
	    executeIntent((ColorTemperatureCallbackPeriodIntent) intent);
	}
	if (intent instanceof ColorCallbackThresholdIntent) {
	    executeIntent((ColorCallbackThresholdIntent) intent);
	}
	if (intent instanceof LightIntent) {
	    executeIntent((LightIntent) intent);
	}
    }

    public void executeIntent(DebouncePeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Color.PERIOD, Long.class);
	getDevice().setDebouncePeriod(period);
	getStatus(DebounceStatus.class).update(Color.PERIOD, getDevice().getDebouncePeriod());
    }

    public void executeIntent(ColorCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Color.PERIOD, Long.class);
	getDevice().setColorCallbackPeriod(period);
	getStatus(ColorCallbackPeriodStatus.class).update(PERIOD, getDevice().getColorCallbackPeriod());
    }

    public void executeIntent(IlluminanceCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long period = intent.getValue(Color.PERIOD, Long.class);
	getDevice().setIlluminanceCallbackPeriod(period);
	getStatus(IlluminanceCallbackPeriodStatus.class).update(PERIOD, getDevice().getIlluminanceCallbackPeriod());
    }

    public void executeIntent(ColorCallbackThresholdIntent intent) throws TimeoutException, NotConnectedException {
	char option = intent.getValue(Color.THRESHOLD_OPTION, Character.class);
	int minR = intent.getValue(Color.THRESHOLD_MIN_R, Integer.class);
	int maxR = intent.getValue(Color.THRESHOLD_MAX_R, Integer.class);
	int minG = intent.getValue(Color.THRESHOLD_MIN_G, Integer.class);
	int maxG = intent.getValue(Color.THRESHOLD_MAX_G, Integer.class);
	int minB = intent.getValue(Color.THRESHOLD_MIN_B, Integer.class);
	int maxB = intent.getValue(Color.THRESHOLD_MAX_B, Integer.class);
	int minC = intent.getValue(Color.THRESHOLD_MIN_C, Integer.class);
	int maxC = intent.getValue(Color.THRESHOLD_MAX_C, Integer.class);
	getDevice().setColorCallbackThreshold(option, minR, maxR, minG, maxG, minB, maxB, minC, maxC);
	getStatus(ColorCallbackThresholdStatus.class).updateIntent(intent);
    }

    public void executeIntent(ColorTemperatureCallbackPeriodIntent intent) throws TimeoutException, NotConnectedException {
	long option = intent.getValue(Color.PERIOD, Long.class);
	getDevice().setColorTemperatureCallbackPeriod(option);
	getStatus(ColorTemperatureCallbackPeriodStatus.class).update(PERIOD, getDevice().getColorTemperatureCallbackPeriod());
    }

    public void executeIntent(LightIntent intent) throws TimeoutException, NotConnectedException {
	boolean isLightOn = intent.getValue(LIGHT_ON, Boolean.class);
	if (isLightOn) {
	    getDevice().lightOn();
	} else {
	    getDevice().lightOff();
	}
	getStatus(LightStatus.class).update(LIGHT_ON, getDevice().isLightOn());
    }

    @Override
    public void color(int i, int i1, int i2, int i3) {
	getEvent(ColorEvent.class).update(RED, i);
	getEvent(ColorEvent.class).update(GREEN, i1);
	getEvent(ColorEvent.class).update(BLUE, i2);
	getEvent(ColorEvent.class).update(CLEAR, i3);
    }

    @Override
    public void colorReached(int i, int i1, int i2, int i3) {
	getEvent(ColorReachedEvent.class).update(RED, i);
	getEvent(ColorReachedEvent.class).update(GREEN, i1);
	getEvent(ColorReachedEvent.class).update(BLUE, i2);
	getEvent(ColorReachedEvent.class).update(CLEAR, i3);
    }

    @Override
    public void colorTemperature(int i) {
	getEvent(TemperatureEvent.class).update(VALUE, i);
    }

    @Override
    public void illuminance(long l) {
	getEvent(IlluminanceEvent.class).update(VALUE, l);
    }

}
