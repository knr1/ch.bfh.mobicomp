/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.agent.device.deviceLED;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.service.device.deviceLED.TinkerforgeLEDEvent;
import ch.quantasy.tinkerbus.service.device.deviceLED.TinkerforgeLEDIntent;
import ch.quantasy.tinkerbus.service.device.deviceLED.TinkerforgeLEDService;
import ch.quantasy.tinkerbus.service.device.deviceLED.TinkerforgeLEDSetting;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeLivingLEDAgent extends ATinkerforgeAgent {

    public static final String ID = "TinkerforgeLivingLEDAgent";
    private final short[][] leds;
    private final Random random;
    private final int MAX_LIGHT = 40;
    private double samplePosition1;
    private double samplePosition2;
    private double samplePosition3;

    @Override
    protected void handleTinkerMessage(DefaultEvent message) {

	this.handleEvent(TinkerforgeLEDService.getTinkerforgeLEDEvent(message));
    }

    public TinkerforgeLivingLEDAgent() {
	random = new Random();
	this.leds = TinkerforgeLEDService.getFreshRGBLEDs(50);

    }

    private String ledServiceID;

    private void handleEvent(TinkerforgeLEDEvent event) {
	if (event == null) {
	    return;
	}
	//System.out.println("---> LED");
	if (event.isDiscovered() != null && event.isDiscovered()) {
	    ledServiceID = event.getSenderID();
	    System.out.println("LED-Stripe discovered");
	    TinkerforgeLEDIntent intent = TinkerforgeLEDService.createIntent(this);
	    intent.getDeviceSetting().setChipType(TinkerforgeLEDSetting.DEFAULT_CHIP_TYPE);
	    intent.getDeviceSetting().setClockFrequencyOfICsInHz(2000000);
	    intent.getDeviceSetting().setFrameDurationInMilliseconds(20);
	    intent.getDeviceSetting().setNumberOfLEDs(50);
	    intent.addReceiverIDs(event.getSenderID());
	    System.out.println(intent);
	    publish(intent);
	    intent = TinkerforgeLEDService.createIntent(this);
	    intent.setRequestCurrentSetting(true);
	    intent.addReceiverIDs(event.getSenderID());
	    publish(intent);

	    final Timer timer = new Timer(true);
	    timer.schedule(new TimerTask() {
		@Override
		public void run() {
		    TinkerforgeLivingLEDAgent.this.updateLEDs();
		}
	    }, 0, 1);
	}
	if (event.getDeviceSetting().getFrameDurationInMilliseconds() != null) {
	    this.frameDurationInMilliseconds = event.getDeviceSetting().getFrameDurationInMilliseconds();
	}
	if (event.isLaging() != null && event.isLaging()) {
	    System.out.println("lagging");
	}

    }

    private void updateLEDs() {
	updateColorFade();
	TinkerforgeLEDIntent intent = TinkerforgeLEDService.createIntent(this);
	intent.addReceiverIDs(ledServiceID);
	intent.setFrame(this.leds);
	publish(intent);
    }

    private void updateColorFade() {

	for (int i = 0; i < this.leds[0].length; i++) {
	    int preLed = this.leds[0][i];
	    int curLed = this.leds[0][(i + 1) % this.leds[0].length];
	    int postLed = this.leds[0][(i + 2) % this.leds[0].length];
	    int lower = 0;//MAX_LIGHT / 2;
	    lower += curLed - preLed;
	    lower += curLed - postLed;

	    int nextTrend = random.nextInt(Math.abs(lower) + 1);
	    if (nextTrend >= Math.abs(lower) / 2) {
		if (random.nextInt(20) < 2) {
		    this.leds[0][(i + 1) % this.leds[0].length] = (short) (Math.min(MAX_LIGHT, Math.max(1, curLed + Math.signum(lower))));
		}
	    } else if (nextTrend <= Math.abs(lower) / 2) {
		if (random.nextInt(20) < 2) {
		    this.leds[0][(i + 1) % this.leds[0].length] = (short) (Math.min(MAX_LIGHT, Math.max(1, curLed - Math.signum(lower))));
		}
	    }

	}

    }

    private void changeSpeed(int speedChange) {
	TinkerforgeLEDIntent intent = TinkerforgeLEDService.createIntent(this);
	intent.addReceiverIDs(ledServiceID);
	intent.getDeviceSetting().setFrameDurationInMilliseconds(Math.max(1, frameDurationInMilliseconds + speedChange));
	publish(intent);
    }

    private int frameDurationInMilliseconds;

    @Override
    public String getID() {
	return ID;
    }

}
