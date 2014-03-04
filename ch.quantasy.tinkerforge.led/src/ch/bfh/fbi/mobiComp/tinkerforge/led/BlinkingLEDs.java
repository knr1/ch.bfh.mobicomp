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
	private short[][] leds;

	public BlinkingLEDs() {
		ledStripeApp = new ConcurrentLEDStripeApplication();
		super.addTinkerforgeApplication(ledStripeApp);
		leds = ledStripeApp.getFreshRGBLEDs();
	}

	

	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.LEDStrip) {
			Timer timer = new Timer(true);
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					updateLEDs();
				}
			}, 0, 1);
			System.out.println("Started");
		}
	}

	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		// TODO Auto-generated method stub

	}
	
	private int i;
	private short j;
	private int randomPosition;
	private Random random = new Random();

	private void updateLEDs() {
		System.out.println(i);
		for (int position = 0; position < leds[2].length; position++) {
			if (j < 128)
				leds[2][position] = j;
			else
				leds[2][position] = (short) (256 - j);
		}
		j++;
		j %= 256;
		leds[0][i] = 0;
		leds[1][(leds[1].length - 1) - i] = 0;
		i++;
		i %= (leds[0].length);
		leds[0][i] = 255;
		leds[1][(leds[1].length - 1) - i] = 255;

		leds[0][randomPosition] = 0;
		leds[1][randomPosition] = 0;
		leds[2][randomPosition] = 0;
		randomPosition = random.nextInt(50);
		leds[0][randomPosition] = 255;
		leds[1][randomPosition] = 255;
		leds[2][randomPosition] = 255;

		ledStripeApp.setRGBLEDs(leds);

	}

	@Override
	public boolean equals(Object obj) {
		return this==obj;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
