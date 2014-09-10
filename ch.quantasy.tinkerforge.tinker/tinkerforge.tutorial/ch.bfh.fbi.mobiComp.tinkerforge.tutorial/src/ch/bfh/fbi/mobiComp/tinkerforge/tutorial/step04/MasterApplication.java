package ch.bfh.fbi.mobiComp.tinkerforge.tutorial.step04;

import java.io.IOException;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgentIdentifier;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;

public class MasterApplication {
	public static void main(final String[] args) throws IOException {
		System.out.println(MasterApplication.class.getPackage());
		final TinkerforgeStackAgentIdentifier identifier = new TinkerforgeStackAgentIdentifier(
				"localhost");

		final TinkerforgeApplication application = new MasterBrickAndMotionBrickletApplication();
		TinkerforgeStackAgency.getInstance().getStackAgent(identifier).addApplication(application);
		System.out.println("Press key to exit");
		System.in.read();
		TinkerforgeStackAgency.getInstance().getStackAgent(identifier).removeApplication(application);

	}
}
