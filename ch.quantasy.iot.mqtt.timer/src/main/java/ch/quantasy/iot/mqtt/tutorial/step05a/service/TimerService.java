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
package ch.quantasy.iot.mqtt.tutorial.step05a.service;

import ch.quantasy.iot.mqtt.tutorial.step05a.communication.MQTTCommunication;
import ch.quantasy.iot.mqtt.tutorial.step05a.communication.MQTTParameters;
import ch.quantasy.iot.mqtt.tutorial.step05a.timer.TickTimer;
import ch.quantasy.iot.mqtt.tutorial.step05a.timer.TickTimerCallback;
import ch.quantasy.iot.mqtt.tutorial.step05a.timer.TickTimerParameters;
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
public class TimerService implements MqttCallback, TickTimerCallback {

    public final static String CLIENT_ID = "Tick_Tack";

    public final static String BASE_TOPIC = "TimerService";
    public final static String STATUS_TOPIC = BASE_TOPIC + "/status";
    public final static String EVENT_TOPIC = BASE_TOPIC + "/event";
    public final static String INTENT_TOPIC = BASE_TOPIC + "/intent";

    public final static String STATUS_TOPIC_PERIOD = STATUS_TOPIC + "/period";
    public final static String STATUS_TOPIC_CONNECTION = STATUS_TOPIC + "/connection";
    public final static String STATUS_CONNECTION_OFFLINE = "offline";
    public final static String STATUS_CONNECTION_ONLINE = "online";

    public final static String STATUS_TOPIC_STATE = STATUS_TOPIC + "/state";
    public final static String STATE_RUNNING = "running";
    public final static String STATE_PAUSE = "pause";

    public static final String INTENT_TOPIC_STATE = INTENT_TOPIC + "/state";
    public final static String INTENT_RUN = "run";
    public final static String INTENT_PAUSE = "pause";

    public static final String EVENT_TOPIC_TIME = EVENT_TOPIC + "/time";

    private MQTTCommunication communication;
    private TickTimer tickTimer;

    public TimerService() throws MqttException {
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
        MqttMessage stateMessage = new MqttMessage(STATE_RUNNING.getBytes());
        stateMessage.setQos(1);
        stateMessage.setRetained(true);
        communication.publish(STATUS_TOPIC_STATE, stateMessage);
        communication.subscribe(BASE_TOPIC + "/#", 0);

        tickTimer = new TickTimer();
        TickTimerParameters tickTimerParameters = new TickTimerParameters();
        tickTimerParameters.setPeriodInMilliSeconds(1000);
        tickTimerParameters.setTimerCallback(this);
        MqttMessage periodMessage = new MqttMessage(("" + tickTimerParameters.getPeriodInMilliSeconds()).getBytes());
        periodMessage.setQos(1);
        periodMessage.setRetained(true);
        communication.publish(STATUS_TOPIC_PERIOD, periodMessage);
        tickTimer.tick(tickTimerParameters);

    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Ouups, lost connection to subscirptions");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        if (INTENT_TOPIC_STATE.equals(string)) {
            System.out.printf("State intent message has been received. Topic: %s, Message: %s \n", string, new String(mm.getPayload()));
        
            boolean oldStatus = tickTimer.isPaused();
            String stringMessage = new String(mm.getPayload());
            if (INTENT_PAUSE.equals(stringMessage)) {
                tickTimer.setPause(true);
            }
            if (INTENT_RUN.equals(stringMessage)) {
                tickTimer.setPause(false);
            }
            boolean newStatus = tickTimer.isPaused();
            if (oldStatus != newStatus) {
                String runStatus = (newStatus ? STATE_PAUSE : STATE_RUNNING);
                MqttMessage statusMessage = new MqttMessage((runStatus).getBytes());
                statusMessage.setQos(1);
                statusMessage.setRetained(true);
                communication.publish(STATUS_TOPIC_STATE, statusMessage);
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        System.out.println("Delivery is done.");
    }

    @Override
    public void tick(LocalDateTime result) {
        MqttMessage message = new MqttMessage(result.toString().getBytes());
        message.setQos(1);
        message.setRetained(true);
        this.communication.publish(EVENT_TOPIC_TIME, (message));
    }

    public static void main(String[] args) throws MqttException {
        TimerService service = new TimerService();
    }

}
