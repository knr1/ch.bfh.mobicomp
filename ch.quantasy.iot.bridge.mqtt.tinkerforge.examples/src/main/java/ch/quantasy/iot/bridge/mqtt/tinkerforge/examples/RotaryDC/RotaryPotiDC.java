/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.bridge.mqtt.tinkerforge.examples.RotaryDC;

import java.io.IOException;
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
public class RotaryPotiDC {

    //public static final String CONNECTION = "tcp://iot.eclipse.org:1883";
    public static final String CONNECTION = "tcp://localhost:1883";
    //public static final String CONNECTION = "tcp://147.87.112.222:1883";

    public static final String UID = "RotaryPoti-DC-Example";

    private MqttAsyncClient client;

    public RotaryPotiDC() throws MqttException, InterruptedException {
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
	    client.subscribe("iot/tf/description/RotaryPoti/#", 0);
	    client.subscribe("iot/tf/localhost/4223/RotaryPoti/#", 0);
	    client.subscribe("iot/tf/#", 1);
	    client.publish("iot/tf/MQTT2TF/0/intent/<" + UID + ">/stackHandler/stackAddress", "{\"hostName\":\"localhost\",\"port\":4223}".getBytes(), 1, true).waitForCompletion();
	    client.publish("iot/tf/localhost/4223/RotaryPoti/etahff/intent/<" + UID + ">/positionCallbackPeriod/period", "1".getBytes(), 1, false);

	    client.publish("iot/tf/localhost/4223/DC/e1cw8v/intent/<" + UID + ">/driveMode/value", "0".getBytes(), 1, false);
	    client.publish("iot/tf/localhost/4223/DC/e1cw8v/intent/<" + UID + ">/enabled/value", "true".getBytes(), 1, false);
	    client.publish("iot/tf/localhost/4223/DC/e1cw8v/intent/<" + UID + ">/currentVelocityPeriod/period", "1".getBytes(), 1, false);
	    client.publish("iot/tf/localhost/4223/DC/e1cw8v/intent/<" + UID + ">/acceleration/value", "20000".getBytes(), 1, false);

	} catch (Exception ex) {
	    Logger.getLogger(RotaryPotiDC.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    public void disconnect() throws MqttException {
	client.publish("iot/tf/localhost/4223/DC/e1cw8v/intent/<" + UID + ">/velocity/value", "0".getBytes(), 1, false);
	client.publish("iot/tf/localhost/4223/RotaryPoti/etahff/intent/<" + UID + ">/positionCallbackPeriod/period", "0".getBytes(), 1, false);
	client.publish("iot/tf/localhost/4223/DC/etahff/intent/<" + UID + ">/enabled/value", "false".getBytes(), 1, false);

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
	    if (string.equals("iot/tf/localhost/4223/RotaryPoti/etahff/event/positionValue/value")) {
		int value = (int) ((32767 / 3) / 150.0 * Integer.parseInt(new String(mm.getPayload())));
		client.publish("iot/tf/localhost/4223/DC/e1cw8v/intent/<" + UID + ">/velocity/value", ("" + (value)).getBytes(), 1, false);
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
	RotaryPotiDC button = new RotaryPotiDC();
	button.connect();

	System.in.read();
	button.disconnect();
    }

}
