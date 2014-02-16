package ch.bfh.fbi.mobiComp.tinkerforge.tutorial.step05;

import java.io.IOException;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgentIdentifier;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;

public class ApplicationManagement {
	public static void main(final String[] args) throws IOException,
			InterruptedException {
		System.out.println(MasterBrickApplication.class.getPackage());
		final TinkerforgeStackAgentIdentifier identifier = new TinkerforgeStackAgentIdentifier(
				"MasterBrick01");

		final TinkerforgeApplication application = new MasterBrickApplication();
		final TinkerforgeApplication motion = new MotionDetectorApplication();
		application.addTinkerforgeStackAgent(identifier);
		motion.addTinkerforgeStackAgent(identifier);
		System.out.println("Press key to exit");
		System.in.read();
		application.removeTinkerforgeStackAgent(identifier);
		motion.removeTinkerforgeStackAgent(identifier);

	}
}
