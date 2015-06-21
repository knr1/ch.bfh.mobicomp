package ch.bfh.smoje.tinkerforge.imagecapture;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ImageCaptureManager {

    public final String MQTT_BROKER = "tcp://smoje.ch:1883";
    public final String MQTT_TOPIC = "selfie/klatsch";
    public final String MQTT_CLIENT_NAME = "SelfieSmojeCamTrigger";

    public final String MQTT_SOUND_INTENSITY_TOPIC = "selfie/trigger/sound/intensity";
    public final String MQTT_SOUND_DEBOUNCE_TOPIC = "selfie/trigger/sound/debouncePeriod";

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

	mqttClient.setCallback(new MqttCallback() {

	    @Override
	    public void connectionLost(Throwable thrwbl) {
		//Do not care
	    }

	    @Override
	    public void messageArrived(String string, MqttMessage mm) throws Exception {
		String message = new String(mm.getPayload());
		try {
		    int value = Integer.parseInt(message);
		    if (string.equals(MQTT_SOUND_INTENSITY_TOPIC)) {
			soundIntensityApplication.setSoundIntensityThreshold(value);
		    }
		    if (string.equals(MQTT_SOUND_DEBOUNCE_TOPIC)) {
			soundIntensityApplication.setDebouncePeriod(value);
		    }
		} catch (NumberFormatException ex) {
		    Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, ex);
		}

	    }

	    @Override
	    public void deliveryComplete(IMqttDeliveryToken imdt) {
		//Do not care
	    }
	});
	mqttClient.subscribe(new String[]{MQTT_SOUND_DEBOUNCE_TOPIC, MQTT_SOUND_INTENSITY_TOPIC}, new int[]{1, 1});
    }

    public void finish() {
	//try {
	//   mqttClient.unsubscribe(new String[]{MQTT_SOUND_DEBOUNCE_TOPIC, MQTT_SOUND_INTENSITY_TOPIC});
	//   mqttClient.disconnect();
	//} catch (MqttException ex) {
	//    Logger.getLogger(ImageCaptureManager.class.getName()).log(Level.SEVERE, null, ex);
	//}
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
	manager.finish();
	System.out.println("Program terminated.");
	System.exit(0);
    }

}
