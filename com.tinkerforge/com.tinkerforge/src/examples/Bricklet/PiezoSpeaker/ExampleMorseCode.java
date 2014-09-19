package examples.Bricklet.PiezoSpeaker;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.IPConnection;

public class ExampleMorseCode {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletPiezoSpeaker ps = new BrickletPiezoSpeaker(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Morse SOS with a frequency of 2kHz
		ps.morseCode("... --- ...", 2000);

		System.console().readLine("Press key to exit\n");
	}
}
