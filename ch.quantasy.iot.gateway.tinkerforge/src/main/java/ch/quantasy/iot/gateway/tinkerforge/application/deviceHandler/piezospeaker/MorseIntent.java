/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.piezospeaker;

import ch.quantasy.iot.gateway.tinkerforge.application.TinkerforgeMQTTPiezoSpeakerApplication;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.Definition;
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
public class MorseIntent extends AnIntent {

    public boolean enabled;
    public String code;
    public int frequency;

    public MorseIntent(PiezoSpeaker deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic + "/morse");
    }

    @Override
    public Map<String, List<Definition>> getIntentTopicDefinitions() {
	Map<String, List<Definition>> definitionMap = new HashMap<>();
	List<Definition> definitions = new ArrayList<>();
	definitionMap.put("morse", definitions);
	definitions.add(new Definition(TinkerforgeMQTTPiezoSpeakerApplication.APPLICATION_TOPIC + "/[identificationString]/intent/morse/enabled", "Boolean", "JSON", "true", "false"));
	definitions.add(new Definition(TinkerforgeMQTTPiezoSpeakerApplication.APPLICATION_TOPIC + "/[identificationString]/intent/morse/code", "String", "JSON", ".", "-", " ", "unbounded"));
	definitions.add(new Definition(TinkerforgeMQTTPiezoSpeakerApplication.APPLICATION_TOPIC + "/[identificationString]/intent/morse/frequency", "Integer", "JSON", "685", "...", "7100"));
	return definitionMap;
    }

    protected void processSpezializedIntent(String string, MqttMessage mm) {
	try {
	    if (string.endsWith("morse/enabled")) {
		enabled = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(mm.getPayload())), Boolean.class);
	    }
	    if (string.endsWith("morse/code")) {
		code = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(mm.getPayload())), String.class);
	    }
	    if (string.endsWith("morse/frequency")) {
		frequency = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(mm.getPayload())), Integer.class);
	    }
	    if (enabled) {
		try {
		    getDeviceHandler().getDevice().morseCode(code, frequency);
		} catch (TimeoutException | NotConnectedException ex) {
		    Logger.getLogger(PiezoSpeaker.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	} catch (Throwable th) {
	    th.printStackTrace();
	}
    }
}
