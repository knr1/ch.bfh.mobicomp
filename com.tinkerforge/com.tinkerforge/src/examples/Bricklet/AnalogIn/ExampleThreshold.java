package examples.Bricklet.AnalogIn;
import com.tinkerforge.BrickletAnalogIn;
import com.tinkerforge.IPConnection;

public class ExampleThreshold {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletAnalogIn ai = new BrickletAnalogIn(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get threshold callbacks with a debounce time of 10 seconds (10000ms)
		ai.setDebouncePeriod(10000);

		// Configure threshold for "smaller than 5V" (unit is mV)
		ai.setVoltageCallbackThreshold('<', (short)(5*1000), (short)0);

		// Add and implement voltage reached listener 
		// (called if voltage is smaller than 5V)
		ai.addVoltageReachedListener(new BrickletAnalogIn.VoltageReachedListener() {
			public void voltageReached(int voltage) {
				System.out.println("Voltage dropped below 5V: " + voltage/1000.0);
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
