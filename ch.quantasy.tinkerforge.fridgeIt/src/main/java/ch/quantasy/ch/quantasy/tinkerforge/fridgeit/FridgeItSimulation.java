package ch.quantasy.ch.quantasy.tinkerforge.fridgeit;

import ch.quantasy.ch.quantasy.tinkerforge.fridgeit.viewer.FridgeViewer;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.io.IOException;
import java.net.UnknownHostException;

public class FridgeItSimulation {

    // Assumes to be connected via USB
    public static final TinkerforgeStackAddress FRIDGE_VIEWER = new TinkerforgeStackAddress(
	    "localhost");

    public static void main(final String[] args) throws UnknownHostException,
	    IOException, InterruptedException {
	FridgeViewer fridgeViewer = null;
	try {
	    System.out
		    .println("Creating a new instance of a Tinkerforge Brick-Stack (USB)");
	    fridgeViewer = new FridgeViewer();
	    System.out
		    .println("Connecting to the Tinkerforge Brick-Stack via brickd");
	    TinkerforgeStackAgency.getInstance().getStackAgent(FRIDGE_VIEWER)
		    .addApplication(fridgeViewer);
	    Thread.sleep(1000);
	    System.out.println("Writing the ambient temperature: 0°C");
	    fridgeViewer.setAmbientTemp((short) 0);
	    System.out.println("Writing door status: 'open'");
	    fridgeViewer.setDoorStatus(true);
	    System.out.println("Writing light status: 'on'");
	    fridgeViewer.setLightStatus(true);
	    System.out.println("Writing the relative humidity: 10.0%");
	    fridgeViewer.setHumidity(10.0);
	    System.out
		    .println("Writing repeatitly the object temperature: (-2.0°C -> +2.0°C) with step (0.1°C)");
	    for (int i = -20; i < 20; i++) {
		fridgeViewer.setObjectTemp((short) i);
		Thread.sleep(500);
	    }
	    System.out.println("Writing door status: 'closed'");
	    System.out.println("This will trigger the 'special-alert'");
	    fridgeViewer.setDoorStatus(false);
	    Thread.sleep(4000);
	} finally {
	    System.out.println("Disconnecting Tinkerforge Brick-Stack");
	    TinkerforgeStackAgency.getInstance().getStackAgent(FRIDGE_VIEWER)
		    .removeApplication(fridgeViewer);
	    Thread.sleep(1000);
	}
    }
}
