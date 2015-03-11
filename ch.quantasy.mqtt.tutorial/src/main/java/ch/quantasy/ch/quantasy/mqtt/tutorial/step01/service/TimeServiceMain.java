package ch.quantasy.ch.quantasy.mqtt.tutorial.step01.service;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * As a first example it is shown how to
 * <ul>
 * <li>Access an MQTT-Broker as a client (connect)</li>
 * <li>send a message (publish) to a MQTT-Broker </li>
 * <li>disconnect form an MQTT-Broker</li>
 * </ul>
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
