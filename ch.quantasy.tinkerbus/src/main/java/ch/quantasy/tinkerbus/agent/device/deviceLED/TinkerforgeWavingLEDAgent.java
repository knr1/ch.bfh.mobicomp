/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.agent.device.deviceLED;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeWavingLEDAgent {
} /*extends ATinkerforgeAgent {

 public static final String ID = "TinkerforgeWavingLEDAgent";
 private final short[][] leds;
 private final short[] sampleWave;
 private Random random;
 private final int MAX_LIGHT = 128;
 private final int AMOUNT_OF_LEDs = 64;

 private double samplePosition1;
 private double samplePosition2;
 private double samplePosition3;

 @Override
 protected void handleTinkerMessage(DefaultEvent message) {

 this.handleEvent(TinkerforgeLEDService.getTinkerforgeLEDEvent(message));
 }

 public TinkerforgeWavingLEDAgent() {
 random = new Random();
 this.leds = TinkerforgeLEDService.getFreshRGBLEDs(AMOUNT_OF_LEDs);
 sampleWave = new short[AMOUNT_OF_LEDs * 255];
 for (int i = 0; i < sampleWave.length; i++) {
 sampleWave[i] = (short) ((MAX_LIGHT / 2) * Math
 .sin((2 * Math.PI / sampleWave.length) * i));
 }

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
 intent.getDeviceSetting().setChipType(TinkerforgeLEDSetting.CHIP_TYPE_WS2812);
 intent.getDeviceSetting().setClockFrequencyOfICsInHz(2000000);
 intent.getDeviceSetting().setFrameDurationInMilliseconds(20);
 intent.getDeviceSetting().setNumberOfLEDs(AMOUNT_OF_LEDs);
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
 TinkerforgeWavingLEDAgent.this.updateLEDs();
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
 int internalSamplePosition = 0;
 samplePosition1 += sampleWave.length / 666667.0;
 samplePosition1 += random.nextInt(13);
 samplePosition1 %= sampleWave.length;
 internalSamplePosition = (int) samplePosition1;
 for (int i = 0; i < this.leds[0].length; i++) {
 internalSamplePosition += sampleWave.length / 51.0;
 internalSamplePosition %= sampleWave.length;
 this.leds[0][i] = (short) (((MAX_LIGHT / 2 + sampleWave[internalSamplePosition]) / 20) + 5);

 }

 samplePosition2 += sampleWave.length / 777777.0;
 samplePosition2 += random.nextInt(19);
 samplePosition2 %= sampleWave.length;
 internalSamplePosition = (int) samplePosition2;
 for (int i = 0; i < this.leds[0].length; i++) {
 internalSamplePosition += sampleWave.length / 13.0;
 internalSamplePosition %= sampleWave.length;
 this.leds[0][(this.leds[0].length - 1) - i] += (short) ((MAX_LIGHT / 2 + sampleWave[internalSamplePosition])
 / 20);
 }
 samplePosition3 += sampleWave.length / 653223.0;
 samplePosition3 += random.nextInt(17);
 samplePosition3 %= sampleWave.length;
 internalSamplePosition = (int) samplePosition3;

 for (int i = 0; i < this.leds[0].length; i++) {
 internalSamplePosition += sampleWave.length / 20;
 internalSamplePosition %= sampleWave.length;
 if (random.nextInt(5) == 0) {
 this.leds[2][i] = (short) (((MAX_LIGHT / 2 + sampleWave[internalSamplePosition]) / 2) / 30);
 } else {
 this.leds[2][i] = 0;

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

 }*/
