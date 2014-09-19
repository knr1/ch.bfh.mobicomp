package examples.Bricklet.LCD20x4;
import com.tinkerforge.BrickletLCD20x4;
import com.tinkerforge.IPConnection;

public class ExampleButtonCallback {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletLCD20x4 lcd = new BrickletLCD20x4(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Add and implement listener for pressed and released events
		lcd.addButtonPressedListener(new BrickletLCD20x4.ButtonPressedListener() {
			public void buttonPressed(short button) {
				System.out.println("Pressed: " + button);
			}
		});
		lcd.addButtonReleasedListener(new BrickletLCD20x4.ButtonReleasedListener() {
			public void buttonReleased(short button) {
				System.out.println("Released: " + button);
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
