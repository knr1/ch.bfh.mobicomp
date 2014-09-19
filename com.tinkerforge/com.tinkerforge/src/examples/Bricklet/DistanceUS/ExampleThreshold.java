package examples.Bricklet.DistanceUS;
import com.tinkerforge.BrickletDistanceUS;
import com.tinkerforge.IPConnection;

public class ExampleThreshold {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "XYZ"; // Change to your UID

	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletDistanceUS dir = new BrickletDistanceUS(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get threshold callbacks with a debounce time of 10 seconds (10000ms)
		dir.setDebouncePeriod(10000);

		// Configure threshold for "smaller than 200"
		dir.setDistanceCallbackThreshold('<', (short)200, (short)0);

		// Add and implement distance reached listener 
		// (called if distance value is smaller than 200)
		dir.addDistanceReachedListener(new BrickletDistanceUS.DistanceReachedListener() {
			public void distanceReached(int distance) {
				System.out.println("Distance Value is smaller than 200: " + distance);
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
