package ch.bfh.smoje.tinkerforge.imagecapture;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.net.InetAddress;
import java.net.URI;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ImageCaptureManager {

    public final String MQTT_BROKER = "tcp://smoje.ch:1883";
    public final String MQTT_TOPIC = "selfie/klatsch";
    public final String MQTT_CLIENT_NAME = "SelfieSmojeTrigger.";

    public final String MQTT_SOUND_INTENSITY_TOPIC = "selfie/trigger/sound/intensity";
    public final String MQTT_SOUND_DEBOUNCE_TOPIC = "selfie/trigger/sound/debouncePeriod";

    private TinkerforgeStackAddress identifier;
    private TinkerforgeStackAgent agent1;
    private SoundIntensityApplication soundIntensityApplication;
    private PlaySoundApplication playSoundApplication;
    private TiltApplication tiltApplication;

    private MqttClient mqttClient;

    private void manageMQTT() {
	while (true) {
	    try {
		String hostname = InetAddress.getLocalHost().getHostName();
		mqttClient = new MqttClient(URI.create(MQTT_BROKER).toString(), MQTT_CLIENT_NAME + hostname, new MemoryPersistence());
		connectClient();
		mqttClient.subscribe(new String[]{MQTT_SOUND_DEBOUNCE_TOPIC, MQTT_SOUND_INTENSITY_TOPIC}, new int[]{1, 1});
		mqttClient.setCallback(new MqttCallback() {

		    @Override
		    public void connectionLost(Throwable thrwbl) {
			Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, thrwbl);
		    }

		    @Override
		    public void messageArrived(String string, MqttMessage mm) throws Exception {
			if (mm == null || mm.getPayload() == null) {
			    return;
			}
			String message = new String(mm.getPayload());
			try {
			    int value = Integer.parseInt(message);
			    if (string.equals(MQTT_SOUND_INTENSITY_TOPIC)) {
				soundIntensityApplication.setSoundIntensityThreshold(value);
			    }
			    if (string.equals(MQTT_SOUND_DEBOUNCE_TOPIC)) {
				soundIntensityApplication.setDebouncePeriod(value);
			    }
			} catch (Exception ex) {
			    Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, ex);
			}

		    }

		    @Override
		    public void deliveryComplete(IMqttDeliveryToken imdt) {
			//Do not care
		    }
		});
		Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.INFO, "MQTT-Established");

		break;
	    } catch (Exception ex) {
		Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    try {
		Thread.sleep(10000);
	    } catch (InterruptedException ex) {
		Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    private void manageTinkerforge() {
	identifier = new TinkerforgeStackAddress(
		"localhost");
	agent1 = TinkerforgeStackAgency
		.getInstance().getStackAgent(identifier);

	soundIntensityApplication = new SoundIntensityApplication(this);
	agent1.addApplication(soundIntensityApplication);

	playSoundApplication = new PlaySoundApplication(this);
	agent1.addApplication(playSoundApplication);

	tiltApplication = new TiltApplication(this);
	agent1.addApplication(tiltApplication);

	Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.INFO, "Tinkerforge-Established");

    }

    private void connectClient() {
	while (true) {
	    try {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(true);
		mqttClient.connect(options);
		Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.INFO, "Connected as: " + mqttClient.getClientId());
		break;
	    } catch (MqttException ex) {
		Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, ex);
		connectClient();
	    }
	    try {
		Thread.sleep(10000);
	    } catch (InterruptedException ex) {
		Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    public ImageCaptureManager() {
	manageMQTT();
	manageTinkerforge();

	while (true) {
	    {
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setPayload(("2500").getBytes());
		mqttMessage.setQos(1);
		mqttMessage.setRetained(true);
		try {
		    mqttClient.publish(MQTT_SOUND_INTENSITY_TOPIC, mqttMessage);
		    Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.INFO, "sent");
		    break;

		} catch (MqttException ex) {
		    Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, ex);
		    connectClient();
		}
	    }
	    try {
		Thread.sleep(10000);
	    } catch (InterruptedException ex) {
		Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	while (true) {
	    {
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setPayload(("50").getBytes());
		mqttMessage.setQos(1);
		mqttMessage.setRetained(true);
		try {
		    mqttClient.publish(MQTT_SOUND_DEBOUNCE_TOPIC, mqttMessage);
		    Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.INFO, "sent");
		    break;

		} catch (MqttException ex) {
		    Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, ex);
		    connectClient();
		}
	    }
	    try {
		Thread.sleep(10000);
	    } catch (InterruptedException ex) {
		Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

    }

    public void finish() {
	try {
	    mqttClient.unsubscribe(new String[]{MQTT_SOUND_DEBOUNCE_TOPIC, MQTT_SOUND_INTENSITY_TOPIC});
	    mqttClient.disconnect();

	} catch (MqttException ex) {
	    Logger.getLogger(ImageCaptureManager.class
		    .getName()).log(Level.SEVERE, null, ex);
	}
    }

    public void captureImage() {
	playSoundApplication.alert();
	//.toJSON
	String message = "{\"action\"=\"Click\";\"timestamp\"=\"" + System.currentTimeMillis() + "\"}";
	Logger
		.getLogger(ImageCaptureManager.class
			.getName()).log(Level.INFO, message);

	MqttMessage mqttMessage = new MqttMessage();

	mqttMessage.setPayload(message.getBytes());
	mqttMessage.setQos(
		1);
	mqttMessage.setRetained(
		true);

	try {
	    mqttClient.publish(MQTT_TOPIC, mqttMessage);
	    Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.INFO, "sent");

	} catch (MqttException ex) {
	    Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public static void main(final String[] args) throws Exception {
	Handler handler = new FileHandler("SensorApp.log", 1000000, 2);
	Logger.getLogger("").addHandler(handler);

	ImageCaptureManager manager = new ImageCaptureManager();
	manager.captureImage();
	System.out.println("Hit a key to terminate program");
	System.in.read();
	manager.finish();
	System.out.println("Program terminated.");
	System.exit(0);
    }

}
