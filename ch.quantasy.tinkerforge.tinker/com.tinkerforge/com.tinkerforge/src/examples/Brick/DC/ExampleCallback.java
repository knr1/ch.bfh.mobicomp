package examples.Brick.DC;
import com.tinkerforge.BrickDC;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

public class ExampleCallback {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "aetiNB3mX2u"; // Change to your UID

	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		// Note: Declare servo final, so the listener can access it
		final BrickDC dc = new BrickDC(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Add and implement velocity reached listener 
		// (called if velocity set by setVelocity is reached)
		dc.addVelocityReachedListener(new BrickDC.VelocityReachedListener() {
			public void velocityReached(short velocity) {
				if(velocity == 32767) {
					System.out.println("Velocity: Full Speed forward, turning backward");
					try {
						dc.setVelocity((short)-32767);
					} catch(TimeoutException e) {
					} catch(NotConnectedException e) {
					}
				} else if(velocity == -32767) {
					System.out.println("Velocity: Full Speed backward, turning forward");
					try {
						dc.setVelocity((short)32767);
					} catch(TimeoutException e) {
					} catch(NotConnectedException e) {
					}
				} else {
					System.out.println("Error"); // Can only happen if another program sets velocity
				}
			}
		});

		dc.enable();
		// The acceleration has to be smaller or equal to the maximum acceleration
		// of the dc motor, otherwise the velocity reached listener will be called too early
		dc.setAcceleration(5000); // Slow acceleration
		dc.setVelocity((short)32767); // Full speed forward

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
