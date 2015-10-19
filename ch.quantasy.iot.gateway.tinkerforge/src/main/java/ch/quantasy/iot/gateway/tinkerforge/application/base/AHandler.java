/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.base;

import ch.quantasy.iot.gateway.tinkerforge.TFMQTTGateway;
import ch.quantasy.iot.gateway.tinkerforge.application.base.message.AStatus;
import ch.quantasy.iot.gateway.tinkerforge.application.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.application.base.message.AnIntent;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.ADeviceHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.status.DeviceHandlerReadyStatus;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class AHandler implements MqttCallback {

    public static final String DEVICE_DESCRIPTION_TOPIC = TFMQTTGateway.TOPIC + "/Description";

    private final String identityString;
    private final Map<String, Set<AnIntent>> intentsMap;
    private final Map<Class, AnEvent> eventMap;
    private final Map<Class, AStatus> statusMap;
    private final String statusTopic;
    private final String eventTopic;
    private final String intentTopic;
    private final MqttAsyncClient mqttClient;
    private final String deviceNameTopic;

    public AHandler(URI mqttURI, String deviceNameRootTopic, String identityString) throws Throwable {

	this.identityString = identityString;
	this.deviceNameTopic = deviceNameRootTopic + "/" + getApplicationName() + "/" + identityString;;
	this.intentTopic = getDeviceBaseTopic() + "/intent";
	this.eventTopic = getDeviceBaseTopic() + "/event";
	this.statusTopic = getDeviceBaseTopic() + "/status";
	this.mqttClient = new MqttAsyncClient(mqttURI.toString(), identityString);
	intentsMap = new HashMap<>();
	Set<AnIntent> intents = createIntentSet();
	intentsMap.put("", intents);
	eventMap = createEventMap();
	statusMap = createStatusMap();
	connectToMQTT();

	for (AnIntent intent : intents) {
	    intent.publishTopicDefinition(mqttClient);
	}

	for (AnEvent event : eventMap.values()) {
	    event.publishTopicDefinition();
	}

	for (AStatus status : statusMap.values()) {
	    status.publishTopicDefinition();
	}
	publishStatus();

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

    private Map<Class, AnEvent> createEventMap() throws Throwable {
	Map<Class, AnEvent> eventMap = new HashMap<>();

	for (Class eventClass : getEventClasses()) {
	    eventMap.put(eventClass, (AnEvent) eventClass.getConstructor(ADeviceHandler.class, String.class, MqttAsyncClient.class
		 ).newInstance(this, eventTopic, mqttClient));
	}
	return eventMap;
    }

    private Map<Class, AStatus> createStatusMap() throws Throwable {
	Map<Class, AStatus> statusMap = new HashMap<>();

	for (Class statusClass : getStatusClasses()) {
	    statusMap.put(statusClass, (AStatus) statusClass.getConstructor(ADeviceHandler.class, String.class, MqttAsyncClient.class).newInstance(this, statusTopic, mqttClient));
	}
	return statusMap;
    }

    private Set<AnIntent> createIntentSet() throws Throwable {
	Set<AnIntent> intents = new HashSet<>();
	for (Class intentClass : getIntentClasses()) {
	    intents.add((AnIntent) intentClass.getConstructor(ADeviceHandler.class, String.class).newInstance(this, intentTopic));
	}
	return intents;
    }

    public void publishStatus() {
	DeviceHandlerReadyStatus readyStatus = getStatus(DeviceHandlerReadyStatus.class);
	readyStatus.updateEnabled(true);
	readyStatus.updateReachable(true);
    }

    private void connectToMQTT() throws MqttException {
	MqttConnectOptions connectOptions = new MqttConnectOptions();
	connectOptions.setCleanSession(false);
	DeviceHandlerReadyStatus readyStatus = getStatus(DeviceHandlerReadyStatus.class);
	MqttMessage message = readyStatus.toJSONMQTTMessage(false);
	connectOptions.setWill(readyStatus.getStatusTopic() + "/reachable", message.getPayload(), 1, true);
	this.mqttClient.setCallback(this);
	IMqttToken token = this.mqttClient.connect(connectOptions);
	token.waitForCompletion();
	this.mqttClient.subscribe(intentTopic + "/#", 1);
    }

    public void disconnectFromMQTT() throws MqttException {
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
	    Set<AnIntent> intents;
	    if (substring.startsWith("<")) {
		String tenant = substring.substring(0, substring.indexOf(">"));
		intents = intentsMap.get(tenant);
		if (intents == null) {
		    try {
			intents = createIntentSet();
		    } catch (Throwable ex) {
			if (ex instanceof Exception) {
			    throw (Exception) ex;
			} else {
			    Logger.getLogger(ADeviceHandler.class.getName()).log(Level.SEVERE, null, ex);
			}
		    }
		    intentsMap.put(tenant, intents);
		}
	    } else {
		intents = intentsMap.get("");
	    }
	    for (AnIntent intent : intents) {
		intent.processMessage(string, mm);
	    }
	}
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {

    }

    protected String getDeviceBaseTopic() {
	return this.deviceNameTopic;
    }

    public abstract String getApplicationName();

    protected abstract Class[] getIntentClasses();

    protected abstract Class[] getEventClasses();

    protected abstract Class[] getStatusClasses();

    /**
     * This method allows to describe the strategy of the DeviceHandler for any incoming intent.
     *
     * @param intent
     */
    public abstract void executeIntent(AnIntent intent) throws Throwable;

}
