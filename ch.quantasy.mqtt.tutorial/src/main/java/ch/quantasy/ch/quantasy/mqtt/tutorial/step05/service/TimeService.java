package ch.quantasy.ch.quantasy.mqtt.tutorial.step05.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TimeService {

    public static final String PROTOCOL = "tcp";
    public static final String BROKER = "localhost";
    public static final String PORT = "1883";
    public static final URI SERVER_URI;
    public static final String CONNECTION_ID;
    public static final String TIME_SERVICE_TIME_10_TOPIC;
    public static final String TIME_SERVICE_TIME_5_TOPIC;

    static {
	SERVER_URI = URI.create(PROTOCOL + "://" + BROKER + ":" + PORT);
	CONNECTION_ID = "ch.quantasy.mqtt.tutorial.TimeService.step05";

	TIME_SERVICE_TIME_10_TOPIC = CONNECTION_ID + "/" + "delay/10/time";
	TIME_SERVICE_TIME_5_TOPIC = CONNECTION_ID + "/" + "delay/5/time";

    }

    private final MqttClient mqttClient;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> ticker10;
    private ScheduledFuture<?> ticker5;

    public TimeService() throws MqttException {
	mqttClient = new MqttClient(SERVER_URI.toString(), CONNECTION_ID);
    }

    public void connectToBroker() throws MqttException {
	MqttConnectOptions connectOptions = new MqttConnectOptions();

	mqttClient.connect();
    }

    public void disconnectFromBroker() throws MqttException {
	mqttClient.disconnect();
    }

    public void startPublishingTicks() {
	if (ticker10 != null) {
	    return;
	}
	ticker10 = scheduler.scheduleAtFixedRate(new TimeTickManager(TIME_SERVICE_TIME_10_TOPIC), 10, 10, TimeUnit.SECONDS);
	ticker5 = scheduler.scheduleAtFixedRate(new TimeTickManager(TIME_SERVICE_TIME_5_TOPIC), 5, 5, TimeUnit.SECONDS);
    }

    public void stopPublishingTicks() {
	if (ticker10 == null) {
	    return;
	}
	ticker10.cancel(true);
	ticker10 = null;
	ticker5.cancel(true);
	ticker5 = null;
    }

    class TimeTickManager implements Runnable {

	private final String topic;

	public TimeTickManager(String topic) {
	    this.topic = topic;
	}

	@Override
	public void run() {
	    try {
		Instant now = Instant.now();
		String nowString = now.toString();
		byte[] nowStringAsBytes = nowString.getBytes("UTF-8");
		MqttMessage message = new MqttMessage(nowStringAsBytes);
		message.setRetained(true);
		mqttClient.publish(topic, message);
	    } catch (UnsupportedEncodingException | MqttException ex) {
		Logger.getLogger(TimeService.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

    }

}
