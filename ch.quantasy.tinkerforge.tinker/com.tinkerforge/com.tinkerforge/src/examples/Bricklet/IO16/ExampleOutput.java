package examples.Bricklet.IO16;
import com.tinkerforge.BrickletIO16;
import com.tinkerforge.IPConnection;

public class ExampleOutput {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletIO16 io16 = new BrickletIO16(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set pin 0 on port a to output low
		io16.setPortConfiguration('a', (short)(1 << 0), 'o', false);

		// Set pin 0 and 7 on port b to output high
		io16.setPortConfiguration('b', (short)((1 << 0) | (1 << 7)), 'o', true);

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
