package ch.quantasy.ch.quantasy.mqtt.tutorial.step04.service;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * This example demonstrates the publisher's persistence ability.<br/>
 * If a publisher loses connection to the broker, it will not send any message and the broker cannot store them. <br/>
 * Hence, the publisher itself has to store those messages meanwhile and as soon as the connection to the broker is
 * established again, the messages will be sent to the broker.<br/>
 *
 * This requires a persistence capability at the publisher's side (which is not always given).<br/>
 * As a side effect, if a new subscriber subscribes to the publisher's topic with cleanSession=false, some of the
 * historic data can be sent from the publisher via broker to the subscriber...
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
	System.out.println("...press a key invoke network disruption.");
	System.in.read();
	//System.out.println("Network fades out...");
	//timeService.disconnectFromBroker();
	//System.in.read();
	//System.out.println("...Network comes back online again.");
	//timeService.connectToBroker();
	System.out.println("That does not work yet like that but will be available in a future release (about June 2015) of paho...");
	System.out.println("Instead try to drop the connection to your mosquitto broker by disrupting the network");
	System.in.read();
	System.out.println("...press a key to end that.");

	timeService.stopPublishingTicks();
	timeService.disconnectFromBroker();
	System.out.println("Done.");
	System.exit(0);
    }
}
