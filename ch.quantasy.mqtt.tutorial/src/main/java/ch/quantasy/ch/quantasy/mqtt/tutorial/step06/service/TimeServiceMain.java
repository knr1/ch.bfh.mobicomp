package ch.quantasy.ch.quantasy.mqtt.tutorial.step06.service;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * This example demonstrates the last will (testament).<br/>
 * If a publisher 'dies' unexpectedly, a last will can be opened by the broker.<br/>
 * In order to do so, the broker receives a testament by the publisher (usually just after connection build up). <br/>
 * However, the last will can be changed by the publisher at any time while it is still connected to the broker by
 * sending a new will. <br/>
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
	System.out.println("Topic (Testament): " + TimeService.TIME_SERVICE_LASTWILL_TOPIC);

	System.out.println("...press a key to disrupt the network (simulated by System.exit(0)).");
	System.in.read();
	//timeService.stopPublishingTicks();
	//timeService.disconnectFromBroker();
	System.out.println("Done.");
	System.exit(0);
    }
}
