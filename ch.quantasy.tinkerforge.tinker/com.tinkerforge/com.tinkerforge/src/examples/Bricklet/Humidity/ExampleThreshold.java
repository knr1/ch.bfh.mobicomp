package examples.Bricklet.Humidity;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.IPConnection;

public class ExampleThreshold {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID

	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletHumidity hum = new BrickletHumidity(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Get threshold callbacks with a debounce time of 10 seconds (10000ms)
		hum.setDebouncePeriod(10000);

		// Configure threshold for "outside of 30 to 60 %RH" (unit is %RH/10)
		hum.setHumidityCallbackThreshold('o', (short)(30*10), (short)(60*10));

		// Add and implement humidity reached listener 
		// (called if humidity is outside of 30 to 60 %RH)
		hum.addHumidityReachedListener(new BrickletHumidity.HumidityReachedListener() {
			public void humidityReached(int humidity) {
				if(humidity < 30*10) {
					System.out.println("Humidity too low: " + humidity/10.0 + " %RH");
				} 
				if(humidity > 60*10) {
					System.out.println("Humidity too high: " + humidity/10.0 + " %RH");
				}

				System.out.println("Recommended humiditiy for human comfort is 30 to 60 %RH.");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
