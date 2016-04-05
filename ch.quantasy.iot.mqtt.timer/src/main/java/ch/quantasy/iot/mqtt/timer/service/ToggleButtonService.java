/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.timer.service;

import ch.quantasy.iot.mqtt.timer.communication.MQTTCommunication;
import ch.quantasy.iot.mqtt.timer.communication.MQTTParameters;
import ch.quantasy.iot.mqtt.timer.toggler.Toggler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class ToggleButtonService implements MqttCallback, ActionListener {

    public final static String CLIENT_ID = "Toggler";

    public final static String BASE_TOPIC = "ToggleButtonService";
    public final static String STATUS_TOPIC = BASE_TOPIC + "/status";
    public final static String EVENT_TOPIC = BASE_TOPIC + "/event";
    public final static String INTENT_TOPIC = BASE_TOPIC + "/intent";

    public final static String STATUS_TOPIC_CONNECTION = STATUS_TOPIC + "/connection";
    public final static String STATUS_CONNECTION_OFFLINE = "offline";
    public final static String STATUS_CONNECTION_ONLINE = "online";

    public static final String INTENT_TOPIC_LABEL = INTENT_TOPIC + "/label";
    public static final String TEXT = "/text";
    public static final String INTENT_TOPIC_LABEL_TEXT = INTENT_TOPIC_LABEL + TEXT;

    public static final String INTENT_TOPIC_TOGGLE = INTENT_TOPIC + "/toggle";
    public static final String STATE = "/state";
    public static final String INTENT_TOPIC_TOGGLE_STATE = INTENT_TOPIC_TOGGLE + STATE;
    public final static String INTENT_TOGGLE_TRUE = "true";
    public final static String INTENT_TOGGLE_FALSE = "false";

    public static final String EVENT_TOPIC_TEXT = EVENT_TOPIC + "/text";
    public static final String EVENT_TOPIC_TOGGLE = EVENT_TOPIC + "/toggle";
    public final static String EVENT_TOGGLE_TRUE = "true";
    public final static String EVENT_TOGGLE_FALSE = "false";

    private MQTTCommunication communication;
    private Toggler toggler;

    public ToggleButtonService() throws MqttException {
        communication = new MQTTCommunication();
        MQTTParameters parameters = new MQTTParameters();
        parameters.setClientID(CLIENT_ID);
        parameters.setIsCleanSession(false);
        parameters.setIsLastWillRetained(true);
        parameters.setLastWillMessage(STATUS_CONNECTION_OFFLINE.getBytes());
        parameters.setLastWillQoS(1);
        parameters.setServerURIs(URI.create("tcp://127.0.0.1:1883"));
        parameters.setWillTopic(STATUS_TOPIC_CONNECTION);
        parameters.setMqttCallback(this);
        communication.connect(parameters);
        communication.publishActualWill(STATUS_CONNECTION_ONLINE.getBytes());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ToggleButtonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        communication.subscribe(INTENT_TOPIC + "/#", 0);
        toggler = new Toggler(this);

    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Ouups, lost connection to subscirptions");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        if (string.startsWith(INTENT_TOPIC_LABEL) && string.endsWith(TEXT)) {
            toggler.setText(new String(mm.getPayload()));
            MqttMessage eventMessage = new MqttMessage(mm.getPayload());
            eventMessage.setQos(1);
            eventMessage.setRetained(true);
            communication.publish(EVENT_TOPIC_TEXT, eventMessage);
            toggler.setText(new String(mm.getPayload()));

        }
        if (string.startsWith(INTENT_TOPIC_TOGGLE) && string.endsWith(STATE)) {
            if (INTENT_TOGGLE_FALSE.equals(new String(mm.getPayload()))) {
                toggler.setSelected(false);
            }
            if (INTENT_TOGGLE_TRUE.equals(new String(mm.getPayload()))) {
                toggler.setSelected(true);
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        System.out.println("Delivery is done.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        MqttMessage eventMessage = new MqttMessage(("" + toggler.isSelected()).getBytes());
        eventMessage.setQos(1);
        eventMessage.setRetained(true);
        communication.publish(EVENT_TOPIC_TOGGLE, eventMessage);
    }

    public static void main(String[] args) throws MqttException {
        ToggleButtonService service = new ToggleButtonService();
    }

}
