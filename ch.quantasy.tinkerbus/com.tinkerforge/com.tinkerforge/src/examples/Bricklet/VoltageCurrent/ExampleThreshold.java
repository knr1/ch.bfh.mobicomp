package examples.Bricklet.VoltageCurrent;
import com.tinkerforge.BrickletVoltageCurrent;
import com.tinkerforge.IPConnection;

public class ExampleThreshold {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the commnents below
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletVoltageCurrent vc = new BrickletVoltageCurrent(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected


		// Get threshold callbacks with a debounce time of 10 seconds (10000ms)
		vc.setDebouncePeriod(10000);

		// Configure threshold for "greater than 1A" (unit is mA)
		vc.setCurrentCallbackThreshold('>', 1*1000, 0);

		// Add and implement current reached listener (called if current is greater than 1A)
		vc.addCurrentReachedListener(new BrickletVoltageCurrent.CurrentReachedListener() {
			public void currentReached(int current) {
				System.out.println("Current is greater than 1A: " + current/1000.0);
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
