package examples.Bricklet.AmbientLight;
import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.IPConnection;

public class ExampleThreshold {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletAmbientLight al = new BrickletAmbientLight(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get threshold callbacks with a debounce time of 10 seconds (10000ms)
		al.setDebouncePeriod(10000);

		// Configure threshold for "greater than 200 Lux" (unit is Lux/10)
		al.setIlluminanceCallbackThreshold('>', (short)(200*10), (short)0);

		// Add and implement illuminance reached listener 
		// (called if illuminance is greater than 200 lux)
		al.addIlluminanceReachedListener(new BrickletAmbientLight.IlluminanceReachedListener() {
			public void illuminanceReached(int illuminance) {
				System.out.println("We have: " + illuminance/10.0 + " Lux.");
				System.out.println("Too bright, close the curtains!");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
