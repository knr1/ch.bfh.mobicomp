package examples.Bricklet.Joystick;
import com.tinkerforge.BrickletJoystick;
import com.tinkerforge.IPConnection;

public class ExampleFindBorders {
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

		// Get threshold callbacks with a debounce time of 0.2 seconds (200ms)
		joy.setDebouncePeriod(200);

		// Configure threshold for "x or y value outside of [-99..99]"
		joy.setPositionCallbackThreshold('o', (short)-99, (short)99, (short)-99, (short)99);

		// Add and implement position reached listener
		// (called if x or y value outside of [-99..99])
		joy.addPositionReachedListener(new BrickletJoystick.PositionReachedListener() {
			public void positionReached(short x, short y) {
				if(y == 100) {
					System.out.println("Top");
				} else if(y == -100) {
					System.out.println("Bottom");
				}

				if(x == 100) {
					System.out.println("Right");
				} else if(x == -100) {
					System.out.println("Left");
				}

				System.out.println("");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
