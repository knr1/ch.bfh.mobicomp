/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.timer.agent;

import ch.quantasy.iot.mqtt.timer.communication.MQTTCommunication;
import ch.quantasy.iot.mqtt.timer.communication.MQTTParameters;
import ch.quantasy.iot.mqtt.timer.service.TimerService;
import ch.quantasy.iot.mqtt.timer.service.ConsoleService;
import ch.quantasy.iot.mqtt.timer.service.ToggleButtonService;
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
public class ToggleAgent implements MqttCallback {

    public final static String CLIENT_ID = "ToggleAgent01";

    public final static String BASE_TOPIC = "ToggleAgent";
    public final static String STATUS_TOPIC = BASE_TOPIC + "/status";
    public final static String STATUS_TOPIC_CONNECTION = STATUS_TOPIC + "/connection";
    public final static String STATUS_CONNECTION_OFFLINE = "offline";
    public final static String STATUS_CONNECTION_ONLINE = "online";

    private MQTTCommunication communication;

    public ToggleAgent() throws MqttException {
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
        MqttMessage textMessage = new MqttMessage("<html><h1>Pause</h1></html>".getBytes());
        textMessage.setQos(1);
        textMessage.setRetained(true);
        communication.publish(ToggleButtonService.INTENT_TOPIC_LABEL_TEXT, textMessage);
        communication.subscribe(TimerService.STATUS_TOPIC_STATE, 0);
        communication.subscribe(ToggleButtonService.EVENT_TOPIC_TOGGLE, 0);

    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Ouups, lost connection to subscirptions");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        if (TimerService.STATUS_TOPIC_STATE.equals(string)) {
            MqttMessage pauseMessage = new MqttMessage();
            if (TimerService.STATE_PAUSE.equals(new String(mm.getPayload()))) {
                pauseMessage.setPayload(ToggleButtonService.INTENT_TOGGLE_TRUE.getBytes());
                pauseMessage.setQos(1);
                communication.publish(ToggleButtonService.INTENT_TOPIC_TOGGLE_STATE, pauseMessage);
            }
            if (TimerService.STATE_RUNNING.equals(new String(mm.getPayload()))) {
                pauseMessage.setPayload(ToggleButtonService.INTENT_TOGGLE_FALSE.getBytes());
                pauseMessage.setQos(1);
                communication.publish(ToggleButtonService.INTENT_TOPIC_TOGGLE_STATE, pauseMessage);
            }

        }

        if (ToggleButtonService.EVENT_TOPIC_TOGGLE.equals(string)) {
            MqttMessage pauseMessage = new MqttMessage();
            if (ToggleButtonService.EVENT_TOGGLE_TRUE.equals(new String(mm.getPayload()))) {
                pauseMessage.setPayload(TimerService.INTENT_PAUSE.getBytes());
                pauseMessage.setQos(1);
                communication.publish(TimerService.INTENT_TOPIC_STATE, pauseMessage);
            }
            if (ToggleButtonService.EVENT_TOGGLE_FALSE.equals(new String(mm.getPayload()))) {
                pauseMessage.setPayload(TimerService.INTENT_RUN.getBytes());
                pauseMessage.setQos(1);
                communication.publish(TimerService.INTENT_TOPIC_STATE, pauseMessage);
            }
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        System.out.println("Delivery is done.");

    }

    public static void main(String[] args) throws MqttException {
        ToggleAgent agent = new ToggleAgent();
    }

}
