package ch.quantasy.mqttfirst.mqtt;

import java.util.UUID;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MqttClientBuilder {

    private String uri;
    private String clientUID;
    private boolean memoryPersistence = true;
    private boolean filePersistence = false;

    public MqttClient build() {
	MqttClient client;
	try {
	    if (memoryPersistence) {
		client = new MqttClient(uri, clientUID, new MemoryPersistence());
	    } else {
		client = new MqttClient(uri, clientUID,
					new MqttDefaultFilePersistence());
	    }
	} catch (MqttException e) {
	    e.printStackTrace();
	    client = null;
	}
	return client;
    }

    public MqttClientBuilder uri(String s) {
	this.uri = s;
	return this;
    }

    public MqttClientBuilder clientUID(String s) {
	this.clientUID = s;
	return this;
    }

    public MqttClientBuilder clientUIDGenerated() {
	String substring = UUID.randomUUID()
		.toString().replace("-", "").substring(0, 22);
	this.clientUID = substring;
	System.out.println("clientUID = " + clientUID);
	return this;
    }

    public MqttClientBuilder memoryPersistence(boolean b) {
	this.memoryPersistence = b;
	this.filePersistence = !this.memoryPersistence;
	return this;
    }

    public MqttClientBuilder filePersistence(boolean b) {
	this.filePersistence = b;
	this.memoryPersistence = !this.filePersistence;
	return this;
    }
}
