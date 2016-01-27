package ch.quantasy.mqttfirst.view;

import ch.quantasy.mqttfirst.barometer.BarometerApplication;
import ch.quantasy.mqttfirst.mqtt.MqttClientBuilder;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 */
public class GUIApplication extends Application {

    private final MqttClient empf;
    private long count;
    private static MqttClientBuilder builder = new MqttClientBuilder();
    String lastMessage;
    private static List<Stage> stages = new LinkedList<>();

    public GUIApplication() throws MqttException {
	empf = builder.uri("tcp://" + BarometerApplication.BROKER + ":1883").clientUID("ch.quantasy.knr1.GUIApplication").build();
	empf.setCallback(new MqttCallback() {
	    @Override
	    public void connectionLost(Throwable throwable) {
	    }

	    @Override
	    public void messageArrived(String str, MqttMessage mqttMessage)
		    throws Exception {
		byte[] payload = mqttMessage.getPayload();
		ByteBuffer bb = ByteBuffer.wrap(payload);
		AltitudeProfileView.addBarometricAltitudeData(bb.getInt());
	    }

	    @Override
	    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
	    }
	});
	MqttConnectOptions options = new MqttConnectOptions();
	options.setCleanSession(true);
	empf.connect(options);
	empf.subscribe(BarometerApplication.TOPIC + "+", 0);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
	// primaryStage.show();
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		final Stage altitudeStage = new Stage();
		final AltitudeProfileView e = new AltitudeProfileView(
			altitudeStage);
		GUIApplication.stages.add(altitudeStage);
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		altitudeStage.setWidth(screenWidth);

		for (final Stage stage : GUIApplication.stages) {
		    stage.show();
		}
	    }
	});

    }

    public static void finish() {
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		for (final Stage stage : GUIApplication.stages) {
		    stage.close();
		}
	    }
	});
    }

    public static void main(final String[] args) throws Exception {
	javafx.application.Application.launch(args);
	System.out.println("Done");
	GUIApplication.finish();

    }
}
