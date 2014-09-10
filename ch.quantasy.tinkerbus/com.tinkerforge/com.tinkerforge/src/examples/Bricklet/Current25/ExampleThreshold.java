package examples.Bricklet.Current25;
import com.tinkerforge.BrickletCurrent25;
import com.tinkerforge.IPConnection;

public class ExampleThreshold {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletCurrent25 c25 = new BrickletCurrent25(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get threshold callbacks with a debounce time of 10 seconds (10000ms)
		c25.setDebouncePeriod(10000);

		// Configure threshold for "greater than 5A" (unit is mA)
		c25.setCurrentCallbackThreshold('>', (short)(5*1000), (short)0);

		// Add and implement current reached listener (called if current is greater than 5A)
		c25.addCurrentReachedListener(new BrickletCurrent25.CurrentReachedListener() {
			public void currentReached(short current) {
				System.out.println("Current is greater than 5A: " + current/1000.0);
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
