package examples.Bricklet.Moisture;
import com.tinkerforge.BrickletMoisture;
import com.tinkerforge.IPConnection;

public class ExampleThreshold {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "XYZ"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletMoisture al = new BrickletMoisture(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get threshold callbacks with a debounce time of 1 seconds (1000ms)
		al.setDebouncePeriod(1000);

		// Configure threshold for "greater than 200"
		al.setMoistureCallbackThreshold('>', (short)(200), (short)0);

		// Add and implement moisture reached listener 
		// (called if moisture is greater than 200)
		al.addMoistureReachedListener(new BrickletMoisture.MoistureReachedListener() {
			public void moistureReached(int moisture) {
				System.out.println("Moisture Value " + moisture);
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
