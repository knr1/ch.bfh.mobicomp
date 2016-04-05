/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tutorial.step06a.agent;

import ch.quantasy.iot.mqtt.tutorial.step06a.communication.MQTTCommunication;
import ch.quantasy.iot.mqtt.tutorial.step06a.communication.MQTTParameters;
import ch.quantasy.iot.mqtt.tutorial.step06a.service.TimerService;
import ch.quantasy.iot.mqtt.tutorial.step06a.service.ConsoleService;
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
public class TimerAgent implements MqttCallback {

    public final static String CLIENT_ID = "TimerAgent01";

    public final static String BASE_TOPIC = "TimerAgent";
    public final static String STATUS_TOPIC = BASE_TOPIC + "/status";
    public final static String STATUS_TOPIC_CONNECTION = STATUS_TOPIC + "/connection";
    public final static String STATUS_CONNECTION_OFFLINE = "offline";
    public final static String STATUS_CONNECTION_ONLINE = "online";

    private MQTTCommunication communication;

    public TimerAgent() throws MqttException {
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
        MqttMessage stateMessage = new MqttMessage(TimerService.STATE_RUNNING.getBytes());
        stateMessage.setQos(1);
        stateMessage.setRetained(true);
        communication.publish(TimerService.STATUS_TOPIC_STATE, stateMessage);
        communication.subscribe(TimerService.BASE_TOPIC + "/#", 0);
    }

    public void go() {
        try {
            while (true) {
                MqttMessage stateMessagePause = new MqttMessage(TimerService.INTENT_PAUSE.getBytes());
                stateMessagePause.setQos(1);
                stateMessagePause.setRetained(true);
                communication.publish(TimerService.INTENT_TOPIC_STATE, stateMessagePause);
                Thread.sleep(10000);
                MqttMessage stateMessageRun = new MqttMessage(TimerService.INTENT_RUN.getBytes());
                stateMessageRun.setQos(1);
                stateMessageRun.setRetained(true);
                communication.publish(TimerService.INTENT_TOPIC_STATE, stateMessageRun);
                Thread.sleep(10000);

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(TimerAgent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Ouups, lost connection to subscirptions");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        if (TimerService.EVENT_TOPIC_TIME.equals(string)) {
            MqttMessage printMessage = new MqttMessage(mm.getPayload());
            printMessage.setQos(1);
            communication.publish(ConsoleService.INTENT_TOPIC_PRINT_TEXT, printMessage);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        System.out.println("Delivery is done.");

    }

    public static void main(String[] args) throws MqttException {
        TimerAgent agent = new TimerAgent();
        agent.go();
    }

}
