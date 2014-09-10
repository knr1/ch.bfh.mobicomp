package examples.Bricklet.AnalogOut;
import com.tinkerforge.BrickletAnalogOut;
import com.tinkerforge.IPConnection;

public class ExampleSimple {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletAnalogOut ao = new BrickletAnalogOut(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set output voltage to 3.3V
		ao.setVoltage(3300);

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
