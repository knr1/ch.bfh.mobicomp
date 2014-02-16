package examples.Bricklet.Humidity;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
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

		// Set Period for humidity callback to 1s (1000ms)
		// Note: The humidity callback is only called every second if the 
		//       humidity has changed since the last call!
		hum.setHumidityCallbackPeriod(1000);

		// Add and implement humidity listener (called if humidity changes)
		hum.addHumidityListener(new BrickletHumidity.HumidityListener() {
			public void humidity(int humidity) {
				System.out.println("Relative Humidity: " + humidity/10.0 + " %RH");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
