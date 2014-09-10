package ch.bfh.fbi.mobiComp.tinkerforge.tutorial.step05;

import java.io.IOException;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgentIdentifier;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;

import com.tinkerforge.BrickMaster;
import com.tinkerforge.Device;

/**
 * This shows how to separate the different {@link Device}-logics. It is very
 * important that you...<br/>
 * Keep It Simple and Small -> KISS <br/>
 * This example shows that one can simply switch the address to the
 * {@link BrickMaster}. It does not care where the {@link Device} is located!
 * 
 * @author reto
 * 
 */
public class ApplicationManagement {
	public static void main(final String[] args) throws IOException,
			InterruptedException {
		System.out.println(MasterBrickApplication.class.getPackage());
		final TinkerforgeStackAgentIdentifier identifier = new TinkerforgeStackAgentIdentifier(
				"MasterBrick01");

		final TinkerforgeApplication application = new MasterBrickApplication();
		final TinkerforgeApplication motion = new MotionDetectorApplication();
		TinkerforgeStackAgency.getInstance().getStackAgent(identifier).addApplication(application);
		TinkerforgeStackAgency.getInstance().getStackAgent(identifier).addApplication(motion);
		System.out.println("Press key to exit");
		System.in.read();
		TinkerforgeStackAgency.getInstance().getStackAgent(identifier).removeApplication(application);
		TinkerforgeStackAgency.getInstance().getStackAgent(identifier).removeApplication(motion);
		
	}
}
