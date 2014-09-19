package examples.Bricklet.MotionDetector;
import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "XYZ"; // Change to your UID
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickletMotionDetector md = new BrickletMotionDetector(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Add and implement "detected" and "detection cycle ended" listeners 
		md.addMotionDetectedListener(new BrickletMotionDetector.MotionDetectedListener() {
			public void motionDetected() {
				System.out.println("Motion Detected");
			}
		});
		md.addDetectionCycleEndedListener(new BrickletMotionDetector.DetectionCycleEndedListener() {
			public void detectionCycleEnded() {
				System.out.println("Detection Cycle Ended (next detection possible in ~3 seconds)");
			}
		});

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
