package examples.Bricklet.Barometer;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.IPConnection;

public class ExampleThreshold {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "bAc"; // Change to your UID

	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletBarometer b = new BrickletBarometer(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get threshold callbacks with a debounce time of 10 seconds (10000ms)
		b.setDebouncePeriod(10000);

		// Configure threshold for "greater than 1025 mbar" (unit is mbar/1000)
		b.setAirPressureCallbackThreshold('>', 1025*1000, 0);

		// Add and implement air pressure reached listener
		// (called if air pressure is greater than 1025 mbar)
		b.addAirPressureReachedListener(new BrickletBarometer.AirPressureReachedListener() {
			public void airPressureReached(int airPressure) {
				System.out.println("We have: " + airPressure/1000.0 + " mbar.");
				System.out.println("Enjoy the potentially good weather!");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
