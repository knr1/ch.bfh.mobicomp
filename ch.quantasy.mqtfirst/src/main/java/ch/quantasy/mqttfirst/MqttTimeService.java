package ch.quantasy.mqttfirst;

import ch.quantasy.mqttfirst.mqtt.MqttBuffer;
import ch.quantasy.mqttfirst.mqtt.MqttClientBuilder;
import java.io.IOException;
import java.time.Instant;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MqttTimeService {

    public static final String TOPIC = "ch.quantasy/mqttfirst/timeservice";
    public static final String BROKER = "localhost";  //broker

    private static MqttClientBuilder builder = new MqttClientBuilder();
    private final MqttBuffer buffer;

    public MqttTimeService() throws MqttException {
	MqttClient sender = builder
		.uri("tcp://" + BROKER + ":1883")
		.clientUIDGenerated()
		.build();

	sender.connect();

	buffer = new MqttBuffer() //Implementierung in Listing 6
		.client(sender).topic(TOPIC).qos(1).retained(true);
    }

    public void emmit() {
	try {
	    while (true) {
		Instant now = Instant.now();
		buffer.sendAsync(now.toString().getBytes("UTF-8"));
		Thread.sleep(1000);
	    }
	} catch (Exception ex) {

	}
    }

    public static void main(String[] args) throws MqttException, IOException {
	MqttTimeService service = new MqttTimeService();
	service.emmit();

	System.in.read();

    }
}
