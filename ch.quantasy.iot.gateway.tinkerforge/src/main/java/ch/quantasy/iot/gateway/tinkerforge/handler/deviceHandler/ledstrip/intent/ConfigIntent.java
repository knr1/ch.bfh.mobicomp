/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.ledstrip.intent;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnIntent;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ConfigIntent extends AnIntent {

    public long clockFrequencyOfICsInHz;
    public boolean enabled;
    public int frameDurationInMilliseconds;
    public int chipType;
    public int numberOfLEDs;

    public ConfigIntent(AHandler deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "config");
	super.addDescription("chipType", "Integer", "JSON", "2801", "2811", "2812");
	super.addDescription("enabled", "Boolean", "JSON", "true", "false");
	super.addDescription("clockFrequencyOfICsInHz", "Long", "JSON", "10000", "...", "2000000");
	super.addDescription("frameDurationInMilliseconds", "Integer", "JSON", "1", "...", "" + Integer.MAX_VALUE);
	super.addDescription("numberOfLEDs", "Integer", "1", "...", "319");

    }

    @Override
    protected void update(String string, MqttMessage mm) throws Throwable {
	if (string.endsWith(getName() + "/enabled")) {
	    enabled = fromMQTTMessage(mm, Boolean.class);
	}
	if (string.endsWith(getName() + "/chipType")) {
	    chipType = fromMQTTMessage(mm, Integer.class);
	}
	if (string.endsWith(getName() + "/clockFrequencyOfICsInHz")) {
	    clockFrequencyOfICsInHz = fromMQTTMessage(mm, Long.class);
	}
	if (string.endsWith(getName() + "/frameDurationInMilliseconds")) {
	    frameDurationInMilliseconds = fromMQTTMessage(mm, Integer.class);
	}
	if (string.endsWith(getName() + "/numberOfLEDs")) {
	    numberOfLEDs = fromMQTTMessage(mm, Integer.class);
	}

    }

    @Override
    public boolean isExecutable() {
	return enabled && isClockFrequencyOfICsInHzInRange() && isFrameDurationInMillisecondsInRange() && isNumberOfLEDsInRange();
    }

    private boolean isChipTypeInRange() {
	return (chipType == 2801 || chipType == 2811 || chipType == 2812);
    }

    private boolean isClockFrequencyOfICsInHzInRange() {
	return (clockFrequencyOfICsInHz >= 10000 && clockFrequencyOfICsInHz <= 2000000);
    }

    private boolean isFrameDurationInMillisecondsInRange() {
	return (frameDurationInMilliseconds >= 1);
    }

    private boolean isNumberOfLEDsInRange() {
	return (numberOfLEDs >= 1 && numberOfLEDs <= 319);
    }

}
