package examples.Bricklet.IO16;
import com.tinkerforge.BrickletIO16;
import com.tinkerforge.IPConnection;

public class ExampleInterrupt {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletIO16 io16 = new BrickletIO16(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Add and implement listener for interrupt (called if pin 2 of port a changes)
		io16.addInterruptListener(new BrickletIO16.InterruptListener() {
			public void interrupt(char port, short interruptMask, short valueMask) {
				System.out.println("Interrupt on port: " + port);
				System.out.println("Interrupt by: " + Integer.toBinaryString(interruptMask));
				System.out.println("Value: " + Integer.toBinaryString(valueMask));
			}
		});

		// Enable interrupt on pin 2 of port a
		io16.setPortInterrupt('a', (short)(1 << 2));

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
