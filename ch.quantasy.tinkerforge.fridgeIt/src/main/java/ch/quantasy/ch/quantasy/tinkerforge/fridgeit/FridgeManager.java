package ch.quantasy.ch.quantasy.tinkerforge.fridgeit;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;

public class FridgeManager {

    // The 'server'-name of the fridge-sensor-stack
    public final TinkerforgeStackAddress FRIDGE_SENSOR = new TinkerforgeStackAddress(
	    "1234567890");
    // Assumes to be connected via USB
    public final TinkerforgeStackAddress FRIDGE_VIEWER = new TinkerforgeStackAddress(
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
     * A simple boot-strap. The program will shut-down gracefully if one hits 'any' key on the console
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
