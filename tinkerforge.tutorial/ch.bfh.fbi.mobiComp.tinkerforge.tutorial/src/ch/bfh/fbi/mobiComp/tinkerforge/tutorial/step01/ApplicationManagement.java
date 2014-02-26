package ch.bfh.fbi.mobiComp.tinkerforge.tutorial.step01;

import java.io.IOException;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgentIdentifier;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;

/**
 * This class manages the {@link TinkerforgeStackAgent}s that should be taken
 * into consideration by the {@link TinkerforgeApplication}.
 * 
 * @author reto
 * 
 */
public class ApplicationManagement {
	public static void main(final String[] args) throws IOException {
		System.out.println(ApplicationManagement.class.getPackage());
		System.out
				.println("The localhost agent will be connected... That is the local USB-connection");
		System.out.println("For this, brickd must be installed and running");
		final TinkerforgeStackAgentIdentifier identifier = new TinkerforgeStackAgentIdentifier(
				"localhost");

		System.out.println("The application is instantiated...");
		final TinkerforgeApplication application = new MasterBrickApplication();

		System.out
				.println("... the application and agent are related to each other");
		TinkerforgeStackAgency.getInstance().getStackAgent(identifier).addApplication(application);

		System.out.println("Press key to exit");
		System.in.read();
		System.out
				.println("The relation between application and agent will be broken.");
		TinkerforgeStackAgency.getInstance().getStackAgent(identifier).removeApplication(application);
	}
}
