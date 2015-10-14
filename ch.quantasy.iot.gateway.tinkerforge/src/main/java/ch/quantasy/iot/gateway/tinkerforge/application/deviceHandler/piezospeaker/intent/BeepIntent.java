/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.intent;

import ch.quantasy.iot.gateway.tinkerforge.application.TinkerforgeMQTTPiezoSpeakerApplication;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.Definition;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker.PiezoSpeaker;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class BeepIntent extends AnIntent {

    public boolean enabled;
    public long duration;
    public int frequency;

    public BeepIntent(PiezoSpeaker deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "/beep");
    }

    @Override
    public List<Definition> getIntentTopicDefinitions() {

	List<Definition> definitions = new ArrayList<>();
	definitions.add(new Definition(TinkerforgeMQTTPiezoSpeakerApplication.APPLICATION_TOPIC + "/[identificationString]/intent/beep/enabled", "Boolean", "JSON", "true", "false"));
	definitions.add(new Definition(TinkerforgeMQTTPiezoSpeakerApplication.APPLICATION_TOPIC + "/[identificationString]/intent/beep/duration", "Long", "JSON", "1", "...", "" + Long.MAX_VALUE));
	definitions.add(new Definition(TinkerforgeMQTTPiezoSpeakerApplication.APPLICATION_TOPIC + "/[identificationString]/intent/beep/frequency", "Integer", "JSON", "585", "...", "7100"));
	return definitions;
    }

    protected void processSpezializedIntent(String string, MqttMessage mm) {
	if (string.endsWith(getSpezializedIntentTopic() + "/enabled")) {
	    enabled = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(mm.getPayload())), Boolean.class);
	}
	if (string.endsWith(getSpezializedIntentTopic() + "/duration")) {
	    duration = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(mm.getPayload())), Long.class);
	}
	if (string.endsWith(getSpezializedIntentTopic() + "/frequency")) {
	    frequency = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(mm.getPayload())), Integer.class);
	}
	if (enabled && isFrequencyInRange()) {
	    try {
		getDeviceHandler().getDevice().beep(duration, frequency);
	    } catch (TimeoutException | NotConnectedException ex) {
		Logger.getLogger(PiezoSpeaker.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    private boolean isFrequencyInRange() {
	return (frequency >= 685 && frequency <= 7100);
    }

    private boolean isDurationInRange() {
	return duration > 0;
    }
}
