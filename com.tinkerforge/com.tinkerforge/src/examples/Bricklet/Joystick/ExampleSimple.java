package examples.Bricklet.Joystick;
import com.tinkerforge.BrickletJoystick;
import com.tinkerforge.BrickletJoystick.Position;
import com.tinkerforge.IPConnection;

public class ExampleSimple {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletJoystick joy = new BrickletJoystick(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get current position (returned as Position object with members x, y)
		Position position = joy.getPosition(); // Can throw com.tinkerforge.TimeoutException
		System.out.println("Position: " + position);

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
