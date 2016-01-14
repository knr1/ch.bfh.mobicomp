/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4;

import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.event.LCD20x4ButtonEvent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.intent.LCD20x4BacklightIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.intent.LCD20x4CursorConfigIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.intent.LCD20x4WriteIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.status.LCD20x4BacklightStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.lcd20x4.status.LCD20x4CursorConfigStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletLCD20x4;
import java.net.URI;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LCD20x4 extends ADeviceHandler<BrickletLCD20x4> implements BrickletLCD20x4.ButtonPressedListener, BrickletLCD20x4.ButtonReleasedListener {

    public static final String FROM = "from";
    public static final String LINE = "line";
    public static final String TO = "to";
    public static final String TEXT = "text";

    public static final String CURSOR_ENABLED = "cursorEnabled";
    public static final String BLINKING = "blinking";
    public static final String CLEAR_DISPLAY = "clearDisplay";
    public static final String BACKLIGHT = "backlight";
    public static final String CONFIG = "config";
    public static final String BUTTON_0 = "0";
    public static final String BUTTON_1 = "1";
    public static final String BUTTON_2 = "2";
    public static final String BUTTON_3 = "3";
    private final String[] buttons = new String[]{BUTTON_0, BUTTON_1, BUTTON_2, BUTTON_3};

    public static final String CUSTOM_CHARACTER_SET = "customCharacterSet";
    public static final String DEFAULT_TEXT = "defaultText";
    public static final String DEFAULT_TEXT_COUNTER = "defaultTextCounter";
    public static final String WRITE_LINE = "writeLine";

    public String getApplicationName() {
	return "LCD20x4";
    }

    public LCD20x4(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);

	super.addIntentClass(LCD20x4BacklightIntent.class, LCD20x4CursorConfigIntent.class, LCD20x4WriteIntent.class);
	super.addStatusClass(LCD20x4BacklightStatus.class, LCD20x4CursorConfigStatus.class);
	super.addEventClass(LCD20x4ButtonEvent.class);
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addButtonPressedListener(this);
	getDevice().addButtonReleasedListener(this);
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeButtonPressedListener(this);
	getDevice().removeButtonReleasedListener(this);
    }

    public void executeIntent(LCD20x4BacklightIntent intent) throws Throwable {
	boolean isEnabled = intent.getValue(ENABLED, Boolean.class);
	if (isEnabled) {
	    getDevice().backlightOn();
	} else {
	    getDevice().backlightOff();
	}
	getStatus(LCD20x4BacklightStatus.class).update(ENABLED, getDevice().isBacklightOn());
    }

    public void executeIntent(LCD20x4CursorConfigIntent intent) throws Throwable {
	boolean cursorEnabled = intent.getValue(CURSOR_ENABLED, Boolean.class);
	boolean cursorBlinking = intent.getValue(BLINKING, Boolean.class);
	getDevice().setConfig(cursorEnabled, cursorBlinking);
	getStatus(LCD20x4CursorConfigStatus.class).update(intent);
    }

    public void executeIntent(LCD20x4WriteIntent intent) throws Throwable {
	short line = intent.getValue(LINE, Short.class);
	short position = intent.getValue(FROM, Short.class);
	String text = intent.getValue(TEXT, String.class);
	getDevice().writeLine(line, position, text);

    }

    @Override
    public void buttonPressed(short s) {
	getEvent(LCD20x4ButtonEvent.class).update(buttons[s], true);
    }

    @Override
    public void buttonReleased(short s) {
	getEvent(LCD20x4ButtonEvent.class).update(buttons[s], false);

    }
}
