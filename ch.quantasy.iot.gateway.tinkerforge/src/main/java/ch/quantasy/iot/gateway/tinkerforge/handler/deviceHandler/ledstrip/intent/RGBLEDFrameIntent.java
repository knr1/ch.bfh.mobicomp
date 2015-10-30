/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ledstrip.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ledstrip.LedStrip;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RGBLEDFrameIntent extends AnIntent {

    //[red1,red2,red...],[green1,green2,green...],[blue1,blue2,blue3]
    public RGBLEDFrameIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "RGBFrame");
	super.addDescription("rgbFrame", Short[][].class, "JSON", "[[]<=319,[]<=319,[]<=319]=3", "[[0],[0],[0]]", "...", "[[255],[255],[255]]");
    }

//    @Override
//    protected void update(String string, MqttMessage mm) throws Throwable {
//	if (string.endsWith(getName() + "/rgbFrame")) {
//	    rgbFrame = fromMQTTMessage(mm, short[][].class);
//	}
//    }
    @Override
    public boolean isExecutable() {
	short[][] rgbFrame = getContent(LedStrip.RGB_FRAME).getValue(short[][].class);
	return (rgbFrame != null && rgbFrame.length == 3 && rgbFrame[0] != null && rgbFrame[1] != null && rgbFrame[2]
		!= null);
    }
}
