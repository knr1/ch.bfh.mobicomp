package ch.bfh.fbi.mobicomp.tinkerforge.tutorial.step07;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.Device;
import java.io.IOException;

/**
 * This example demonstrates that the designer of a tinkerforge setting is completely free in separating the different
 * {@link Device}s. Here Two stacks are managed by {@link TheApplication}. If there are devices found (throughout the
 * different stacks) that are used by the {@link TinkerforgeApplication}s, they will be used. No matter where they are.
 *
 * @author reto
 *
 */
public class ApplicationManagement {

    public static void main(final String[] args) throws IOException,
	    InterruptedException {
	System.out.println(MasterBrickApplication.class.getPackage());
	final TinkerforgeStackAddress identifier1 = new TinkerforgeStackAddress(
		"MasterBrick01");

	final TinkerforgeStackAddress identifier2 = new TinkerforgeStackAddress(
		"localhost");

	final TinkerforgeApplication application = new TheApplication();
	TinkerforgeStackAgency.getInstance().getStackAgent(identifier1).addApplication(application);
	TinkerforgeStackAgency.getInstance().getStackAgent(identifier2).addApplication(application);

	System.out.println("Press key to exit");
	System.in.read();
	TinkerforgeStackAgency.getInstance().getStackAgent(identifier2).removeApplication(application);
	TinkerforgeStackAgency.getInstance().getStackAgent(identifier1).removeApplication(application);

    }
}
