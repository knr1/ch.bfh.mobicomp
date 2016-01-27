package ch.quantasy.ch.quantasy.mqtt.tutorial.step03.service;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TimeServiceMain {

    public static void main(String[] args) throws MqttException, IOException {
	TimeService timeService = new TimeService();
	timeService.connectToBroker();
	timeService.startPublishingTicks();
	System.out.println("I am ticking...");
	System.out.println("Broker: " + TimeService.BROKER);
	System.out.println("Topic (Time): " + TimeService.TIME_SERVICE_TIME_TOPIC);
	System.out.println("...press a key to end that.");
	System.in.read();
	timeService.stopPublishingTicks();
	timeService.disconnectFromBroker();
	System.out.println("Done.");
	System.exit(0);
    }
}
