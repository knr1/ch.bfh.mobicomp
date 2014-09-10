package examples.Bricklet.Line;
import com.tinkerforge.BrickletLine;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "XYZ"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletLine line = new BrickletLine(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set Period for reflectivity callback to 1s (1000ms)
		// Note: The reflectivity callback is only called every second if the 
		//       reflectivity has changed since the last call!
		line.setReflectivityCallbackPeriod(1000);

		// Add and implement reflectivity listener (called if reflectivity changes)
		line.addReflectivityListener(new BrickletLine.ReflectivityListener() {
			public void reflectivity(int reflectivity) {
				System.out.println("Reflectivity: " + reflectivity);
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
