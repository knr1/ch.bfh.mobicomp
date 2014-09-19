package ch.bfh.fbi.mobicomp.tinkerforge.tutorial.step06;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.io.IOException;

public class ApplicationManagement {

    public static void main(final String[] args) throws IOException,
	    InterruptedException {
	System.out.println(MasterBrickApplication.class.getPackage());
	final TinkerforgeStackAddress identifier = new TinkerforgeStackAddress(
		"MasterBrick01");

	final TinkerforgeApplication application = new TheApplication();
	TinkerforgeStackAgency.getInstance().getStackAgent(identifier).addApplication(application);
	System.out.println("Press key to exit");
	System.in.read();
	TinkerforgeStackAgency.getInstance().getStackAgent(identifier).removeApplication(application);
    }
}
