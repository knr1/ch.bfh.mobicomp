package ch.bfh.fbi.mobiComp.tinkerforge.tutorial.step04;

import java.io.IOException;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgentIdentifier;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;

public class MasterApplication {
	public static void main(final String[] args) throws IOException {
		System.out.println(MasterApplication.class.getPackage());
		final TinkerforgeStackAgentIdentifier identifier = new TinkerforgeStackAgentIdentifier(
				"localhost");

		final TinkerforgeApplication application = new MasterBrickAndMotionBrickletApplication();
		application.addTinkerforgeStackAgent(identifier);
		System.out.println("Press key to exit");
		System.in.read();
		application.removeTinkerforgeStackAgent(identifier);

	}
}
