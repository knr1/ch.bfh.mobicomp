package ch.quantasy.mqttfirst.mqtt;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MqttBuffer {

    static final ExecutorService fixedThreadPool
	    = Executors.newFixedThreadPool(4);

    private String topic;
    private MqttClient client;
    private int qos = 1;
    private boolean retained = true;

    public MqttBuffer topic(String s) {
	this.topic = s;
	return this;
    }

    public MqttBuffer client(MqttClient c) {
	this.client = c;
	return this;
    }

    public MqttBuffer qos(int q) {
	this.qos = q;
	return this;
    }

    public MqttBuffer retained(boolean b) {
	this.retained = b;
	return this;
    }

    public void sendAsync(byte[] msg) {
	Supplier<Boolean> task = () -> {
	    try {
		client.publish(topic, msg,
			       qos, retained);
	    } catch (MqttException e) {
		e.printStackTrace();
		return false;
	    }
	    return true;
	};
	CompletableFuture.supplyAsync(task, fixedThreadPool);
	//.thenAccept(System.out::println);
    }
}
