package examples.Bricklet.Line;
import com.tinkerforge.BrickletLine;
import com.tinkerforge.IPConnection;

public class ExampleThreshold {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "XYZ"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletLine line = new BrickletLine(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get threshold callbacks with a debounce time of 1 seconds (1000ms)
		line.setDebouncePeriod(1000);

		// Configure threshold for "greater than 2000"
		line.setReflectivityCallbackThreshold('>', (short)(2000), (short)0);

		// Add and implement reflectivity reached listener 
		// (called if reflectivity is greater than 2000)
		line.addReflectivityReachedListener(new BrickletLine.ReflectivityReachedListener() {
			public void reflectivityReached(int reflectivity) {
				System.out.println("Reflectivity: " + reflectivity);
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
