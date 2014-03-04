package ch.bfh.fbi.mobiComp.tinkerforge.led;

import java.io.IOException;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgentIdentifier;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;

public class LEDManager {
	public static void main(String[] args) throws IOException {
		System.out.println("Start");
		TinkerforgeApplication ledApp=new BlinkingLEDs();
		TinkerforgeStackAgentIdentifier identifier=new TinkerforgeStackAgentIdentifier("localhost");
		System.out.println("Starting");
		TinkerforgeStackAgency.getInstance().getStackAgent(identifier).addApplication(ledApp);
		System.out.println("Running");
		System.in.read();
		TinkerforgeStackAgency.getInstance().getStackAgent(identifier).removeApplication(ledApp);
		
	}
}
