package ch.quantasy.tinkerforge.remoteControl;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.io.IOException;

public class RemoteControllerManager {

    public static void main(String[] args) throws IOException {
	TinkerforgeStackAddress identifier1 = new TinkerforgeStackAddress("localhost");
	TinkerforgeStackAddress identifier2 = new TinkerforgeStackAddress("Robot01");
	TinkerforgeStackAgent agent1 = TinkerforgeStackAgency.getInstance().getStackAgent(identifier1);
	TinkerforgeStackAgent agent2 = TinkerforgeStackAgency.getInstance().getStackAgent(identifier2);

	RemoteControllerApplication application = new RemoteControllerApplication();
	agent1.addApplication(application);
	agent2.addApplication(application);

	System.in.read();

	agent1.removeApplication(application);
	agent2.removeApplication(application);
    }
}
