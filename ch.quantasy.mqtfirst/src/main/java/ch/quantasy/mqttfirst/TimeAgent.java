package ch.quantasy.mqttfirst;

import ch.quantasy.mqttfirst.mqtt.MqttClientBuilder;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TimeAgent {

    private MqttClient receiver;
    private long count;
    private static MqttClientBuilder builder = new MqttClientBuilder();
    String lastMessage;

    public TimeAgent() throws MqttException {
	receiver = builder.uri("tcp://" + MqttTimeService.BROKER + ":1883").clientUID("ch.quantasy.mqttfirst.TimeService").build();
	receiver.setCallback(new MqttCallback() {
	    @Override
	    public void connectionLost(Throwable throwable) {
	    }

	    @Override
	    public void messageArrived(String str, MqttMessage mqttMessage)
		    throws Exception {
		if (System.currentTimeMillis() > count + 500) {
		    count = System.currentTimeMillis();
		    byte[] payload = mqttMessage.getPayload();
		    lastMessage = new String(payload);
		    System.out.println("s = " + str + " msg "
			    + lastMessage);
		}
	    }

	    @Override
	    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
	    }
	});
	MqttConnectOptions options = new MqttConnectOptions();
	options.setCleanSession(false);
	receiver.connect(options);
	receiver.subscribe(MqttTimeService.TOPIC, 1);
    }

    public static void main(String[] args) throws IOException, MqttException {
	TimeAgent ta = new TimeAgent();
	System.in.read();
	ta.receiver.disconnect();
    }
}
