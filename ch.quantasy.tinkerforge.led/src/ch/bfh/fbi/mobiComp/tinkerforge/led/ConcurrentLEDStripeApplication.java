package ch.bfh.fbi.mobiComp.tinkerforge.led;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 * This class can be used in order to send an 'image' that has to be displayed
 * on an LED-stripe. It is best to get a fresh 'frame' by
 * {@link #getFreshRGBLEDs()}. Then 'drawing' the image within this array and
 * finally by setting it by {@link #setRGBLEDs(short[][])}. This method will
 * make a copy of the data. So once this method returns, the array can be reused
 * to 'draw' the next 'image'.
 * 
 * @author reto
 * 
 */
public class ConcurrentLEDStripeApplication extends
		AbstractTinkerforgeApplication implements FrameRenderedListener {

	private enum SendState {
		FREE, PREPARING, TO_BE_SENT, SENDING, MAINTENANCE;
	}

	private enum RenderState {
		FREE, RENDERING;
	}

	private SendState sendState;
	private RenderState renderState;

	public static final int DEFAULT_NUMBER_OF_LEDS = 50;

	// Frame duration in Milliseconds 20ms (1000ms / 50frames = 20ms per frame)
	public static final int DEFAULT_FRAME_DURATION_IN_MILLISECONDS = 20;

	// IC-refresh in Hz
	public static final int DEFAULT_CLOCK_FREQUENCY_OF_ICS_IN_HZ = 2000000;

	private int frameDurationInMilliseconds;
	private int clockFrequencyOfICsInHz;
	private BrickletLEDStrip ledStrip;
	private final static int NUMBER_OF_LEDS_PER_WRITE = 16;
	private final static int NUMBER_OF_COLOR_CHANNELS = 3;

	private int numberOfLEDs;
	private int numberOfWrites;

	private short[][][] frame;

	public ConcurrentLEDStripeApplication() {
		System.out.println("Concurrent");
		this.setClockFrequencyOfICsInHz(ConcurrentLEDStripeApplication.DEFAULT_CLOCK_FREQUENCY_OF_ICS_IN_HZ);
		System.out.println("MHz set");
		this.setFrameDurationInMilliseconds(ConcurrentLEDStripeApplication.DEFAULT_FRAME_DURATION_IN_MILLISECONDS);
		System.out.println("FrameDuration set");
		this.renderState = RenderState.FREE;
		System.out.println("RenderState set");
		this.sendState = SendState.FREE;
		System.out.println("sendingState set");
		this.setNumberOfLEDs(ConcurrentLEDStripeApplication.DEFAULT_NUMBER_OF_LEDS);
		System.out.println("NumLEDs set");

	}

	@Override
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.LEDStrip) {
			if (!TinkerforgeDevice.areEqual(this.ledStrip, device)) {
				if (this.ledStrip != null) {
					this.removeTinkerforgeApplication(this);
				}
				this.ledStrip = (BrickletLEDStrip) device;
				this.ledStrip.addFrameRenderedListener(this);
				this.setup();
			}
		}

	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.LEDStrip) {
			if (TinkerforgeDevice.areEqual(this.ledStrip, device)) {
				this.tearDown();
				this.ledStrip.removeFrameRenderedListener(this);
				this.ledStrip = null;
			}
		}
	}

	private void setup() {

		this.setFrameDurationInMilliseconds(this.frameDurationInMilliseconds);
		this.setClockFrequencyOfICsInHz(this.clockFrequencyOfICsInHz);
		this.sendRGBLEDFrame();
	}

	private void tearDown() {

	}

	/**
	 * Accepts 2-Dimensional arrays of shorts with the length of the LED-Stripe.
	 * It will copy the content and will return after that. You are free to
	 * reuse the array again.
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
				/ ConcurrentLEDStripeApplication.NUMBER_OF_LEDS_PER_WRITE);
		this.frame = new short[ConcurrentLEDStripeApplication.NUMBER_OF_COLOR_CHANNELS][this.numberOfWrites][ConcurrentLEDStripeApplication.NUMBER_OF_LEDS_PER_WRITE];
		synchronized (this) {
			this.sendState = SendState.FREE;
			this.notifyAll();
		}
	}

	public int getNumberOfLEDs() {
		return this.numberOfLEDs;
	}

	public void setFrameDurationInMilliseconds(
			final int frameDurationInMilliseconds) {
		if (frameDurationInMilliseconds < 1) {
			throw new IllegalArgumentException();
		}
		this.frameDurationInMilliseconds = frameDurationInMilliseconds;
		if (this.ledStrip != null) {
			try {
				this.ledStrip
						.setFrameDuration(this.frameDurationInMilliseconds);
			} catch (final TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final NotConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public int getFrameDurationInMilliseconds() {
		return this.frameDurationInMilliseconds;
	}

	public void setClockFrequencyOfICsInHz(final int clockFrequencyOfICsInHz) {
		if (clockFrequencyOfICsInHz < 1) {
			throw new IllegalArgumentException();
		}
		this.clockFrequencyOfICsInHz = clockFrequencyOfICsInHz;
		if (this.ledStrip != null) {
			try {
				this.ledStrip.setClockFrequency(clockFrequencyOfICsInHz);
			} catch (final TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final NotConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public int getClockFrequencyOfICsInHz() {
		return this.clockFrequencyOfICsInHz;
	}

	/**
	 * Returns a new 2-dimensional array of shorts representing all the RGB-LEDs
	 * of the strip.
	 * 
	 * @return 3-channel-array of shorts
	 */
	public short[][] getFreshRGBLEDs() {
		return new short[ConcurrentLEDStripeApplication.NUMBER_OF_COLOR_CHANNELS][this
				.getNumberOfLEDs()];
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ConcurrentLEDStripeApplication)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		final ConcurrentLEDStripeApplication other = (ConcurrentLEDStripeApplication) obj;
		return TinkerforgeDevice.areEqual(this.ledStrip, other.ledStrip);
	}

	@Override
	public int hashCode() {
		return 0;
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
		if (rgbLEDs.length != ConcurrentLEDStripeApplication.NUMBER_OF_COLOR_CHANNELS) {
			return;
		}

		synchronized (this) {
			while ((this.ledStrip != null)
					&& (this.sendState != SendState.FREE)) {
				try {
					this.wait(250);
				} catch (final InterruptedException e) {
				}

			}
			if (this.ledStrip == null) {
				return;
			}

			this.sendState = SendState.PREPARING;
		}

		for (int colorChannel = 0; colorChannel < ConcurrentLEDStripeApplication.NUMBER_OF_COLOR_CHANNELS; colorChannel++) {
			if ((rgbLEDs[colorChannel] == null)
					|| (rgbLEDs[colorChannel].length != this.numberOfLEDs)) {
				return;
			}
		}

		for (int colorChannel = 0; colorChannel < ConcurrentLEDStripeApplication.NUMBER_OF_COLOR_CHANNELS; colorChannel++) {
			for (int write = 0, remaining = this.numberOfLEDs; write < this.numberOfWrites; write++, remaining -= ConcurrentLEDStripeApplication.NUMBER_OF_LEDS_PER_WRITE) {
				System.arraycopy(
						rgbLEDs[colorChannel],
						write
								* ConcurrentLEDStripeApplication.NUMBER_OF_LEDS_PER_WRITE,
						this.frame[colorChannel][write],
						0,
						Math.min(
								remaining,
								ConcurrentLEDStripeApplication.NUMBER_OF_LEDS_PER_WRITE));
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
			if (this.ledStrip == null) {
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

				this.ledStrip
						.setRGBValues(
								write
										* ConcurrentLEDStripeApplication.NUMBER_OF_LEDS_PER_WRITE,
								(short) ConcurrentLEDStripeApplication.NUMBER_OF_LEDS_PER_WRITE,
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
}
