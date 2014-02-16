package examples.Bricklet.DistanceIR;
import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletDistanceIR dir = new BrickletDistanceIR(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Set Period for distance callback to 1s (1000ms)
		// Note: The distance callback is only called every second if the 
		//       distance has changed since the last call!
		dir.setDistanceCallbackPeriod(1000);

		// Add and implement distance listener (called if distance changes)
		dir.addDistanceListener(new BrickletDistanceIR.DistanceListener() {
			public void distance(int distance) {
				System.out.println("Distance: " + distance/10.0 + " cm");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
