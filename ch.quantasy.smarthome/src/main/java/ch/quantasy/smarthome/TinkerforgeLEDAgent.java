/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome;

import ch.quantasy.messagebus.message.definition.Content;
import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.TinkerforgeLEDService;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.ChipTypeContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.FrameDurationInMilliSecondsContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.ICClockFrequencyInHzContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.NumberOfLEDsContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.RGBLEDsContent;
import ch.quantasy.tinkerbus.service.device.message.TinkerforgeDeviceEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeLEDAgent extends ATinkerforgeAgent {

    public static final String ID = "TinkerforgeLEDAgent";

    private TinkerforgeDeviceContent ledContent;
    private short[][] frame;
    private int power;

    private void handleTinkerforgeEvent(final TinkerforgeDeviceEvent event) {
	TinkerforgeDeviceContent content = event.getDeviceContent();
	if (ledContent == null) {
	    this.ledContent = content;
	    this.ledContent.updateSetting(new ChipTypeContent(ChipTypeContent.WS2801));
	    this.ledContent.updateSetting(new FrameDurationInMilliSecondsContent(20));
	    this.ledContent.updateSetting(new ICClockFrequencyInHzContent(2000000L));
	    this.ledContent.updateSetting(new NumberOfLEDsContent(40));
	    frame = TinkerforgeLEDService.getFreshRGBLEDs(40);
	    this.ledContent.updateSetting(new RGBLEDsContent(frame));
	    publish(TinkerforgeLEDService.createIntent(this.ledContent, this));
	} else {
	    //try {
	    //	Thread.sleep(80);
	    //  } catch (InterruptedException ex) {
	    //	Logger.getLogger(TinkerforgeLEDAgent.class.getName()).log(Level.SEVERE, null, ex);
	    //  }
	    //TinkerforgeLEDIntent intent = TinkerforgeLEDService.createIntent(ledContent, TinkerforgeLEDAgent.this, event.getSenderID());
	    //  power++;
	    // power %= 256;
	    // for (int i = 0; i < frame.length; i++) {
	    //	for (int j = 0; j < frame[i].length; j++) {
//		    frame[i][j] = (short) (power * Math.random());
//		}
//	    }
//	    TinkerforgeDeviceContent dc = new TinkerforgeDeviceContent();
//
//	    Content c = dc.updateSetting(new RGBLEDsContent(frame));
//	    publish(TinkerforgeLEDService.createIntent(dc, TinkerforgeLEDAgent.this, event.getSenderID()));

	}
    }

    @Override
    protected void handleEvent(Event message) {
	if (message instanceof TinkerforgeDeviceEvent) {
	    handleTinkerforgeEvent((TinkerforgeDeviceEvent) message);
	}
	ColorContent colorContent = (ColorContent) (message.getContentByID(ColorContent.class));
	if (colorContent != null) {
	    Color color = colorContent.getValue();

	    if (ledContent != null) {
		RGBLEDsContent rgbsContent = (RGBLEDsContent) ledContent.getSettingContentByID(RGBLEDsContent.class);
		if (rgbsContent != null) {
		    frame = rgbsContent.getValue();
		    for (int i = 0; i < frame[0].length; i++) {
			frame[0][i] = TinkerforgeLEDService.DEFAULT_GAMMA_CORRECTION[Math.max(0, Math.min((short) color.getB(), 255))];
			frame[1][i] = TinkerforgeLEDService.DEFAULT_GAMMA_CORRECTION[Math.max(0, Math.min((short) color.getG(), 255))];
			frame[2][i] = TinkerforgeLEDService.DEFAULT_GAMMA_CORRECTION[Math.max(0, Math.min((short) color.getR(), 255))];

		    }
		    TinkerforgeDeviceContent dc = new TinkerforgeDeviceContent();

		    Content c = dc.updateSetting(new RGBLEDsContent(frame));
		    publish(TinkerforgeLEDService.createIntent(dc, TinkerforgeLEDAgent.this));
		    System.out.println("Clicky");
		    Logger.getLogger(TinkerforgeLEDAgent.class.getName()).log(Level.INFO, null, "clicky");

		}

	    }
	}
    }

    @Override
    public String getID() {
	return ID;
    }

    @Override
    protected void finalize() throws Throwable {
	super.finalize();
	System.out.println("Shoot I am going down.");
    }

}
