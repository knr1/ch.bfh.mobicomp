package ch.quantasy.mqttfirst.barometer;

import ch.quantasy.mqttfirst.mqtt.MqttBuffer;
import ch.quantasy.mqttfirst.mqtt.MqttClientBuilder;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.BrickletBarometer.AltitudeListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class BarometerApplication extends AbstractTinkerforgeApplication implements AltitudeListener {

    public static final String TOPIC = "ch.quantasy.tinkerforge/barometer/";
    public static final String BROKER = "localhost";  //broker

    private static MqttClientBuilder builder = new MqttClientBuilder();
    private MqttBuffer buffer;
    private MqttClient sender;

    private BrickletBarometer barometer;

    private final long updatePeriodeInMilliseconds = 1;

    public BarometerApplication() throws MqttException {

    }

    @Override
    public void deviceConnected(
	    final TinkerforgeStackAgent tinkerforgeStackAgent,
	    final Device device) {
	if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Barometer) {
	    if (this.barometer == null) {
		try {
		    this.barometer = (BrickletBarometer) device;

		    sender = builder
			    .uri("tcp://" + BROKER + ":1883")
			    .clientUID(device.toString())
			    .build();

		    sender.connect();

		    buffer = new MqttBuffer().client(sender).topic(TOPIC + device.toString()).qos(0).retained(true);
		    System.out.printf("Connected to: (%s).", TOPIC + device.toString());
		    this.barometer
			    .addAltitudeListener(this);
		    // Turn averaging of in the Barometer Bricklet to make sure that
		    // the data is without delay
		    try {
			try {
			    this.barometer
				    .setAveraging((short) 0, (short) 0, (short) 0);
			    this.barometer
				    .setAltitudeCallbackPeriod(this.updatePeriodeInMilliseconds);
			} catch (com.tinkerforge.TimeoutException ex) {
			    Logger.getLogger(BarometerApplication.class.getName()).log(Level.SEVERE, null, ex);
			}
		    } catch (final NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }

		} catch (MqttException ex) {
		    Logger.getLogger(BarometerApplication.class.getName()).log(Level.SEVERE, null, ex);
		}

	    }
	}

    }

    @Override
    public void deviceDisconnected(
	    final TinkerforgeStackAgent tinkerforgeStackAgent,
	    final Device device) {
	if (TinkerforgeDevice.areEqual(this.barometer, device)) {
	    this.barometer.removeAltitudeListener(this);
	    try {
		try {
		    this.barometer.setAltitudeCallbackPeriod(0);
		    this.sender.disconnect();
		} catch (com.tinkerforge.TimeoutException ex) {
		    Logger.getLogger(BarometerApplication.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MqttException ex) {
		    Logger.getLogger(BarometerApplication.class.getName()).log(Level.SEVERE, null, ex);
		}
	    } catch (final NotConnectedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    this.barometer = null;

	}
    }

    @Override
    public void altitude(int altitude) {

	//buffer.sendAsync(ByteBuffer.allocate(4).putInt(altitude).array());
	buffer.sendAsync(Integer.toString(altitude).getBytes());
    }

    @Override
    public boolean equals(final Object obj
    ) {
	return this == obj;
    }

    @Override
    public int hashCode() {
	// TODO Auto-generated method stub
	return 0;
    }

}
