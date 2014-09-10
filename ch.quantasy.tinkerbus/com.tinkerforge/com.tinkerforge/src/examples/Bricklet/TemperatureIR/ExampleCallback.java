package examples.Bricklet.TemperatureIR;
import com.tinkerforge.BrickletTemperatureIR;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID

	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletTemperatureIR tir = new BrickletTemperatureIR(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set Period for temperature callbacks to 1s (1000ms)
		// Note: The temperature callbacks are only called every second if the 
		//       temperature has changed since the last call!
		tir.setObjectTemperatureCallbackPeriod(1000);
		tir.setAmbientTemperatureCallbackPeriod(1000);

		// Add and implement object/ambient temperature listener 
		// (called if temperature changes)
		tir.addObjectTemperatureListener(new BrickletTemperatureIR.ObjectTemperatureListener() {
			public void objectTemperature(short temperature) {
				System.out.println("Object Temperature: " + temperature/10.0 + " °C");
			}
		});
		tir.addAmbientTemperatureListener(new BrickletTemperatureIR.AmbientTemperatureListener() {
			public void ambientTemperature(short temperature) {
				System.out.println("Ambient Temperature: " + temperature/10.0 + " °C");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
