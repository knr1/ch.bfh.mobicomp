/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.bridge.mqtt.tinkerforge.examples.LCD20x4;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class Button {

    public static final String CONNECTION = "tcp://iot.eclipse.org:1883";
    //public static final String CONNECTION = "tcp://localhost:1883";
    //public static final String CONNECTION = "tcp://147.87.112.222:1883";

    public static final String UID = "Button";

    private MqttAsyncClient client;

    public Button() throws MqttException, InterruptedException {
	client = new MqttAsyncClient(CONNECTION, UID, new MemoryPersistence());
	client.setCallback(new MQTTCallbackHandler());
    }

    public void connect() throws MqttException {
	MqttConnectOptions options = new MqttConnectOptions();
	//options.setUserName("user");
	//options.setPassword("pass".toCharArray());
	options.setWill(UID + "/status", "connection broken".getBytes(), 1, true);
	options.setCleanSession(true);
	IMqttToken token = client.connect(options, null, new MQTTActionHandler());
	token.waitForCompletion();
	try {
	    client.subscribe("iot/tf/description/LCD20x4/#", 1);
	    client.subscribe("iot/tf/localhost/4223/LCD20x4/#", 1);
	    client.subscribe("iot/tf/#", 1);
	    client.publish("iot/tf/MQTT2TF/0/intent/<" + UID + ">/stackHandler/stackAddress", "{\"hostName\":\"localhost\",\"port\":4223}".getBytes(), 1, true);
	    Thread.sleep(1000);

	} catch (Exception ex) {
	    Logger.getLogger(Button.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    public void disconnect() throws MqttException {
	client.disconnect();
    }

    class MQTTCallbackHandler implements MqttCallback {

	@Override
	public void connectionLost(Throwable thrwbl) {
	    System.out.printf("Ou: We lost connection: %s \n", thrwbl);
	}

	private int trigger = 0;

	@Override
	public void messageArrived(String string, MqttMessage mm) throws Exception {
	    System.out.printf("Hey, some message arrived: Topic: %s, message: %s \n", string, mm.toString());
	    if (string.endsWith("dzbt1t/event/buttonPressed/0")) {
		if (Arrays.equals(mm.getPayload(), "true".getBytes())) {
		    client.publish("iot/tf/localhost/4223/LCD20x4/dzbt1t/intent/<" + UID + ">/write/enabled", ("false").getBytes(), 1, true);
		    client.publish("iot/tf/localhost/4223/LCD20x4/dzbt1t/intent/<" + UID + ">/write/line", ("3").getBytes(), 1, true);
		    client.publish("iot/tf/localhost/4223/LCD20x4/dzbt1t/intent/<" + UID + ">/write/from", ("0").getBytes(), 1, true);
		    client.publish("iot/tf/localhost/4223/LCD20x4/dzbt1t/intent/<" + UID + ">/write/text", ("----------" + (trigger++) + "----------").getBytes(), 1, true);
		    client.publish("iot/tf/localhost/4223/LCD20x4/dzbt1t/intent/<" + UID + ">/write/enabled", ("true").getBytes(), 1, true);
		}
	    }

	    if (string.endsWith("dzbt1t/event/buttonPressed/3")) {
		client.publish("iot/tf/localhost/4223/LCD20x4/dzbt1t/intent/<" + UID + ">/backlight/enabled", mm.getPayload(), 1, true);

	    }
	    if (string.endsWith("dzbt1t/event/buttonPressed/2")) {
		client.publish("iot/tf/localhost/4223/LCD20x4/dzbt1t/intent/<" + UID + ">/backlight/enabled", ("" + true).getBytes(), 1, true);

	    }
	    if (string.endsWith("dzbt1t/event/buttonPressed/1")) {
		client.publish("iot/tf/localhost/4223/LCD20x4/dzbt1t/intent/<" + UID + ">/backlight/enabled", ("" + false).getBytes(), 1, true);

	    }

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken imdt) {
	    System.out.printf("Delivery complete: %s \n", imdt);
	}

    }

    class MQTTActionHandler implements IMqttActionListener {

	@Override
	public void onSuccess(IMqttToken imt) {
	    System.out.printf("Great it worked: %s \n", (imt.getResponse().toString()));
	}

	@Override
	public void onFailure(IMqttToken imt, Throwable thrwbl) {
	    System.out.printf("Nope did not work: %s Throwable was: %s \n", imt, thrwbl);
	}

    }

    public static void main(String[] args) throws MqttException, InterruptedException, IOException {
	Button button = new Button();
	button.connect();

	System.in.read();
	button.disconnect();
    }

}
