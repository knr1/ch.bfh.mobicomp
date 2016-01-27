package ch.quantasy.ch.quantasy.mqtt.tutorial.step05.service;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * This example demonstrates how a publisher can publish multiple topics.<br/>
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
	System.out.println("Topic (Time 10 sec): " + TimeService.TIME_SERVICE_TIME_10_TOPIC);
	System.out.println("Topic (Time 5 sec): " + TimeService.TIME_SERVICE_TIME_5_TOPIC);

	System.out.println("...press a key to end that.");
	System.in.read();
	timeService.stopPublishingTicks();
	timeService.disconnectFromBroker();
	System.out.println("Done.");
	System.exit(0);
    }
}
