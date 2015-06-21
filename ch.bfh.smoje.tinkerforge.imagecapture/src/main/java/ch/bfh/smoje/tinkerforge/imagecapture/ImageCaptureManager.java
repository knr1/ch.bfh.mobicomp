package ch.bfh.smoje.tinkerforge.imagecapture;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ImageCaptureManager {

    public final String MQTT_BROKER = "tcp://smoje.ch:1883";
    public final String MQTT_TOPIC = "selfie/klatsch";
    public final String MQTT_CLIENT_NAME = "SelfieSmojeCamTrigger";

    private final TinkerforgeStackAddress identifier;
    private final TinkerforgeStackAgent agent1;
    private final SoundIntensityApplication soundIntensityApplication;
    private final PlaySoundApplication playSoundApplication;
    private final TiltApplication tiltApplication;

    private MqttClient mqttClient;

    public ImageCaptureManager() throws MqttException {
	mqttClient = new MqttClient(MQTT_BROKER, MQTT_CLIENT_NAME);
	mqttClient.connect();

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
    }

    public void captureImage() {
	playSoundApplication.alert();
	//.toJSON
	String message = "{\"action\"=\"Click\";\"timestamp\"=\"" + System.currentTimeMillis() + "\"}";
	Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.INFO, message);

	MqttMessage mqttMessage = new MqttMessage();
	mqttMessage.setPayload(message.getBytes());
	mqttMessage.setQos(1);
	mqttMessage.setRetained(false);

	try {
	    mqttClient.publish(MQTT_TOPIC, mqttMessage);
	    Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.INFO, "sent");

	} catch (MqttException ex) {
	    Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public static void main(final String[] args) throws Exception {
	ImageCaptureManager manager = new ImageCaptureManager();
	manager.captureImage();
	System.out.println("Hit a key to terminate program");
	System.in.read();
	System.out.println("Program terminated.");
	System.exit(0);
    }

}
