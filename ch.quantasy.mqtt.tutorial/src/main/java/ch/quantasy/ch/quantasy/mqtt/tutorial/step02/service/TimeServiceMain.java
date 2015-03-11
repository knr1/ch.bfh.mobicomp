package ch.quantasy.ch.quantasy.mqtt.tutorial.step02.service;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * This example demonstrates the 'retain' mechanism.<br/>
 * If a new subscriber should always get the latest message then a publish can be 'retained'.<br/>
 * This is indicated by the publisher.<br/>
 * The implementation of the subscriber does not have to be changed.<br/>
 * In order to demonstrate the 'retained' message, the subscriber prints out this information by accessing the message
 * object.
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
