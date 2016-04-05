/*
 * Within this step, a service is using the MQTTCommunication and the 'business-logic'
 * The response of the 'business logic' is promoted to the event topic.
 * The 'business-logic' is invoked via the service and then is self-sustaining.
 * The 'business-logic' sends the result via Callback
 * This way we have a Model-'View'-Presenter (MVP) Where the presenter (the service) is glueing together
 * the Model ('business-logic') and the 'View' (the MQTT-Communication)
 * The service is promoting more status information to the status topic about the underlying 'business-logic'.
 *
 * This time, an agent is communicating with the service and controls it.
 * This way we delve into the Service based Agent oriented programming
 */
package ch.quantasy.iot.mqtt.timer.service;

import ch.quantasy.iot.mqtt.timer.communication.MQTTCommunication;
import ch.quantasy.iot.mqtt.timer.communication.MQTTParameters;
import ch.quantasy.iot.mqtt.timer.timer.TickTimer;
import ch.quantasy.iot.mqtt.timer.timer.TickTimerCallback;
import ch.quantasy.iot.mqtt.timer.timer.TickTimerParameters;
import java.net.URI;
import java.time.LocalDateTime;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class ConsoleService implements MqttCallback {

    public final static String CLIENT_ID = "Console";

    public final static String BASE_TOPIC = "ConsoleService";
    public final static String STATUS_TOPIC = BASE_TOPIC + "/status";
    public final static String EVENT_TOPIC = BASE_TOPIC + "/event";
    public final static String INTENT_TOPIC = BASE_TOPIC + "/intent";

    public final static String STATUS_TOPIC_CONNECTION = STATUS_TOPIC + "/connection";
    public final static String STATUS_CONNECTION_OFFLINE = "offline";
    public final static String STATUS_CONNECTION_ONLINE = "online";

    public static final String INTENT_TOPIC_PRINT = INTENT_TOPIC + "/print";
    public static final String TEXT = "/text";
    public static final String INTENT_TOPIC_PRINT_TEXT = INTENT_TOPIC_PRINT + TEXT;

    public static final String EVENT_TOPIC_PRINTED = EVENT_TOPIC + "/printed";

    private MQTTCommunication communication;

    public ConsoleService() throws MqttException {
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

        communication.subscribe(INTENT_TOPIC_PRINT + "/#", 0);

    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Ouups, lost connection to subscirptions");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        if (string.startsWith(INTENT_TOPIC_PRINT) && string.endsWith(TEXT)) {
            System.out.println(">>"+new String(mm.getPayload()));

            MqttMessage eventMessage = new MqttMessage(mm.getPayload());
            eventMessage.setQos(1);
            eventMessage.setRetained(true);
            communication.publish(EVENT_TOPIC_PRINTED, eventMessage);

        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        System.out.println("Delivery is done.");
    }

    public static void main(String[] args) throws MqttException {
        ConsoleService service = new ConsoleService();
    }

}
