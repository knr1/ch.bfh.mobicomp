package examples.Bricklet.Tilt;
import com.tinkerforge.BrickletTilt;
import com.tinkerforge.IPConnection;

public class ExampleSimple {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "XYZ"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletTilt tilt = new BrickletTilt(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get current tilt state
		short tiltState = tilt.getTiltState(); // Can throw com.tinkerforge.TimeoutException

		switch(tiltState) {
			case BrickletTilt.TILT_STATE_CLOSED: System.out.println("closed"); break;
			case BrickletTilt.TILT_STATE_OPEN: System.out.println("open"); break;
			case BrickletTilt.TILT_STATE_CLOSED_VIBRATING: System.out.println("closed vibrating"); break;
		}

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
