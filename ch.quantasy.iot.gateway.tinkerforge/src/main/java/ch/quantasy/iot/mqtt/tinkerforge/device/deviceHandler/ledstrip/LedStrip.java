/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ledstrip;

import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ledstrip.intent.ConfigIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ledstrip.intent.RGBLEDFrameIntent;
import ch.quantasy.iot.mqtt.tinkerforge.device.deviceHandler.ledstrip.status.ConfigStatus;
import ch.quantasy.iot.mqtt.tinkerforge.device.stackHandler.MQTTTinkerforgeStackHandler;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LedStrip extends ADeviceHandler<BrickletLEDStrip> implements BrickletLEDStrip.FrameRenderedListener {

    public static final String CONFIG_CLOCK_FREQUENCY_OF_ICS_IN_HZ = "clockFrequencyOfICsInHz";
    public static final String CONFIG_FRAME_DURATION_IN_MILLISECONDS = "frameDurationInMilliseconds";
    public static final String CONFIG_CHIP_TYPE = "chipType";
    public static final String CONFIG_NUMBER_OF_LEDS = "numberOfLEDs";

    public static final String RGB_FRAME = "rgbFrame";

    private enum SendState {

	FREE, PREPARING, TO_BE_SENT, SENDING, MAINTENANCE;
    }

    private enum RenderState {

	FREE, RENDERING;
    }

    private boolean isLaging;
    private SendState sendState;
    private RenderState renderState;

    public static final int DEFAULT_NUMBER_OF_LEDS = 50;

    // Frame duration in Milliseconds 20ms (1000ms / 50frames = 20ms per frame)
    public static final int DEFAULT_FRAME_DURATION_IN_MILLISECONDS = 20;

    // IC-refresh in Hz
    public static final int DEFAULT_CLOCK_FREQUENCY_OF_ICS_IN_HZ = 2000000;

    // ChipType
    public static final int DEFAULT_CHIP_TYPE = BrickletLEDStrip.CHIP_TYPE_WS2801;

    private int chipType;
    private int frameDurationInMilliseconds;
    private int clockFrequencyOfICsInHz;
    private BrickletLEDStrip ledStrip;
    private final static int NUMBER_OF_LEDS_PER_WRITE = 16;
    private final static int NUMBER_OF_COLOR_CHANNELS = 3;

    private int numberOfLEDs;
    private int numberOfWrites;

    private short[][][] frame;

    public String getApplicationName() {
	return "LEDStrip";
    }

    public LedStrip(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	super(stackApplication, mqttURI, stackAddress, identityString);
	super.addIntentClass(ConfigIntent.class, RGBLEDFrameIntent.class);
	super.addStatusClass(ConfigStatus.class);
	System.out.println("Concurrent");
	this.setChipType(LedStrip.DEFAULT_CHIP_TYPE);
	this.setClockFrequencyOfICsInHz(LedStrip.DEFAULT_CLOCK_FREQUENCY_OF_ICS_IN_HZ);
	System.out.println("MHz set");
	this.setFrameDurationInMilliseconds(LedStrip.DEFAULT_FRAME_DURATION_IN_MILLISECONDS);
	System.out.println("FrameDuration set");
	this.renderState = LedStrip.RenderState.FREE;
	System.out.println("RenderState set");
	this.sendState = LedStrip.SendState.FREE;
	System.out.println("sendingState set");
	this.setNumberOfLEDs(LedStrip.DEFAULT_NUMBER_OF_LEDS);

	System.out.println("NumLEDs set");
    }

    @Override
    protected void addDeviceListeners() {
	getDevice().addFrameRenderedListener(this);
	try {
	    this.setup();
	} catch (TimeoutException | NotConnectedException ex) {
	    Logger.getLogger(LedStrip.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    protected void removeDeviceListeners() {
	getDevice().removeFrameRenderedListener(this);
    }

    private void setup() throws TimeoutException, NotConnectedException {
	this.setFrameDurationInMilliseconds(this.frameDurationInMilliseconds);
	this.setClockFrequencyOfICsInHz(this.clockFrequencyOfICsInHz);
	this.sendRGBLEDFrame();
    }

    /**
     * Accepts 2-Dimensional arrays of shorts with the length of the LED-Stripe. It will copy the content and will
     * return after that. You are free to reuse the array again.
     *
     * @param numberOfLEDs
     */
    public void setNumberOfLEDs(final int numberOfLEDs) {
	if ((numberOfLEDs < 0) || (numberOfLEDs > 320)) {
	    throw new IllegalArgumentException();
	}
	if (numberOfLEDs == this.numberOfLEDs) {
	    return;
	}
	synchronized (this) {
	    while (this.sendState != LedStrip.SendState.FREE) {
		try {
		    this.wait(250);
		} catch (final InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	    this.sendState = LedStrip.SendState.MAINTENANCE;
	}
	this.numberOfLEDs = numberOfLEDs;
	this.numberOfWrites = (int) Math.ceil((double) this.numberOfLEDs
		/ LedStrip.NUMBER_OF_LEDS_PER_WRITE);
	this.frame = new short[LedStrip.NUMBER_OF_COLOR_CHANNELS][this.numberOfWrites][LedStrip.NUMBER_OF_LEDS_PER_WRITE];
	synchronized (this) {
	    this.sendState = LedStrip.SendState.FREE;
	    this.notifyAll();
	}
    }

    public int getNumberOfLEDs() {
	return this.numberOfLEDs;
    }

    public void setFrameDurationInMilliseconds(
	    final int frameDurationInMilliseconds) throws TimeoutException, NotConnectedException {
	if (frameDurationInMilliseconds < 1) {
	    throw new IllegalArgumentException();
	}
	this.frameDurationInMilliseconds = frameDurationInMilliseconds;
	if (getDevice() != null) {
	    getDevice()
		    .setFrameDuration(this.frameDurationInMilliseconds);

	}

    }

    public int getFrameDurationInMilliseconds() {
	return this.frameDurationInMilliseconds;
    }

    public void setChipType(int chipType) throws TimeoutException, NotConnectedException {
	if (getDevice() != null) {
	    synchronized (this) {
		while (this.sendState != LedStrip.SendState.FREE) {
		    try {
			this.wait(250);
		    } catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
		this.sendState = LedStrip.SendState.MAINTENANCE;
	    }
	    getDevice().setChipType(chipType);
	    synchronized (this) {
		this.sendState = LedStrip.SendState.FREE;
		this.notifyAll();
	    }
	}
    }

    public void setClockFrequencyOfICsInHz(final int clockFrequencyOfICsInHz) throws TimeoutException, NotConnectedException {
	if (clockFrequencyOfICsInHz < 1) {
	    throw new IllegalArgumentException();
	}
	this.clockFrequencyOfICsInHz = clockFrequencyOfICsInHz;
	if (getDevice() != null) {

	    getDevice().setClockFrequency(clockFrequencyOfICsInHz);

	}

    }

    public int getClockFrequencyOfICsInHz() {
	return this.clockFrequencyOfICsInHz;
    }

    /**
     * Returns a new 2-dimensional array of shorts representing all the RGB-LEDs of the strip.
     *
     * @return 3-channel-array of shorts
     */
    public short[][] getFreshRGBLEDs() {
	return new short[LedStrip.NUMBER_OF_COLOR_CHANNELS][this
		.getNumberOfLEDs()];
    }

    public boolean getLagState() {
	return isLaging;
    }

    public void resetLagState() {
	isLaging = false;
    }

    /**
     * This will guarantee the display of the set LEDs! in the next rendering!
     *
     * @param rgbLEDs
     */
    public void setRGBLEDs(final short[][] rgbLEDs) {
	if (rgbLEDs == null) {
	    return;
	}
	if (rgbLEDs.length != LedStrip.NUMBER_OF_COLOR_CHANNELS) {
	    return;
	}

	synchronized (this) {
	    while ((getDevice() != null)
		    && (this.sendState != LedStrip.SendState.FREE)) {
		try {
		    this.wait(50);
		} catch (final InterruptedException e) {
		}

	    }
	    if (getDevice() == null) {
		return;
	    }

	    this.sendState = LedStrip.SendState.PREPARING;
	}

	for (int colorChannel = 0; colorChannel < LedStrip.NUMBER_OF_COLOR_CHANNELS; colorChannel++) {
	    if ((rgbLEDs[colorChannel] == null)
		    || (rgbLEDs[colorChannel].length != this.numberOfLEDs)) {
		return;
	    }
	}

	for (int colorChannel = 0; colorChannel < LedStrip.NUMBER_OF_COLOR_CHANNELS; colorChannel++) {
	    for (int write = 0, remaining = this.numberOfLEDs; write < this.numberOfWrites; write++, remaining -= LedStrip.NUMBER_OF_LEDS_PER_WRITE) {
		System.arraycopy(rgbLEDs[colorChannel],
				 write
				 * LedStrip.NUMBER_OF_LEDS_PER_WRITE,
				 this.frame[colorChannel][write],
				 0,
				 Math.min(
					 remaining,
					 LedStrip.NUMBER_OF_LEDS_PER_WRITE));
	    }
	}
	synchronized (this) {
	    this.sendState = LedStrip.SendState.TO_BE_SENT;
	}
	this.sendRGBLEDFrame();
    }

    /**
     * Either this method is called via setRGBLEDs or via the render-Listener.
     */
    private void sendRGBLEDFrame() {

	synchronized (this) {
	    if (getDevice() == null) {
		this.sendState = LedStrip.SendState.FREE;
		this.notifyAll();
		return;
	    }
	    if (this.renderState == LedStrip.RenderState.RENDERING) {
		return;
	    }
	    if (this.sendState != LedStrip.SendState.TO_BE_SENT) {
		return;
	    }
	    this.sendState = LedStrip.SendState.SENDING;
	    this.renderState = LedStrip.RenderState.RENDERING;
	}
	try {
	    for (int write = 0; write < this.numberOfWrites; write++) {

		getDevice()
			.setRGBValues(write
				* LedStrip.NUMBER_OF_LEDS_PER_WRITE,
				      (short) LedStrip.NUMBER_OF_LEDS_PER_WRITE,
				      this.frame[0][write], this.frame[1][write],
				      this.frame[2][write]);
	    }

	} catch (final TimeoutException e) {
	    // Eh... ok
	} catch (final NotConnectedException e) {
	    // Well...
	} catch (final NullPointerException e) {
	    this.renderState = LedStrip.RenderState.FREE;
	}
	synchronized (this) {
	    this.sendState = LedStrip.SendState.FREE;
	    this.notifyAll();
	}
    }

    @Override
    public void frameRendered(final int length) {
	synchronized (this) {
	    this.isLaging = (this.sendState == LedStrip.SendState.SENDING);
	    if ((this.sendState == LedStrip.SendState.FREE)
		    && (this.renderState == LedStrip.RenderState.RENDERING)) {
		this.renderState = LedStrip.RenderState.FREE;
		this.notifyAll();
	    }

	    if (this.sendState == LedStrip.SendState.TO_BE_SENT) {
		this.renderState = LedStrip.RenderState.FREE;
		this.sendRGBLEDFrame();
	    }
	}
    }

    /**
     * This method allows to describe the strategy of the DeviceHandler for any incoming intent. In this specific case
     * it simply dispatches every intent to the concrete execution.
     *
     * @param intent
     */
    @Override
    public void executeIntent(AnIntent intent) throws Throwable {
	if (intent instanceof ConfigIntent) {
	    executeIntent((ConfigIntent) intent);
	}
	if (intent instanceof RGBLEDFrameIntent) {
	    executeIntent((RGBLEDFrameIntent) intent);
	}
    }

    public void executeIntent(RGBLEDFrameIntent intent) {
	setRGBLEDs(intent.getValue(LedStrip.RGB_FRAME, short[][].class));
    }

    public void executeIntent(ConfigIntent intent) throws TimeoutException, NotConnectedException {
	setChipType(intent.getValue(CONFIG_CHIP_TYPE, Integer.class));
	setClockFrequencyOfICsInHz(intent.getValue(CONFIG_CLOCK_FREQUENCY_OF_ICS_IN_HZ, Long.class).intValue());
	setFrameDurationInMilliseconds(intent.getValue(CONFIG_FRAME_DURATION_IN_MILLISECONDS, Integer.class));
	setNumberOfLEDs(intent.getValue(CONFIG_NUMBER_OF_LEDS, Integer.class));
	getStatus(ConfigStatus.class).update(CONFIG_CHIP_TYPE, getDevice().getChipType());
	getStatus(ConfigStatus.class).update(CONFIG_CLOCK_FREQUENCY_OF_ICS_IN_HZ, getDevice().getClockFrequency());
	getStatus(ConfigStatus.class).update(CONFIG_FRAME_DURATION_IN_MILLISECONDS, getDevice().getFrameDuration());
	getStatus(ConfigStatus.class).update(CONFIG_NUMBER_OF_LEDS, getNumberOfLEDs());
    }
}
