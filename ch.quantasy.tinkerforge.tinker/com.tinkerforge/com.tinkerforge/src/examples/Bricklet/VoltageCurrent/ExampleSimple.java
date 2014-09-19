package examples.Bricklet.VoltageCurrent;
import com.tinkerforge.BrickletVoltageCurrent;
import com.tinkerforge.IPConnection;

public class ExampleSimple {
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

		// Get current current and voltage (unit is mA and mV)
		int current = vc.getCurrent(); // Can throw com.tinkerforge.TimeoutException
		int voltage = vc.getVoltage(); // Can throw com.tinkerforge.TimeoutException

		System.out.println("Current: " + current/1000.0 + " A");
		System.out.println("Voltgae: " + voltage/1000.0 + " V");

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
