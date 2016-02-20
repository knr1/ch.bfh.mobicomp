/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.bridge.mqtt.tinkerforge.examples.JoystickDCs;

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
public class JoysticksDCs {

    //public static final String CONNECTION = "tcp://iot.eclipse.org:1883";
    public static final String CONNECTION = "tcp://localhost:1883";
    //public static final String CONNECTION = "tcp://147.87.112.222:1883";

    public static final String UID = "RotaryPoti-DC-Example";

    private MqttAsyncClient client;

    public JoysticksDCs() throws MqttException, InterruptedException {
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
	    client.subscribe("iot/tf/localhost/4223/Joystick/#", 0);
	    client.publish("iot/tf/MQTT2TF/0/intent/<" + UID + ">/stackHandler/stackAddress", "{\"hostName\":\"localhost\",\"port\":4223}".getBytes(), 1, true).waitForCompletion();
	    client.publish("iot/tf/localhost/4223/Joystick/df9sif/intent/<" + UID + ">/positionCallbackPeriod/period", "10".getBytes(), 1, false);
//
	    client.publish("iot/tf/localhost/4223/DC/6ahn4g/intent/<" + UID + ">/driveMode/value", "1".getBytes(), 1, false).waitForCompletion();
	    client.publish("iot/tf/localhost/4223/DC/6ahn4g/intent/<" + UID + ">/enabled/value", "true".getBytes(), 1, false).waitForCompletion();
	    //client.publish("iot/tf/localhost/4223/DC/6ahn4g/intent/<" + UID + ">/currentVelocityPeriod/period", "1".getBytes(), 1, false).waitForCompletion();
	    client.publish("iot/tf/localhost/4223/DC/6ahn4g/intent/<" + UID + ">/acceleration/value", "20000".getBytes(), 1, false).waitForCompletion();

	    client.publish("iot/tf/localhost/4223/DC/69o8jl/intent/<" + UID + ">/driveMode/value", "1".getBytes(), 1, false).waitForCompletion();
	    client.publish("iot/tf/localhost/4223/DC/69o8jl/intent/<" + UID + ">/enabled/value", "true".getBytes(), 1, false).waitForCompletion();
	    //client.publish("iot/tf/localhost/4223/DC/69o8jl/intent/<" + UID + ">/currentVelocityPeriod/period", "1".getBytes(), 1, false).waitForCompletion();
	    client.publish("iot/tf/localhost/4223/DC/69o8jl/intent/<" + UID + ">/acceleration/value", "20000".getBytes(), 1, false).waitForCompletion();

	} catch (Exception ex) {
	    Logger.getLogger(JoysticksDCs.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    public void disconnect() throws MqttException {
	client.publish("iot/tf/localhost/4223/DC/69o8jl/intent/<" + UID + ">/velocity/value", "0".getBytes(), 1, false);
	client.publish("iot/tf/localhost/4223/DC/69o8jl/intent/<" + UID + ">/enabled/value", "false".getBytes(), 1, false);
	client.publish("iot/tf/localhost/4223/DC/6ahn4g/intent/<" + UID + ">/velocity/value", "0".getBytes(), 1, false);
	client.publish("iot/tf/localhost/4223/DC/6ahn4g/intent/<" + UID + ">/enabled/value", "false".getBytes(), 1, false);

	client.publish("iot/tf/localhost/4223/Joystick/df9sif/intent/<" + UID + ">/positionCallbackPeriod/period", "0".getBytes(), 1, false);

	client.disconnect();
    }

    class MQTTCallbackHandler implements MqttCallback {

	private int velocityAll;
	private int steering;
	private boolean brake;
	private int left;
	private int right;

	@Override
	public void connectionLost(Throwable thrwbl) {
	    System.out.printf("Ou: We lost connection: %s \n", thrwbl);
	}

	@Override
	public void messageArrived(String string, MqttMessage mm) throws Exception {
	    System.out.printf("Hey, some message arrived: Topic: %s, message: %s \n", string, mm.toString());
	    if (string.equals("iot/tf/localhost/4223/Joystick/df9sif/event/isPressed/value")) {
		brake = new Boolean(new String(mm.getPayload()));
		if (brake) {
		    motorFullBrake();
		}
	    }
	    if (brake) {
		return;
	    }

	    if (string.equals("iot/tf/localhost/4223/Joystick/df9sif/event/positionValue/valueY")) {
		velocityAll = (int) ((32767 / 2) / 100.0 * Integer.parseInt(new String(mm.getPayload())));
	    }
	    if (string.equals("iot/tf/localhost/4223/Joystick/df9sif/event/positionValue/valueX")) {
		steering = (int) ((32767 / 2) / 100.0 * Integer.parseInt(new String(mm.getPayload())));
	    }

	    int newLeft = velocityAll + steering;
	    int newRight = (velocityAll - steering) * -1;
	    if (newLeft == left && newRight == right) {
		return;
	    }
	    left = newLeft;
	    right = newRight;
	    try {
		client.publish("iot/tf/localhost/4223/DC/6ahn4g/intent/<" + UID + ">/velocity/value", ("" + (left)).getBytes(), 1, false);
		client.publish("iot/tf/localhost/4223/DC/69o8jl/intent/<" + UID + ">/velocity/value", ("" + (right)).getBytes(), 1, false).waitForCompletion(10);
	    } catch (Exception ex) {
	    };
	}

	private void motorFullBrake() throws Exception {
	    try {
		client.publish("iot/tf/localhost/4223/DC/6ahn4g/intent/<" + UID + ">/fullBrake/enabled", "true".getBytes(), 1, false);
		client.publish("iot/tf/localhost/4223/DC/69o8jl/intent/<" + UID + ">/fullBrake/enabled", "true".getBytes(), 1, false).waitForCompletion(10);
	    } catch (Exception ex) {
	    };
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken imdt) {
	    //  System.out.printf("Delivery complete: %s \n", imdt);
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
	JoysticksDCs button = new JoysticksDCs();
	button.connect();

	System.in.read();
	button.disconnect();
    }

}
