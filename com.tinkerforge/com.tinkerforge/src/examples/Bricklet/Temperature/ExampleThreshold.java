package examples.Bricklet.Temperature;
import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.IPConnection;

public class ExampleThreshold {
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

		// Get threshold callbacks with a debounce time of 10 seconds (10000ms)
		temp.setDebouncePeriod(10000);

		// Configure threshold for "greater than 30 °C" (unit is °C/100)
		temp.setTemperatureCallbackThreshold('>', (short)(30*100), (short)0);

		// Add and implement temperature reached listener 
		// (called if temperature is greater than 30 °C)
		temp.addTemperatureReachedListener(new BrickletTemperature.TemperatureReachedListener() {
			public void temperatureReached(short temperature) {
				System.out.println("We have " + temperature/100.0 + " °C.");
				System.out.println("It is too hot, we need air conditioning!");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
