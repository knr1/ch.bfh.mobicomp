package ch.quantasy.tinkerforge.led;

import java.io.IOException;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgentIdentifier;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;

public class LEDManager {
	public static void main(final String[] args) throws IOException {
		System.out.println("Start");
		final TinkerforgeApplication ledApp = new BlinkingLEDs();
		final TinkerforgeStackAgentIdentifier identifier = new TinkerforgeStackAgentIdentifier(
				"localhost");
		System.out.println("Starting");
		TinkerforgeStackAgency.getInstance().getStackAgent(identifier)
				.addApplication(ledApp);
		System.out.println("Running");
		System.in.read();
		TinkerforgeStackAgency.getInstance().getStackAgent(identifier)
				.removeApplication(ledApp);

	}
}
