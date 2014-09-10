package examples.Bricklet.VoltageCurrent;
import com.tinkerforge.BrickletVoltageCurrent;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "ABC"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the commnents below
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletVoltageCurrent vc = new BrickletVoltageCurrent(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected


		// Set Period for current callback to 1s (1000ms)
		// Note: The current callback is only called every second if the 
		//       current has changed since the last call!
		vc.setCurrentCallbackPeriod(1000);

		// Add and implement current listener (called if current changes)
		vc.addCurrentListener(new BrickletVoltageCurrent.CurrentListener() {
			public void current(int current) {
				System.out.println("Current: " + current/1000.0 + " A");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
