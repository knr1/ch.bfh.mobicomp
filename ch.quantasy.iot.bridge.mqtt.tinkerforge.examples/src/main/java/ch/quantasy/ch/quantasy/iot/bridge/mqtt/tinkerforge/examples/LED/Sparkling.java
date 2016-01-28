/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.ch.quantasy.iot.bridge.mqtt.tinkerforge.examples.LED;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Random;
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
public class Sparkling {

    public static final String CONNECTION = "tcp://localhost:1883";
    public static final String UID = "Sparkling";

    private MqttAsyncClient client;

    private short[][] sparklers = new short[3][250];
    private Random random;
    public static final int RED = 2;
    public static final int GREEN = 1;
    public static final int BLUE = 0;

    public Sparkling() throws MqttException, InterruptedException {
	random = new Random();
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
	    client.subscribe("iot/tf/description/LEDStrip/#", 1);
	    client.subscribe("iot/tf/xmas/4223/LEDStrip/#", 1);
	    client.subscribe("iot/tf/#", 1);
	    client.publish("iot/tf/MQTT2TF/0/intent/<" + UID + ">/stackHandler/stackAddress", "{\"hostName\":\"xmas\",\"port\":4223}".getBytes(), 1, true);
	    Thread.sleep(1000);
	    client.publish("iot/tf/xmas/4223/LEDStrip/dpu9q2/intent/<" + UID + ">/config/enabled", "false".getBytes(), 1, true);
	    Thread.sleep(1000);

	    client.publish("iot/tf/xmas/4223/LEDStrip/dpu9q2/intent/<" + UID + ">/config/chipType", "2801".getBytes(), 1, true);
	    client.publish("iot/tf/xmas/4223/LEDStrip/dpu9q2/intent/<" + UID + ">/config/clockFrequencyOfICsInHz", "2000000".getBytes(), 1, true);
	    client.publish("iot/tf/xmas/4223/LEDStrip/dpu9q2/intent/<" + UID + ">/config/frameDurationInMilliseconds", "10".getBytes(), 1, true);
	    client.publish("iot/tf/xmas/4223/LEDStrip/dpu9q2/intent/<" + UID + ">/config/numberOfLEDs", "250".getBytes(), 1, true);
	    client.publish("iot/tf/xmas/4223/LEDStrip/dpu9q2/intent/<" + UID + ">/config/enabled", "true".getBytes(), 1, true);
	    Thread.sleep(1000);

	} catch (Exception ex) {
	    Logger.getLogger(Sparkling.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    public void sparkle() throws MqttException, InterruptedException {
	for (int i = 0; i < 250; i++) {
	    sparklers[RED][i] = (short) (240 + random.nextInt(30) - 15);
	}
	for (int i = 0; i < 250; i++) {
	    sparklers[GREEN][i] = (short) (70 + random.nextInt(20) - 10);
	}
	for (int i = 0; i < 250; i++) {
	    sparklers[BLUE][i] = (short) (6 + random.nextInt(12) - 6);
	}
	int spark = random.nextInt(250);
	sparklers[RED][spark] = 255;
	sparklers[GREEN][spark] = 255;
	sparklers[BLUE][spark] = 255;

	Gson gson = new Gson();
	String sparklerString = gson.toJson(sparklers);
	client.publish("iot/tf/xmas/4223/LEDStrip/dpu9q2/intent/<" + UID + ">/RGBFrame/rgbFrame", sparklerString.getBytes(), 1, true);
    }

    public void disconnect() throws MqttException {
	client.disconnect();
    }

    class MQTTCallbackHandler implements MqttCallback {

	@Override
	public void connectionLost(Throwable thrwbl) {
	    System.out.printf("Ou: We lost connection: %s \n", thrwbl);
	}

	@Override
	public void messageArrived(String string, MqttMessage mm) throws Exception {
	    System.out.printf("Hey, some message arrived: Topic: %s, message: %s \n", string, mm.toString());
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
	Sparkling sparkling = new Sparkling();
	sparkling.connect();
	while (true) {
	    try {
		sparkling.sparkle();
		Thread.sleep(100);
	    } catch (Exception x) {
	    };
	}

//	System.in.read();
//	sparkling.disconnect();
    }

}
