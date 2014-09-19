package examples.Bricklet.IndustrialDigitalOut4;
import com.tinkerforge.BrickletIndustrialDigitalOut4;
import com.tinkerforge.IPConnection;

public class ExampleSimple {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "xyz"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletIndustrialDigitalOut4 ido4 =
		  new BrickletIndustrialDigitalOut4(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Turn pins alternating high/low for 10 times with 100 ms delay 
		for(int i = 0; i < 10; i++) {
			Thread.sleep(100);
			ido4.setValue(1 << 0);
			Thread.sleep(100);
			ido4.setValue(1 << 1);
			Thread.sleep(100);
			ido4.setValue(1 << 2);
			Thread.sleep(100);
			ido4.setValue(1 << 3);
		}

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
