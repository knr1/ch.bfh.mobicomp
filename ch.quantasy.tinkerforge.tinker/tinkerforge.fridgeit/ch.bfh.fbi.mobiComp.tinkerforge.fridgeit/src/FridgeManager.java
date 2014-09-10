import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgentIdentifier;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import fridgeit.FridgeIt;

public class FridgeManager {
	// The 'server'-name of the fridge-sensor-stack
	public final TinkerforgeStackAgentIdentifier FRIDGE_SENSOR = new TinkerforgeStackAgentIdentifier(
			"MasterBrick01");
	// Assumes to be connected via USB
	public final TinkerforgeStackAgentIdentifier FRIDGE_VIEWER = new TinkerforgeStackAgentIdentifier(
			"localhost");
	private final TinkerforgeApplication fridgeIt;

	public FridgeManager() {
		this.fridgeIt = new FridgeIt();
	}

	public void start() {
		TinkerforgeStackAgency.getInstance().getStackAgent(FRIDGE_VIEWER)
				.addApplication(fridgeIt);
		TinkerforgeStackAgency.getInstance().getStackAgent(FRIDGE_SENSOR)
				.addApplication(fridgeIt);
	}

	public void stop() {
		TinkerforgeStackAgency.getInstance().getStackAgent(FRIDGE_VIEWER)
				.removeApplication(fridgeIt);
		TinkerforgeStackAgency.getInstance().getStackAgent(FRIDGE_SENSOR)
				.removeApplication(fridgeIt);
	}

	/**
	 * A simple boot-strap. The program will shut-down gracefully if one hits
	 * 'any' key on the console
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		final FridgeManager manager = new FridgeManager();
		manager.start();
		System.in.read();
		manager.stop();

	}

}
