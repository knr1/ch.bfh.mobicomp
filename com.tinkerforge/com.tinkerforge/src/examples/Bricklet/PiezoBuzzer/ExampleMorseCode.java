package examples.Bricklet.PiezoBuzzer;
import com.tinkerforge.BrickletPiezoBuzzer;
import com.tinkerforge.IPConnection;

public class ExampleMorseCode {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletPiezoBuzzer pb = new BrickletPiezoBuzzer(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Morse SOS
		pb.morseCode("... --- ...");

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
