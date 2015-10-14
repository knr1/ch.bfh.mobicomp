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
public class CalibrationIntent extends AnIntent {

    public boolean enabled;
    public long duration;
    public int frequency;

    public CalibrationIntent(PiezoSpeaker deviceHandler, String intentTopic) {
	super(deviceHandler, intentTopic, "/calibrate");
    }

    @Override
    public List<Definition> getIntentTopicDefinitions() {
	List<Definition> definitions = new ArrayList<>();
	definitions.add(new Definition(TinkerforgeMQTTPiezoSpeakerApplication.APPLICATION_TOPIC + "/[identificationString]/intent/calibrate/enabled", "Boolean", "JSON", "true", "false"));
	return definitions;
    }

    protected void processSpezializedIntent(String string, MqttMessage mm) {
	if (string.endsWith(getSpezializedIntentTopic() + "/enabled")) {
	    enabled = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(mm.getPayload())), Boolean.class);
	}
	if (enabled) {
	    try {
		getDeviceHandler().getDevice().calibrate();
	    } catch (TimeoutException | NotConnectedException ex) {
		Logger.getLogger(PiezoSpeaker.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
}
