/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome;

import ch.quantasy.messagebus.message.definition.Content;
import ch.quantasy.messagebus.message.definition.Event;
import ch.quantasy.smarthome.content.Color;
import ch.quantasy.smarthome.content.ColorContent;
import ch.quantasy.smarthome.content.Type;
import ch.quantasy.smarthome.content.TypeContent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeAgent;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.TinkerforgeLEDService;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.ChipTypeContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.FrameDurationInMilliSecondsContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.ICClockFrequencyInHzContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.NumberOfLEDsContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.content.RGBLEDsContent;
import ch.quantasy.tinkerbus.service.device.deviceLED.message.TinkerforgeLEDEvent;
import ch.quantasy.tinkerbus.service.device.message.TinkerforgeDeviceEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LightAgent extends ATinkerforgeAgent {

    public static final String ID = "TinkerforgeLEDAgent";
    public static final String AMBIENT_ID = "";
    public static final String DIRECT_ID = "";
    public static final String SPOT_ID = "";

    private Map<Type, TinkerforgeDeviceContent> ledContentMap;

    private int power;

    public LightAgent() {
	this.ledContentMap = new HashMap<>();
    }

    private void handleTinkerforgeEvent(final TinkerforgeLEDEvent event) {
	TinkerforgeDeviceContent content = event.getDeviceContent();
	short[][] frame;

	if (!ledContentMap.containsKey(event.getSenderID())) {
	    if (event.getSenderID().equals(DIRECT_ID)) {
		ledContentMap.put(Type.direct, content);
		content.updateSetting(new ChipTypeContent(ChipTypeContent.WS2801));
		content.updateSetting(new NumberOfLEDsContent(40));
		frame = TinkerforgeLEDService.getFreshRGBLEDs(40);
		content.updateSetting(new RGBLEDsContent(frame));
	    }
	    if (event.getSenderID().equals(AMBIENT_ID)) {
		ledContentMap.put(Type.ambient, content);
		content.updateSetting(new ChipTypeContent(ChipTypeContent.WS2812));
		content.updateSetting(new NumberOfLEDsContent(155));
		frame = TinkerforgeLEDService.getFreshRGBLEDs(155);
		content.updateSetting(new RGBLEDsContent(frame));
	    }
	    if (event.getSenderID().equals(SPOT_ID)) {
		ledContentMap.put(Type.spot, content);
		content.updateSetting(new ChipTypeContent(ChipTypeContent.WS2812));
		content.updateSetting(new NumberOfLEDsContent(64 * 3));
		frame = TinkerforgeLEDService.getFreshRGBLEDs(64 * 3);
		content.updateSetting(new RGBLEDsContent(frame));
	    }
	    content.updateSetting(new FrameDurationInMilliSecondsContent(20));
	    content.updateSetting(new ICClockFrequencyInHzContent(2000000L));
	    publish(TinkerforgeLEDService.createIntent(content, this));
	} else {
	    //try {
	    //	Thread.sleep(80);
	    //  } catch (InterruptedException ex) {
	    //	Logger.getLogger(LightAgent.class.getName()).log(Level.SEVERE, null, ex);
	    //  }
	    //TinkerforgeLEDIntent intent = TinkerforgeLEDService.createIntent(ledContent, LightAgent.this, event.getSenderID());
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
//	    publish(TinkerforgeLEDService.createIntent(dc, LightAgent.this, event.getSenderID()));

	}

    }

    private void handleTinkerforgeEvent(final TinkerforgeDeviceEvent event) {
	TinkerforgeDeviceContent content = event.getDeviceContent();
	if (event instanceof TinkerforgeLEDService) {
	    handleTinkerforgeEvent((TinkerforgeLEDEvent) event);
	}
    }

    @Override
    protected void handleEvent(Event message) {
	if (message instanceof TinkerforgeDeviceEvent) {
	    handleTinkerforgeEvent((TinkerforgeDeviceEvent) message);
	}
	TypeContent typeContent = (TypeContent) (message.getContentByID(TypeContent.class));
	if (typeContent == null) {
	    return;
	}

	Type type = typeContent.getValue();
	TinkerforgeDeviceContent content = ledContentMap.get(type);
	if (content == null) {
	    return;
	}
	ColorContent colorContent = (ColorContent) (message.getContentByID(ColorContent.class));
	if (colorContent != null) {
	    Color color = colorContent.getValue();
	    RGBLEDsContent rgbsContent = (RGBLEDsContent) content.getSettingContentByID(RGBLEDsContent.class);
	    if (rgbsContent != null) {
		short[][] frame = rgbsContent.getValue();
		for (int i = 0; i < frame[0].length; i++) {
		    frame[0][i] = TinkerforgeLEDService.DEFAULT_GAMMA_CORRECTION[Math.max(0, Math.min((short) color.getB(), 255))];
		    frame[1][i] = TinkerforgeLEDService.DEFAULT_GAMMA_CORRECTION[Math.max(0, Math.min((short) color.getG(), 255))];
		    frame[2][i] = TinkerforgeLEDService.DEFAULT_GAMMA_CORRECTION[Math.max(0, Math.min((short) color.getR(), 255))];

		}
		TinkerforgeDeviceContent dc = new TinkerforgeDeviceContent();

		Content c = dc.updateSetting(new RGBLEDsContent(frame));
		switch (type) {
		    case ambient:
			publish(TinkerforgeLEDService.createIntent(dc, LightAgent.this, AMBIENT_ID));
			break;
		    case direct:
			publish(TinkerforgeLEDService.createIntent(dc, LightAgent.this, DIRECT_ID));
			break;
		    case spot:
			publish(TinkerforgeLEDService.createIntent(dc, LightAgent.this, SPOT_ID));
			break;
		    default:
			break;
		}

		System.out.println("Clicky");
		Logger.getLogger(LightAgent.class.getName()).log(Level.INFO, null, "clicky");

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
