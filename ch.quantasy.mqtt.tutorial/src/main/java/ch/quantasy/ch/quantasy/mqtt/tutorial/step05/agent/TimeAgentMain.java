package ch.quantasy.ch.quantasy.mqtt.tutorial.step05.agent;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * This example demonstrates how a subscriber can listen to multiple topics on a single connection.<br/>
 * If a subscriber wants to listen to multiple topics, it can register itself explicitly.
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TimeAgentMain {

    public static void main(String[] args) throws MqttException, IOException {
	TimeAgent timeService = new TimeAgent();
	timeService.connectToBroker();

	System.out.println("I am going to receive ticks...");
	System.out.println("Broker: " + TimeAgent.BROKER);
	System.out.println("Topic (Time): " + TimeAgent.TIME_SERVICE_TIME_10_TOPIC);
	System.out.println("Topic (Time): " + TimeAgent.TIME_SERVICE_TIME_5_TOPIC);

	System.out.println("...press a key to end that.");
	timeService.startReceivingTicks();
	System.in.read();
	timeService.stopReceivingTicks();
	timeService.disconnectFromBroker();
	System.out.println("Done.");
	System.exit(0);
    }
}
