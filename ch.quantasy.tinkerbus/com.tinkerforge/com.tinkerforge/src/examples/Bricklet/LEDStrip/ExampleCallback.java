package examples.Bricklet.LEDStrip;
import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.IPConnection;

public class ExampleCallback {
	private static final String host = "localhost";
	private static final int port = 4223;
	private static final String UID = "XYZ"; // Change to your UID
	private static final int NUM_LEDS = 16;
	private static int rIndex = 0;
	private static short[] r = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private static short[] g = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private static short[] b = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	// Note: To make the examples code cleaner we do not handle exceptions. Exceptions you
	//       might normally want to catch are described in the documentation
	public static void main(String args[]) throws Exception {
		IPConnection ipcon = new IPConnection(); // Create IP connection
		final BrickletLEDStrip ledStrip = new BrickletLEDStrip(UID, ipcon); // Create device object

		ipcon.connect(host, port); // Connect to brickd
		// Don't use device before ipcon is connected

		// Frame rendered callback, is called when a new frame was rendered
		// We increase the index of one blue LED with every frame
		ledStrip.addFrameRenderedListener(new BrickletLEDStrip.FrameRenderedListener() {
			public void frameRendered(int length) {
				b[rIndex] = 0;
				if(rIndex == NUM_LEDS-1) {
					rIndex = 0;
				} else {
					rIndex++;
				}
				b[rIndex] = 255;

				// Set new data for next render cycle
				try {
					ledStrip.setRGBValues(0, (short)NUM_LEDS, r, g, b);
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		});

		// Set frame duration to 50ms (20 frames per second)
		ledStrip.setFrameDuration(50);

		// Set initial rgb values to get started
		ledStrip.setRGBValues(0, (short)NUM_LEDS, r, g, b);

		System.console().readLine("Press key to exit\n");
		ipcon.disconnect();
	}
}
