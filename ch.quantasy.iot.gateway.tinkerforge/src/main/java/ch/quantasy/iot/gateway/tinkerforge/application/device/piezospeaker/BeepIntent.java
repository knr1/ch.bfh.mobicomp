/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.device.piezospeaker;

import ch.quantasy.iot.gateway.tinkerforge.application.TinkerforgeMQTTPiezoSpeakerApplication;
import ch.quantasy.iot.gateway.tinkerforge.application.device.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.device.Definition;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class BeepIntent extends AnIntent {

    public Boolean enabled;
    public long duration;
    public int frequency;

    public BeepIntent(PiezoSpeaker deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic + "/beep");
    }

    @Override
    public Map<String, List<Definition>> getIntentTopicDefinitions() {
	Map<String, List<Definition>> definitionMap = new HashMap<>();
	List<Definition> definitions = new ArrayList<>();
	definitionMap.put("beep", definitions);
	definitions.add(new Definition(TinkerforgeMQTTPiezoSpeakerApplication.APPLICATION_TOPIC + "/[identificationString]/intent/beep/enabled", "Boolean", "JSON", "true", "false"));
	definitions.add(new Definition(TinkerforgeMQTTPiezoSpeakerApplication.APPLICATION_TOPIC + "/[identificationString]/intent/beep/duration", "Long", "JSON", "1", "...", "" + Long.MAX_VALUE));
	definitions.add(new Definition(TinkerforgeMQTTPiezoSpeakerApplication.APPLICATION_TOPIC + "/[identificationString]/intent/beep/frequency", "Integer", "JSON", "585", "...", "7100"));
	return definitionMap;
    }

    protected void processSpezializedIntent(String string, MqttMessage mm) {
	if (string.endsWith("beep/enabled")) {
	    enabled = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(mm.getPayload())), Boolean.class);
	}
	if (string.endsWith("beep/duration")) {
	    duration = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(mm.getPayload())), Long.class);
	}
	if (string.endsWith("beep/frequency")) {
	    frequency = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(mm.getPayload())), Integer.class);
	}
	if (enabled) {
	    try {
		getDeviceHandler().getDevice().beep(duration, frequency);
	    } catch (TimeoutException | NotConnectedException ex) {
		Logger.getLogger(PiezoSpeaker.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
}
