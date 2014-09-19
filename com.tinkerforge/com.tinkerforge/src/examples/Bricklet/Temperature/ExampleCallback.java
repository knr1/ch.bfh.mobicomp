package examples.Bricklet.Temperature;
import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletTemperature temp = new BrickletTemperature(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set Period for temperature callback to 1s (1000ms)
		// Note: The temperature callback is only called every second if the 
		//       temperature has changed since the last call!
		temp.setTemperatureCallbackPeriod(1000);

		// Add and implement temperature listener (called if temperature changes)
		temp.addTemperatureListener(new BrickletTemperature.TemperatureListener() {
			public void temperature(short temperature) {
				System.out.println("Temperature: " + temperature/100.0 + " °C");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
