package examples.Bricklet.Barometer;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "bAc"; // Change to your UID

	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletBarometer b = new BrickletBarometer(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set Period for air pressure and altitude callbacks to 1s (1000ms)
		// Note: The air pressure and altitude callbacks are only called every second
		//       if the air pressure or altitude has changed since the last call!
		b.setAirPressureCallbackPeriod(1000);
		b.setAltitudeCallbackPeriod(1000);

		// Add and implement air pressure listener (called if air pressure changes)
		b.addAirPressureListener(new BrickletBarometer.AirPressureListener() {
			public void airPressure(int airPressure) {
				System.out.println("Air Pressure: " + airPressure/1000.0 + " mbar");
			}
		});

		// Add and implement altitude listener (called if altitude changes)
		b.addAltitudeListener(new BrickletBarometer.AltitudeListener() {
			public void altitude(int altitude) {
				System.out.println("Altitude: " + altitude/100.0 + " m");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
