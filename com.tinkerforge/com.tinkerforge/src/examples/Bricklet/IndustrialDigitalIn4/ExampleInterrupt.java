package examples.Bricklet.IndustrialDigitalIn4;
import com.tinkerforge.BrickletIndustrialDigitalIn4;
import com.tinkerforge.IPConnection;

public class ExampleInterrupt {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "xyz"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletIndustrialDigitalIn4 idi4 =
		  new BrickletIndustrialDigitalIn4(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Add and implement listener for interrupt (called if pin 0 changes)
		idi4.addInterruptListener(new BrickletIndustrialDigitalIn4.InterruptListener() {
			public void interrupt(int interruptMask, int valueMask) {
				System.out.println("Interrupt by: " + Integer.toBinaryString(interruptMask));
				System.out.println("Value: " + Integer.toBinaryString(valueMask));
			}
		});

		// Enable interrupt on pin 0
		idi4.setInterrupt(1 << 0);

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
