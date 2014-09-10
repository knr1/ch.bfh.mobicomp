package examples.Bricklet.Current25;
import com.tinkerforge.BrickletCurrent25;
import com.tinkerforge.IPConnection;

public class ExampleSimple {
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

		// Get current current (unit is mA)
		short current = c25.getCurrent(); // Can throw com.tinkerforge.TimeoutException

		System.out.println("Current: " + current/1000.0 + " A");

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
