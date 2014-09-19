package examples.Bricklet.Moisture;
import com.tinkerforge.BrickletMoisture;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "XYZ"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletMoisture al = new BrickletMoisture(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set Period for moisture callback to 1s (1000ms)
		// Note: The moisture callback is only called every second if the 
		//       moisture value has changed since the last call!
		al.setMoistureCallbackPeriod(1000);

		// Add and implement moisture listener (called if moisture value changes)
		al.addMoistureListener(new BrickletMoisture.MoistureListener() {
			public void moisture(int moisture) {
				System.out.println("Moisture Value: " + moisture);
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
