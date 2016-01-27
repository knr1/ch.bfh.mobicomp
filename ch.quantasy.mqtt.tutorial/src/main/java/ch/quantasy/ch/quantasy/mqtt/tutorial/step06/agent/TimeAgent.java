/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.ch.quantasy.mqtt.tutorial.step06.agent;

import ch.quantasy.ch.quantasy.mqtt.tutorial.step06.service.TimeService;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TimeAgent {

    public static final String PROTOCOL = "tcp";
    public static final String BROKER = "localhost";
    public static final String PORT = "1883";
    public static final URI SERVER_URI;
    public static final String CONNECTION_ID;
    public static final String TIME_SERVICE_TIME_ANY_TOPIC;
    public static final String TIME_SERVICE_LASTWILL_TOPIC;

    static {
	SERVER_URI = URI.create(PROTOCOL + "://" + BROKER + ":" + PORT);
	CONNECTION_ID = "ch.quantasy.mqtt.tutorial.TimeAgent.step06";

	TIME_SERVICE_TIME_ANY_TOPIC = TimeService.CONNECTION_ID + "/delay/+/time";
	TIME_SERVICE_LASTWILL_TOPIC = TimeService.TIME_SERVICE_LASTWILL_TOPIC;

    }

    private final MqttClient mqttClient;

    private MqttCallback messageHandler;

    public TimeAgent() throws MqttException {
	mqttClient = new MqttClient(SERVER_URI.toString(), CONNECTION_ID);
    }

    public void connectToBroker() throws MqttException {
	MqttConnectOptions connectOptions = new MqttConnectOptions();
	connectOptions.setCleanSession(false);
	mqttClient.connect(connectOptions);
    }

    public void disconnectFromBroker() throws MqttException {
	mqttClient.disconnect();
    }

    public void startReceivingTicks() throws MqttException {
	mqttClient.setCallback(new MQTTMessageHandler());
	mqttClient.subscribe(TIME_SERVICE_TIME_ANY_TOPIC);
	mqttClient.subscribe(TIME_SERVICE_LASTWILL_TOPIC);

    }

    public void stopReceivingTicks() throws MqttException {
	System.exit(0);
	mqttClient.setCallback(null);
	mqttClient.unsubscribe(TIME_SERVICE_TIME_ANY_TOPIC);
	mqttClient.unsubscribe(TIME_SERVICE_LASTWILL_TOPIC);
    }

    class MQTTMessageHandler implements MqttCallback {

	@Override
	public void connectionLost(Throwable thrwbl) {
	    System.out.println("Connection Lost...");
	}

	@Override
	public void messageArrived(String string, MqttMessage mm) throws Exception {
	    System.out.printf("Topic: (%s) Payload: (%s) Retained: (%b) \n", string, new String(mm.getPayload()), mm.isRetained());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken imdt) {
	    System.out.println("Delivery Complete...");
	}
    }

}
