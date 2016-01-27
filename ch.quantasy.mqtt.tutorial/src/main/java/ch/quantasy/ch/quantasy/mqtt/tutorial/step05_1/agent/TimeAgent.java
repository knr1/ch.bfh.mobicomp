package ch.quantasy.ch.quantasy.mqtt.tutorial.step05_1.agent;

import ch.quantasy.ch.quantasy.mqtt.tutorial.step05.service.TimeService;
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
    public static final String CONNECTION_ID_CLEAN;
    public static final String CONNECTION_ID_HISTORY;

    public static final String TIME_SERVICE_TIME_10_TOPIC;
    public static final String TIME_SERVICE_TIME_5_TOPIC;

    static {
	SERVER_URI = URI.create(PROTOCOL + "://" + BROKER + ":" + PORT);
	CONNECTION_ID_CLEAN = "ch.quantasy.mqtt.tutorial.TimeAgent.step05.clean";
	CONNECTION_ID_HISTORY = "ch.quantasy.mqtt.tutorial.TimeAgent.step05.history";

	TIME_SERVICE_TIME_10_TOPIC = TimeService.TIME_SERVICE_TIME_10_TOPIC;
	TIME_SERVICE_TIME_5_TOPIC = TimeService.TIME_SERVICE_TIME_5_TOPIC;

    }

    private final MqttClient mqttClientClean;
    private final MqttClient mqttClientHistory;

    private MqttCallback messageHandler;

    public TimeAgent() throws MqttException {
	mqttClientClean = new MqttClient(SERVER_URI.toString(), CONNECTION_ID_CLEAN);
	mqttClientHistory = new MqttClient(SERVER_URI.toString(), CONNECTION_ID_HISTORY);

    }

    public void connectToBroker() throws MqttException {
	MqttConnectOptions connectOptions;

	connectOptions = new MqttConnectOptions();
	connectOptions.setCleanSession(true);
	mqttClientClean.connect();

	connectOptions = new MqttConnectOptions();
	connectOptions.setCleanSession(false);
	mqttClientHistory.connect(connectOptions);

    }

    public void disconnectFromBroker() throws MqttException {
	mqttClientClean.disconnect();
	mqttClientHistory.disconnect();
    }

    public void startReceivingTicks() throws MqttException {
	mqttClientClean.setCallback(new MQTTMessageHandler());
	mqttClientHistory.setCallback(new MQTTMessageHandler());
	mqttClientClean.subscribe(TIME_SERVICE_TIME_10_TOPIC);
	mqttClientHistory.subscribe(TIME_SERVICE_TIME_5_TOPIC);

    }

    public void stopReceivingTicks() throws MqttException {
	System.exit(0);
	mqttClientClean.setCallback(null);
	mqttClientHistory.setCallback(null);
	mqttClientClean.unsubscribe(TIME_SERVICE_TIME_10_TOPIC);
	mqttClientHistory.unsubscribe(TIME_SERVICE_TIME_5_TOPIC);
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
