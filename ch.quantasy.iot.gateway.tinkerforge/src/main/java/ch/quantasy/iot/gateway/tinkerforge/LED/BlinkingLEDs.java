package ch.quantasy.iot.gateway.tinkerforge.LED;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.Device;

public class BlinkingLEDs extends AbstractTinkerforgeApplication {
	private ConcurrentLEDStripeApplication ledStripeApp;
	private RotaryApplication rotaryApp;
	private final short[][] leds;
	private boolean isFlashing;

	public BlinkingLEDs() {
		this.ledStripeApp = new ConcurrentLEDStripeApplication();
		this.rotaryApp = new RotaryApplication(this);
		super.addTinkerforgeApplication(rotaryApp);
		super.addTinkerforgeApplication(this.ledStripeApp);
		this.ledStripeApp.setFrameDurationInMilliseconds(50);
		this.ledStripeApp.setNumberOfLEDs(64);
		this.leds = this.ledStripeApp.getFreshRGBLEDs();

	}

	@Override
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.LEDStrip) {
			final Timer timer = new Timer(true);
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					BlinkingLEDs.this.updateLEDs();
				}
			}, 0, 1);
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					if (ledStripeApp.getLagState())
						System.out.println("Laging!");
					ledStripeApp.resetLagState();

				}
			}, 0, 1000);
		}
	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
	}

	
	private void updateLEDs() {
		if (isFlashing)
			updateFlashing();
		else {
			updateColorFade();
			updateColorTrains();
			updateTwinkles();
		}
		this.ledStripeApp.setRGBLEDs(this.leds);
	}

	private int flashCounter;

	private void updateFlashing() {
		flashCounter++;
		flashCounter %= 11;
		if (flashCounter == 0) {
			this.isFlashing = false;
		}
		switch (flashCounter) {
		case 1:
		case 5:
		case 7:
			Arrays.fill(this.leds[0], (short) 255);
			Arrays.fill(this.leds[1], (short) 255);
			Arrays.fill(this.leds[2], (short) 255);
			break;
		case 4:
		case 6:
		case 8:
			Arrays.fill(this.leds[0], (short) 0);
			Arrays.fill(this.leds[1], (short) 0);
			Arrays.fill(this.leds[2], (short) 0);
			break;
		}

	}

	private final int MAX_LIGHT = 255;
	private short j;
	private void updateColorFade(){
			short actualLightIntensity=0;
			if (this.j < MAX_LIGHT) {
				actualLightIntensity=j;
			} else {
				actualLightIntensity=(short) ((MAX_LIGHT * 2) - this.j);
			}
			Arrays.fill(this.leds[2],actualLightIntensity);
		this.j++;
		this.j %= (MAX_LIGHT * 2 + 1);

	}
	private int i;
	private void updateColorTrains() {
		for (int x = 0; x < 10; x++) {
			this.leds[0][(this.i + x) % this.leds[0].length] = 0;
			this.leds[1][(this.leds[1].length - 1)
					- ((this.i + x) % this.leds[0].length)] = 0;
		}
		this.i++;
		this.i %= (this.leds[0].length);
		for (int x = 0; x < 10; x++) {
			this.leds[0][(this.i + x) % this.leds[0].length] = (short) (255 - (25 * x));
			this.leds[1][(this.leds[1].length - 1)
					- ((this.i + x) % this.leds[0].length)] = (short) (255 - (25 * x));
		}

	}
	private int randomPosition;
	private final Random random = new Random();
	private void updateTwinkles() {
		this.leds[0][this.randomPosition] = 0;
		this.leds[1][this.randomPosition] = 0;
		this.leds[2][this.randomPosition] = 0;
		this.randomPosition = this.random.nextInt(this.ledStripeApp
				.getNumberOfLEDs());
		this.leds[0][this.randomPosition] = 255;
		this.leds[1][this.randomPosition] = 255;
		this.leds[2][this.randomPosition] = 255;

	}

	public void flash() {
		this.isFlashing = true;
	}

	public void changeSpeed(int speedChange) {
		this.ledStripeApp.setFrameDurationInMilliseconds(Math.max(1,
				ledStripeApp.getFrameDurationInMilliseconds() + speedChange));
	}

	@Override
	public boolean equals(final Object obj) {
		return this == obj;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
