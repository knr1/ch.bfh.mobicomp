package ch.quantasy.mqttfirst.barometer;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;

public class BarometerManager {

    public static void main(final String[] args) throws Exception {
	final TinkerforgeStackAddress identifier = new TinkerforgeStackAddress(
		"localhost");
	final TinkerforgeStackAgent agent1 = TinkerforgeStackAgency
		.getInstance().getStackAgent(identifier);

	final BarometerApplication application = new BarometerApplication();
	agent1.addApplication(application);
	System.in.read();
	agent1.removeApplication(application);
    }
}
