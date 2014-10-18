/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceLED;

import ch.quantasy.messagebus.message.definition.Content;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;
import ch.quantasy.tinkerbus.service.device.core.ATinkerforgeDeviceService;
import ch.quantasy.tinkerbus.service.device.deviceAmbientLight.TinkerforgeAmbientLightService;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.ChipTypeContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.FrameDurationInMilliSecondsContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.ICClockFrequencyInHzContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.NumberOfLEDsContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.RGBLEDsContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.message.TinkerforgeLEDEvent;
import ch.quantasy.tinkerbus.service.device.deviceLED.message.TinkerforgeLEDIntent;
import ch.quantasy.tinkerbus.service.device.message.ATinkerforgeDeviceEvent;
import ch.quantasy.tinkerbus.service.device.message.ATinkerforgeDeviceIntent;
import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class can be used in order to send an 'image' that has to be displayed on an LED-stripe. It is best to get a
 * fresh 'frame' by {@link #getFreshRGBLEDs()}. Then 'drawing' the image within this array and finally by setting it by
 * {@link #setRGBLEDs(short[][])}. This method will make a copy of the data. So once this method returns, the array can
 * be reused to 'draw' the next 'image'.
 *
 * @author reto
 *
 */
public class TinkerforgeLEDService extends ATinkerforgeDeviceService<BrickletLEDStrip, TinkerforgeLEDIntent, TinkerforgeLEDEvent> implements BrickletLEDStrip.FrameRenderedListener {

    public final static short[] DEFAULT_GAMMA_CORRECTION = {
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1,
	1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2,
	2, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5,
	5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9, 10,
	10, 10, 11, 11, 11, 12, 12, 13, 13, 13, 14, 14, 15, 15, 16, 16,
	17, 17, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 24, 24, 25,
	25, 26, 27, 27, 28, 29, 29, 30, 31, 32, 32, 33, 34, 35, 35, 36,
	37, 38, 39, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 50,
	51, 52, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 66, 67, 68,
	69, 70, 72, 73, 74, 75, 77, 78, 79, 81, 82, 83, 85, 86, 87, 89,
	90, 92, 93, 95, 96, 98, 99, 101, 102, 104, 105, 107, 109, 110, 112, 114,
	115, 117, 119, 120, 122, 124, 126, 127, 129, 131, 133, 135, 137, 138, 140, 142,
	144, 146, 148, 150, 152, 154, 156, 158, 160, 162, 164, 167, 169, 171, 173, 175,
	177, 180, 182, 184, 186, 189, 191, 193, 196, 198, 200, 203, 205, 208, 210, 213,
	215, 218, 220, 223, 225, 228, 231, 233, 236, 239, 241, 244, 247, 249, 252, 255};

    @Override
    protected void updateSettingsOnDevice(BrickletLEDStrip device, Map<Class, Content> contentMap) {
	if (contentMap == null || contentMap.isEmpty() || device == null) {
	    return;
	}
	try {
	    {
		ChipTypeContent content = ((ChipTypeContent) (contentMap.get(ChipTypeContent.class)));
		if (content != null) {
		    Integer value = content.getValue();
		    if (value != null) {
			device.setChipType(value);
		    }
		}
	    }
	    {
		ICClockFrequencyInHzContent content = ((ICClockFrequencyInHzContent) (contentMap.get(ICClockFrequencyInHzContent.class)));
		if (content != null) {
		    Long value = content.getValue();
		    if (value != null) {
			//	device.setClockFrequency(value);
		    }
		}
	    }
	    {
		FrameDurationInMilliSecondsContent content = ((FrameDurationInMilliSecondsContent) (contentMap.get(FrameDurationInMilliSecondsContent.class)));
		if (content != null) {
		    Integer value = content.getValue();
		    if (value != null) {
			device.setFrameDuration(value);
		    }
		}
	    }

	    {
		NumberOfLEDsContent content = ((NumberOfLEDsContent) (contentMap.get(NumberOfLEDsContent.class)));
		if (content != null) {
		    Integer value = content.getValue();
		    if (value != null) {
			this.setNumberOfLEDs(value);
		    }
		}
	    }
	    {
		RGBLEDsContent content = ((RGBLEDsContent) (contentMap.get(RGBLEDsContent.class)));
		if (content != null) {
		    short[][] value = content.getValue();
		    if (value != null) {
			this.setRGBLEDs(value);
		    }
		}
	    }
	} catch (TimeoutException ex) {
	    Logger.getLogger(TinkerforgeAmbientLightService.class.getName()).log(Level.SEVERE, null, ex);
	} catch (NotConnectedException ex) {
	    Logger.getLogger(TinkerforgeAmbientLightService.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    @Override
    public TinkerforgeLEDEvent createEvent() {
	return new Event(getDeviceContent(), this);
    }

    @Override
    protected void updateListeners(BrickletLEDStrip device) {
	device.addFrameRenderedListener(this);
    }

    @Override
    public void handleTinkerforgeIntent(TinkerforgeLEDIntent message) {
	//Nothing special
    }

    private enum SendState {

	FREE, PREPARING, TO_BE_SENT, SENDING, MAINTENANCE;
    }

    private enum RenderState {

	FREE, RENDERING;
    }

    private boolean isLaging;
    private SendState sendState = SendState.FREE;
    private RenderState renderState = RenderState.FREE;

    private boolean isGammaCorrection;

    private final static int NUMBER_OF_LEDS_PER_WRITE = 16;
    private final static int NUMBER_OF_COLOR_CHANNELS = 3;

    private int numberOfLEDs;
    private int numberOfWrites;

    private short[][][] frame;

    public TinkerforgeLEDService(BrickletLEDStrip device, String deviceID) {
	super(device, deviceID);
	sendRGBLEDFrame();

    }

    /**
     * Accepts 2-Dimensional arrays of shorts with the length of the LED-Stripe. It will copy the content and will
     * return after that. You are free to reuse the array again.
     *
     * @param numberOfLEDs
     */
    public void setNumberOfLEDs(final int numberOfLEDs) {
	if (numberOfLEDs == this.numberOfLEDs) {
	    return;
	}
	synchronized (this) {
	    if (this.sendState == null) {
		this.sendState = SendState.FREE;
	    }
	    while (this.sendState != SendState.FREE) {
		try {
		    this.wait(250);
		} catch (final InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	    this.sendState = SendState.MAINTENANCE;
	}
	this.numberOfLEDs = numberOfLEDs;
	this.numberOfWrites = (int) Math.ceil((double) this.numberOfLEDs
		/ TinkerforgeLEDService.NUMBER_OF_LEDS_PER_WRITE);
	this.frame = new short[TinkerforgeLEDService.NUMBER_OF_COLOR_CHANNELS][this.numberOfWrites][TinkerforgeLEDService.NUMBER_OF_LEDS_PER_WRITE];
	synchronized (this) {
	    this.sendState = SendState.FREE;
	    this.notifyAll();
	}
    }

    public int getNumberOfLEDs() {
	return this.numberOfLEDs;
    }

    /**
     * Returns a new 2-dimensional array of shorts representing all the RGB-LEDs of the strip.
     *
     * @return 3-channel-array of shorts
     */
    public static short[][] getFreshRGBLEDs(int numberOfLEDs) {
	return new short[TinkerforgeLEDService.NUMBER_OF_COLOR_CHANNELS][numberOfLEDs];
    }

    /**
     * Returns a new 2-dimensional array of shorts representing all the RGB-LEDs of the strip.
     *
     * @return 3-channel-array of shorts
     */
    public short[][] getFreshRGBLEDs() {
	return getFreshRGBLEDs(this
		.getNumberOfLEDs());
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
	if (rgbLEDs.length != TinkerforgeLEDService.NUMBER_OF_COLOR_CHANNELS) {
	    return;
	}

	synchronized (this) {
	    while ((super.device != null)
		    && (this.sendState != SendState.FREE)) {
		try {
		    this.wait(250);
		} catch (final InterruptedException e) {
		}

	    }
	    if (super.device == null) {
		return;
	    }

	    this.sendState = SendState.PREPARING;
	}

	for (int colorChannel = 0; colorChannel < TinkerforgeLEDService.NUMBER_OF_COLOR_CHANNELS; colorChannel++) {
	    if ((rgbLEDs[colorChannel] == null)
		    || (rgbLEDs[colorChannel].length != this.numberOfLEDs)) {
		return;
	    }
	}

	for (int colorChannel = 0; colorChannel < TinkerforgeLEDService.NUMBER_OF_COLOR_CHANNELS; colorChannel++) {
	    for (int write = 0, remaining = this.numberOfLEDs; write < this.numberOfWrites; write++, remaining -= TinkerforgeLEDService.NUMBER_OF_LEDS_PER_WRITE) {
		System.arraycopy(
			rgbLEDs[colorChannel],
			write
			* TinkerforgeLEDService.NUMBER_OF_LEDS_PER_WRITE,
			this.frame[colorChannel][write],
			0,
			Math.min(
				remaining,
				TinkerforgeLEDService.NUMBER_OF_LEDS_PER_WRITE));
	    }
	}
	synchronized (this) {
	    this.sendState = SendState.TO_BE_SENT;
	}
	this.sendRGBLEDFrame();
    }

    /**
     * Either this method is called via setRGBLEDs or via the render-Listener.
     */
    private void sendRGBLEDFrame() {

	synchronized (this) {
	    if (super.device == null) {
		this.sendState = SendState.FREE;
		this.notifyAll();
		return;
	    }
	    if (this.renderState == RenderState.RENDERING) {
		return;
	    }
	    if (this.sendState != SendState.TO_BE_SENT) {
		return;
	    }
	    this.sendState = SendState.SENDING;
	    this.renderState = RenderState.RENDERING;
	}
	try {
	    for (int write = 0; write < this.numberOfWrites; write++) {

		super.device
			.setRGBValues(
				write
				* TinkerforgeLEDService.NUMBER_OF_LEDS_PER_WRITE,
				(short) TinkerforgeLEDService.NUMBER_OF_LEDS_PER_WRITE,
				this.frame[0][write], this.frame[1][write],
				this.frame[2][write]);
	    }

	} catch (final TimeoutException e) {
	    // Eh... ok
	} catch (final NotConnectedException e) {
	    // Well...
	} catch (final NullPointerException e) {
	    this.renderState = RenderState.FREE;
	}
	synchronized (this) {
	    this.sendState = SendState.FREE;
	    this.notifyAll();
	}
    }

    @Override
    public void frameRendered(final int length) {
	synchronized (this) {
	    this.isLaging = (this.sendState == SendState.SENDING);
	    if ((this.sendState == SendState.FREE)
		    && (this.renderState == RenderState.RENDERING)) {
		this.renderState = RenderState.FREE;
		this.notifyAll();
	    }

	    if (this.sendState == SendState.TO_BE_SENT) {
		this.renderState = RenderState.FREE;
		this.sendRGBLEDFrame();
	    }

	}
    }

    public static TinkerforgeLEDIntent createIntent(TinkerforgeDeviceContent deviceContent, Agent agent) {
	return new Intent(deviceContent, agent);
    }

    public static TinkerforgeLEDIntent createIntent(TinkerforgeDeviceContent deviceContent, Agent agent, String... intentReceivers) {
	return new Intent(deviceContent, agent, intentReceivers);
    }
}

class Event extends ATinkerforgeDeviceEvent implements TinkerforgeLEDEvent {

    public Event(TinkerforgeDeviceContent deviceContent, Service eventSender) {
	super(deviceContent, eventSender);
    }

}

class Intent extends ATinkerforgeDeviceIntent implements TinkerforgeLEDIntent {

    public Intent(TinkerforgeDeviceContent deviceContent, Agent intentSender) {
	super(deviceContent, intentSender);
    }

    public Intent(TinkerforgeDeviceContent deviceContent, Agent intentSender, String... intentReceivers) {
	super(deviceContent, intentSender, intentReceivers);
    }

}
