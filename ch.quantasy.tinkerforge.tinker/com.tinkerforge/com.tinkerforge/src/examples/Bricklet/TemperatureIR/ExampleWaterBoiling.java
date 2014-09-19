package examples.Bricklet.TemperatureIR;
import com.tinkerforge.BrickletTemperatureIR;
import com.tinkerforge.IPConnection;

public class ExampleWaterBoiling {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletTemperatureIR tir = new BrickletTemperatureIR(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set emissivity to 0.98 (emissivity of water)
		tir.setEmissivity((int)(0xFFFF*0.98));

		// Get threshold callbacks with a debounce time of 10 seconds (10000ms)
		tir.setDebouncePeriod(10000);

		// Configure threshold for "object temperature greater than 100 °C" (unit is °C/10)
		tir.setObjectTemperatureCallbackThreshold('>', (short)(100*10), (short)0);

		// Add and implement temperature reached listener 
		// (called if object temperature is greater than 100 °C)
		tir.addObjectTemperatureReachedListener(new BrickletTemperatureIR.ObjectTemperatureReachedListener() {
			public void objectTemperatureReached(short temperature) {
				System.out.println("The surface has a temperature of " + 
				                   temperature/10.0 + " °C.");
				System.out.println("The water is boiling!");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
