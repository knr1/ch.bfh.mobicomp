package examples.Bricklet.DistanceIR;
import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.IPConnection;

public class ExampleThreshold {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletDistanceIR dir = new BrickletDistanceIR(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get threshold callbacks with a debounce time of 10 seconds (10000ms)
		dir.setDebouncePeriod(10000);

		// Configure threshold for "smaller than 20 cm" (unit is mm)
		dir.setDistanceCallbackThreshold('<', (short)200, (short)0);

		// Add and implement distance reached listener 
		// (called if distance is smaller than 20 cm)
		dir.addDistanceReachedListener(new BrickletDistanceIR.DistanceReachedListener() {
			public void distanceReached(int distance) {
				System.out.println("Distance is smaller than 20cm: " + distance/10.0 + " cm");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
