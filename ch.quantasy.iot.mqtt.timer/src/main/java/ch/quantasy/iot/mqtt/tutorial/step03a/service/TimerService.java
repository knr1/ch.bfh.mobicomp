/*
 * Within this step, a service is using the MQTTCommunication and the 'business-logic'
 * The response of the 'business logic' is promoted to the event topic.
 * The 'business-logic' is invoked via the service and then is self-sustaining.
 * The 'business-logic' sends the result via Callback
 * This way we have a Model-'View'-Presenter (MVP) Where the presenter (the service) is glueing together
 * the Model ('business-logic') and the 'View' (the MQTT-Communication)
 */
package ch.quantasy.iot.mqtt.tutorial.step03a.service;


import ch.quantasy.iot.mqtt.tutorial.step03a.communication.MQTTCommunication;
import ch.quantasy.iot.mqtt.tutorial.step03a.communication.MQTTParameters;
import ch.quantasy.iot.mqtt.tutorial.step03a.timer.TickTimer;
import ch.quantasy.iot.mqtt.tutorial.step03a.timer.TickTimerCallback;
import ch.quantasy.iot.mqtt.tutorial.step03a.timer.TickTimerParameters;
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

    public final static String CLIENT_ID="Tick_Tack";
    
    public final static String BASE_TOPIC = "TimerService";
    public final static String STATUS_TOPIC = BASE_TOPIC + "/status";
    public final static String EVENT_TOPIC = BASE_TOPIC + "/event";

    public final static String STATUS_TOPIC_CONNECTION = STATUS_TOPIC + "/connection";
    public final static String STATUS_CONNECTION_OFFLINE="offline";
    public final static String STATUS_CONNECTION_ONLINE="online";
    

    public static final String EVENT_TOPIC_TIME = EVENT_TOPIC+"/time";

    private final MQTTCommunication communication;
    private final TickTimer tickTimer;

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
        communication.subscribe(BASE_TOPIC+"/#", 0);

        tickTimer = new TickTimer();
        TickTimerParameters tickTimerParameters = new TickTimerParameters();
        tickTimerParameters.setPeriodInMilliSeconds(1000);
        tickTimerParameters.setTimerCallback(this);
        tickTimer.tick(tickTimerParameters);

    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Ouups, lost connection to subscirptions");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.printf("Message has been delivered and is back again. Topic: %s, Message: %s \n", string, new String(mm.getPayload()));
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
        TimerService service=new TimerService();
        
    }

}
