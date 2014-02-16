package examples.Bricklet.GPS;
import com.tinkerforge.BrickletGPS;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID

	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the commnents below
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletGPS gps = new BrickletGPS(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set Period for coordinates callback to 1s (1000ms)
		// Note: The current callback is only called every second if the 
		//       coordinates have changed since the last call!
		gps.setCoordinatesCallbackPeriod(1000);

		// Add and implement coordinates listener (called if coordinates change)
		gps.addCoordinatesListener(new BrickletGPS.CoordinatesListener() {
			public void coordinates(long latitude, char ns, long longitude, char ew,
			                        int pdop, int hdop, int vdop, int epe) {
				System.out.println("Latitude: " + latitude/1000000.0 + "° " + ns);
				System.out.println("Longitude: " + longitude/1000000.0 + "° " + ew);
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
