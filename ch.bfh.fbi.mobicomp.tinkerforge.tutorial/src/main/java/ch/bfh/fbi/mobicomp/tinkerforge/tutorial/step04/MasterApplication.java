package ch.bfh.fbi.mobicomp.tinkerforge.tutorial.step04;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.io.IOException;

public class MasterApplication {

    public static void main(final String[] args) throws IOException {
	System.out.println(MasterApplication.class.getPackage());
	final TinkerforgeStackAddress identifier = new TinkerforgeStackAddress(
		"localhost");

	final TinkerforgeApplication application = new MasterBrickAndMotionBrickletApplication();
	TinkerforgeStackAgency.getInstance().getStackAgent(identifier).addApplication(application);
	System.out.println("Press key to exit");
	System.in.read();
	TinkerforgeStackAgency.getInstance().getStackAgent(identifier).removeApplication(application);

    }
}
