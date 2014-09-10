package examples.Bricklet.IndustrialDigitalIn4;
import com.tinkerforge.BrickletIndustrialDigitalIn4;
import com.tinkerforge.IPConnection;

public class ExampleSimple {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "xyz"; // Change to your UID

	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletIndustrialDigitalIn4 idi4 =
		  new BrickletIndustrialDigitalIn4(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Read out values as bitmask
		int value = idi4.getValue();
		System.out.println("value: " + value);

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
