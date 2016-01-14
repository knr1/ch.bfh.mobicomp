/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.base;

import ch.quantasy.iot.mqtt.base.message.AStatus;
import ch.quantasy.iot.mqtt.base.message.AnEvent;
import ch.quantasy.iot.mqtt.base.message.AnIntent;
import ch.quantasy.iot.mqtt.base.status.HandlerReadyStatus;
import ch.quantasy.iot.mqtt.tinkerforge.gateway.MQTT2TF;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class AHandler implements MqttCallback {

    public static final String REACHABLE = "reachable";
    public static final String DESCRIPTION = "description";

    public static final String DEVICE_DESCRIPTION_TOPIC = MQTT2TF.TOPIC + "/" + DESCRIPTION;

    private final String identityString;
    private final Set<Class> intentSet;
    private final Map<String, Map<Class, AnIntent>> intentsMap;
    private final Map<Class, AnEvent> eventMap;
    private final Map<Class, AStatus> statusMap;
    private final String statusTopic;
    private final String eventTopic;
    private final String intentTopic;
    private final MqttAsyncClient mqttClient;
    private final String deviceNameTopic;
    private final URI mqttURI;

    public AHandler(URI mqttURI, String deviceNameRootTopic, String identityString) throws Throwable {
	this.mqttURI = mqttURI;
	this.identityString = identityString;
	this.deviceNameTopic = deviceNameRootTopic + "/" + getApplicationName() + "/" + identityString;;
	this.intentTopic = getDeviceBaseTopic() + "/intent";
	this.eventTopic = getDeviceBaseTopic() + "/event";
	this.statusTopic = getDeviceBaseTopic() + "/status";

	this.mqttClient = new MqttAsyncClient(mqttURI.toString(), identityString, new MemoryPersistence());
	this.intentSet = new HashSet<>();
	this.intentsMap = new HashMap<>();
	this.eventMap = new HashMap<>();
	this.statusMap = new HashMap<>();
	this.addStatusClass(HandlerReadyStatus.class);
	connectToMQTT();
    }

    public URI getMqttURI() {
	return mqttURI;
    }

    public String getIdentityString() {
	return identityString;
    }

    public <T> T getEvent(Class<T> classOfT) {
	return (T) eventMap.get(classOfT);
    }

    public <T> T getStatus(Class<T> classOfT) {
	return (T) statusMap.get(classOfT);
    }

    protected void connectToMQTT() throws MqttException {
	MqttConnectOptions connectOptions = new MqttConnectOptions();
	connectOptions.setCleanSession(false);
	HandlerReadyStatus readyStatus = getStatus(HandlerReadyStatus.class);
	MqttMessage message = readyStatus.toJSONMQTTMessage(false);
	connectOptions.setWill(readyStatus.getTopic() + "/" + REACHABLE, message.getPayload(), 1, true);
	this.mqttClient.setCallback(this);
	IMqttToken token = this.mqttClient.connect(connectOptions);
	token.waitForCompletion();
	this.mqttClient.subscribe(intentTopic + "/#", 1);
    }

    protected void disconnectFromMQTT() throws MqttException {
	getStatus(HandlerReadyStatus.class).update(REACHABLE, false);
	IMqttToken token = this.mqttClient.disconnect();
	token.waitForCompletion();
    }

    @Override
    public void connectionLost(Throwable thrwbl) {

    }

    //HIER checken ob ein Intent mit einem (neuen) Tenent-Indikator reingekommen ist...
    //Ein tenent is in < > Brackets gehalten.
    //TODO: This is a boiler plate. Use a good REGEX for that.
    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
	if (string.startsWith(intentTopic)) {
	    String substring = string.substring(string.indexOf("intent/") + 7);
	    Map<Class, AnIntent> intents;
	    if (substring.startsWith("<")) {
		String tenant = substring.substring(1, substring.indexOf(">"));
		intents = intentsMap.get(tenant);
		if (intents == null) {
		    try {
			intents = createIntentMap();
		    } catch (Throwable ex) {
			if (ex instanceof Exception) {
			    throw (Exception) ex;
			} else {
			    Logger.getLogger(AHandler.class.getName()).log(Level.SEVERE, null, ex);
			}
		    }
		    intentsMap.put(tenant, intents);
		}

		for (AnIntent intent : intents.values()) {
		    intent.processMessage(string, mm);
		}
	    }
	}
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {

    }

    protected String getDeviceBaseTopic() {
	return this.deviceNameTopic;
    }

    private Map<Class, AnIntent> createIntentMap() {
	Map<Class, AnIntent> intentMap = new HashMap<>();
	for (Class intentClass : intentSet) {
	    try {
		intentMap.put(intentClass, (AnIntent) intentClass.getConstructor(AHandler.class, String.class
		      ).newInstance(this, intentTopic));
	    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
		Logger.getLogger(AHandler.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	return intentMap;
    }

    public void addIntentClass(Class... classes) {
	for (Class intentClass : classes) {
	    intentSet.add(intentClass);

	    try {
		for (Map<Class, AnIntent> intentEntry : intentsMap.values()) {
		    if (!intentEntry.containsKey(intentClass)) {
			intentEntry.put(intentClass, (AnIntent) intentClass.getConstructor(AHandler.class, String.class
				).newInstance(this, intentTopic));
		    }
		}

	    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
		Logger.getLogger(AHandler.class
			.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	if (mqttClient != null && mqttClient.isConnected()) {
	    for (Class intentClass : intentSet) {
		try {
		    AnIntent intent = (AnIntent) intentClass.getConstructor(AHandler.class, String.class).newInstance(this, intentTopic);
		    intent.publishDescriptions(mqttClient);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
		    Logger.getLogger(AHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}
    }

    public void addEventClass(Class... classes) {
	for (Class eventClass : classes) {
	    try {
		eventMap.put(eventClass, (AnEvent) eventClass.getConstructor(AHandler.class, String.class, MqttAsyncClient.class
		     ).newInstance(this, eventTopic, mqttClient));
	    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
		Logger.getLogger(AHandler.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	if (mqttClient != null && mqttClient.isConnected()) {
	    for (AnEvent event : eventMap.values()) {
		event.publishDescriptions();
	    }
	}

    }

    public void addStatusClass(Class... classes) {
	for (Class statusClass : classes) {
	    try {
		statusMap.put(statusClass, (AStatus) statusClass.getConstructor(AHandler.class, String.class, MqttAsyncClient.class
		      ).newInstance(this, statusTopic, mqttClient));
	    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
		Logger.getLogger(AHandler.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	if (mqttClient != null && mqttClient.isConnected()) {
	    for (AStatus status : statusMap.values()) {
		status.publishDescriptions();
	    }
	    getStatus(HandlerReadyStatus.class).update(REACHABLE, true);
	}
    }

    public abstract String getApplicationName();

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 59 * hash + Objects.hashCode(this.identityString);
	hash = 59 * hash + Objects.hashCode(this.mqttURI);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final AHandler other = (AHandler) obj;
	if (!Objects.equals(this.identityString, other.identityString)) {
	    return false;
	}
	if (!Objects.equals(this.mqttURI, other.mqttURI)) {
	    return false;
	}
	return true;
    }

}
