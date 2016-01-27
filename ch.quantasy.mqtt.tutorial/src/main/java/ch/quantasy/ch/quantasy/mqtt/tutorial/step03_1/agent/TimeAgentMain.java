package ch.quantasy.ch.quantasy.mqtt.tutorial.step03_1.agent;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * This example demonstrates the 'cleanSession=false' mechanism (continued).<br/>
 * If a subscriber comes back online carrying the same connectionID, the broker will send all the stored data to this
 * connection.<br/>
 * As you can see here, the broker discriminates the different subscribers only by their connectionID, hence even a
 * different program can recuperate the stored data on the broker, as long as the connectionID matches
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
