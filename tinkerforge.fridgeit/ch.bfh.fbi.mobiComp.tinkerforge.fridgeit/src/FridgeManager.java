import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgentIdentifier;
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
		this.fridgeIt.addTinkerforgeStackAgent(this.FRIDGE_VIEWER);
		this.fridgeIt.addTinkerforgeStackAgent(this.FRIDGE_SENSOR);
	}

	public void stop() {
		this.fridgeIt.removeTinkerforgeStackAgent(this.FRIDGE_SENSOR);
		this.fridgeIt.removeTinkerforgeStackAgent(this.FRIDGE_VIEWER);
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
