package examples.Bricklet.LCD16x2;
import com.tinkerforge.BrickletLCD16x2;
import com.tinkerforge.IPConnection;

public class ExampleHelloWorld {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletLCD16x2 lcd = new BrickletLCD16x2(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Turn backlight on
		lcd.backlightOn();

		// Write "Hello World"
		lcd.writeLine((short)0, (short)0, "Hello World");

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
