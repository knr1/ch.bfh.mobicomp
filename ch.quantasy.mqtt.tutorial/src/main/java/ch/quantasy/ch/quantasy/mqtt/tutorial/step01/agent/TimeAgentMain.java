package ch.quantasy.ch.quantasy.mqtt.tutorial.step01.agent;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * This example shows how to
 * <ul>
 * <li>Access an MQTT-Broker as a client (connect)</li>
 * <li>send a message (publish) to a MQTT-Broker </li>
 * <li>disconnect form an MQTT-Broker</li>
 * </ul>
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TimeAgentMain {

    public static void main(String[] args) throws MqttException, IOException {
	TimeAgent timeService = new TimeAgent();
	timeService.connectToBroker();

	System.out.println("I am going to receive ticks...");
	System.out.println("Broker: " + TimeAgent.BROKER);
	System.out.println("Topic (Time): " + TimeAgent.TIME_SERVICE_TIME_TOPIC);
	System.out.println("...press a key to end that.");
	timeService.startReceivingTicks();
	System.in.read();
	timeService.stopReceivingTicks();
	timeService.disconnectFromBroker();
	System.out.println("Done.");
	System.exit(0);
    }
}
