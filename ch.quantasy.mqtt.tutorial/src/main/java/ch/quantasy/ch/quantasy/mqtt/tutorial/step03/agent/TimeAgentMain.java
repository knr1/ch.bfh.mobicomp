package ch.quantasy.ch.quantasy.mqtt.tutorial.step03.agent;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * This example demonstrates the 'cleanSession=false' mechanism.<br/>
 * Per default, if a subscriber loses connection, it will not get the messages that have been provided meanwhile. It
 * will restart at the 'retained' message and will then keep on track with the publisher again.<br/>
 *
 * But if messages shall be delivered to a subscriber, after it recuperates from a 'black out', the subscriber can
 * request the broker to store the messages for him and deliver them once the publisher is back on again.<br/>
 * This is done by setting the connection to non-clean (setCleanSession to false). This will alert the broker to store
 * the messages in case the subscriber loses the session. (here it is simulated by a System.exit(0) in the
 * stopReceivingTicks() method. <br/>
 * Please note that if a subscriber (is) unsubscribe(d)(s) from a topic, the broker will not store the messages for this
 * subscriber anymore.
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
