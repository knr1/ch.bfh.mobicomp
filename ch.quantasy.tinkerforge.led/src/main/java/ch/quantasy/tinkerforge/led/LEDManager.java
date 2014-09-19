package ch.quantasy.tinkerforge.led;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.io.IOException;

public class LEDManager {

    public static void main(final String[] args) throws IOException {
	System.out.println("Start");
	final TinkerforgeApplication ledApp = new BlinkingLEDs();
	final TinkerforgeStackAddress identifier = new TinkerforgeStackAddress(
		"10.0.233.120");
	System.out.println("Starting");
	TinkerforgeStackAgency.getInstance().getStackAgent(identifier)
		.addApplication(ledApp);
	System.out.println("Running");
	System.in.read();
	TinkerforgeStackAgency.getInstance().getStackAgent(identifier)
		.removeApplication(ledApp);

    }
}
