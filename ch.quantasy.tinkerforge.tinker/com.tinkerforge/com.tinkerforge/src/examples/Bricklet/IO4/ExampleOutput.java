package examples.Bricklet.IO4;
import com.tinkerforge.BrickletIO4;
import com.tinkerforge.IPConnection;

public class ExampleOutput {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletIO4 io4 = new BrickletIO4(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set pin 1 output low
		io4.setConfiguration((short)(1 << 1), 'o', false);

		// Set pin 2 and 3 to output high
		io4.setConfiguration((short)((1 << 2) | (1 << 3)), 'o', true);

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
