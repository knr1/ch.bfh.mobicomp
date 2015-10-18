/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.ledstrip.intent;

import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.AnIntent;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RGBLEDFrameIntent extends AnIntent {

    //[red1,red2,red...],[green1,green2,green...],[blue1,blue2,blue3]
    public short[][] rgbFrame;

    public RGBLEDFrameIntent(ADeviceHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "RGBFrame");
	super.addTopicDescription("rgbFrame", "Short[][]", "JSON", "[[]<=319,[]<=319,[]<=319]=3", "[[0],[0],[0]]", "...", "[[255],[255],[255]]");
    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getIntentName() + "/rgbFrame")) {
	    rgbFrame = fromMQTTMessage(mm, short[][].class);
	}
    }

    @Override
    public boolean isExecutable() {
	return rgbFrame != null;
    }
}
