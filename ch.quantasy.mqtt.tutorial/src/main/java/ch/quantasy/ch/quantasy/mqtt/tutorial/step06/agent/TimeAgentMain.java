package ch.quantasy.ch.quantasy.mqtt.tutorial.step06.agent;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * This example demonstrates how a subscriber can listen to the publisher's last will.<br/>
 * The testament containing the last will is treated just like any other topic the subscriber is interested in. <br/>
 * The broker will publish the testament as soon as it realizes that the publisher 'droped out of sight' , hence the
 * connection is lost without prior disconnect.
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TimeAgentMain {

    public static void main(String[] args) throws MqttException, IOException {
	TimeAgent timeService = new TimeAgent();
	timeService.connectToBroker();

	System.out.println("I am going to receive ticks...");
	System.out.println("Broker: " + TimeAgent.BROKER);
	System.out.println("Topic (Time): " + TimeAgent.TIME_SERVICE_TIME_ANY_TOPIC);
	System.out.println("Topic (Time): " + TimeAgent.TIME_SERVICE_LASTWILL_TOPIC);

	System.out.println("...press a key to end that.");
	timeService.startReceivingTicks();
	System.in.read();
	timeService.stopReceivingTicks();
	timeService.disconnectFromBroker();
	System.out.println("Done.");
	System.exit(0);
    }
}
