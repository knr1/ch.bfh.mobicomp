/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base;

import ch.quantasy.iot.gateway.tinkerforge.TFMQTTGateway;
import ch.quantasy.iot.gateway.tinkerforge.application.MQTTTinkerforgeStackHandler;
import ch.quantasy.iot.gateway.tinkerforge.application.deviceHandler.base.status.DeviceHandlerReadyStatus;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
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
public abstract class ADeviceHandler<D extends Device> implements MqttCallback {

    public static final String DEVICE_DESCRIPTION_TOPIC = TFMQTTGateway.TOPIC + "/Description";

    private D device;
    private String deviceName;
    private final String identityString;
    private final TinkerforgeStackAddress stackAddress;

    private final URI mqttURI;
    private final MqttAsyncClient mqttClient;
    private final String mqttClientID;

    private final String deviceBaseTopic;
    private final String statusTopic;
    private final String eventTopic;
    private final String intentTopic;
    private String intentTopicEnabled;
    private String intentTopicBeep;
    private final MQTTTinkerforgeStackHandler stackApplication;

    private final Map<String, Set<AnIntent>> intentsMap;
    private final Map<Class, AnEvent> eventMap;
    private final Map<Class, AStatus> statusMap;

    public ADeviceHandler(MQTTTinkerforgeStackHandler stackApplication, URI mqttURI, TinkerforgeStackAddress stackAddress, String identityString) throws Throwable {
	this.stackApplication = stackApplication;
	this.mqttURI = mqttURI;
	this.stackAddress = stackAddress;
	this.identityString = identityString;
	this.mqttClientID = this.identityString;
	this.deviceBaseTopic = TFMQTTGateway.TOPIC + "/" + stackAddress.hostName + "/" + stackAddress.port + "/" + getApplicationName() + "/" + this.identityString;
	this.intentTopic = deviceBaseTopic + "/intent";
	this.eventTopic = deviceBaseTopic + "/event";
	this.statusTopic = deviceBaseTopic + "/status";
	this.mqttClient = new MqttAsyncClient(mqttURI.toString(), this.mqttClientID);
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

    public <T> T getEvent(Class<T> classOfT) {
	return (T) eventMap.get(classOfT);
    }

    public <T> T getStatus(Class<T> classOfT) {
	return (T) statusMap.get(classOfT);
    }

    public Map<Class, AnEvent> createEventMap() throws Throwable {
	Map<Class, AnEvent> eventMap = new HashMap<>();
	for (Class eventClass : getEventClasses()) {
	    eventMap.put(eventClass, (AnEvent) eventClass.getConstructor(ADeviceHandler.class, String.class, MqttAsyncClient.class).newInstance(this, eventTopic, mqttClient));
	}
	return eventMap;
    }

    public Map<Class, AStatus> createStatusMap() throws Throwable {
	Map<Class, AStatus> statusMap = new HashMap<>();
	statusMap.put(DeviceHandlerReadyStatus.class, new DeviceHandlerReadyStatus(this, statusTopic, mqttClient));

	for (Class statusClass : getStatusClasses()) {
	    statusMap.put(statusClass, (AStatus) statusClass.getConstructor(ADeviceHandler.class, String.class, MqttAsyncClient.class).newInstance(this, statusTopic, mqttClient));
	}
	return statusMap;
    }

    public Set<AnIntent> createIntentSet() throws Throwable {
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

    public String getIdentityString() {
	return identityString;
    }

    public void enableDevice(D device) {
	try {
	    if (!this.identityString.equals(stackApplication.digestIdentityString(device.getIdentity().toString()))) {
		return;
	    }
	} catch (TimeoutException | NotConnectedException ex) {
	    return;
	}
	if (this.device != null) {
	    return;
	}
	this.device = device;
	addDeviceListeners();
    }

    public void disableDevice(Device device) {
	if (!TinkerforgeDevice.areEqual(this.device, device)) {
	    return;
	}
	removeDeviceListeners();
	this.device = null;

    }

    protected abstract Class[] getIntentClasses();

    protected abstract Class[] getEventClasses();

    protected abstract Class[] getStatusClasses();

    protected abstract void addDeviceListeners();

    protected abstract void removeDeviceListeners();

    protected D getDevice() {
	return this.device;
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

    public abstract String getApplicationName();

    /**
     * This method allows to describe the strategy of the DeviceHandler for any incoming intent.
     *
     * @param intent
     */
    protected abstract void executeIntent(AnIntent intent) throws Throwable;
}
