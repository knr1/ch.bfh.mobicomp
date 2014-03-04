package ch.bfh.fbi.mobiComp.tinkerforge.led;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.Device;

public class BlinkingLEDs extends AbstractTinkerforgeApplication {
	public ConcurrentLEDStripeApplication ledStripeApp;
	private final short[][] leds;

	public BlinkingLEDs() {
		this.ledStripeApp = new ConcurrentLEDStripeApplication();
		super.addTinkerforgeApplication(this.ledStripeApp);
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
			System.out.println("Started");
		}
	}

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		// TODO Auto-generated method stub

	}

	private int i;
	private short j;
	private int randomPosition;
	private final Random random = new Random();

	private void updateLEDs() {
		System.out.println(this.i);
		for (int position = 0; position < this.leds[2].length; position++) {
			if (this.j < 128) {
				this.leds[2][position] = this.j;
			} else {
				this.leds[2][position] = (short) (256 - this.j);
			}
		}
		this.j++;
		this.j %= 256;
		this.leds[0][this.i] = 0;
		this.leds[1][(this.leds[1].length - 1) - this.i] = 0;
		this.i++;
		this.i %= (this.leds[0].length);
		this.leds[0][this.i] = 255;
		this.leds[1][(this.leds[1].length - 1) - this.i] = 255;

		this.leds[0][this.randomPosition] = 0;
		this.leds[1][this.randomPosition] = 0;
		this.leds[2][this.randomPosition] = 0;
		this.randomPosition = this.random.nextInt(50);
		this.leds[0][this.randomPosition] = 255;
		this.leds[1][this.randomPosition] = 255;
		this.leds[2][this.randomPosition] = 255;

		this.ledStripeApp.setRGBLEDs(this.leds);

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
