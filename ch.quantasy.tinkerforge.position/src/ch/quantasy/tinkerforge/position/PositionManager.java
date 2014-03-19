package ch.quantasy.tinkerforge.position;

import ch.quantasy.tinkerforge.position.view.GUIApplication;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgentIdentifier;

public class PositionManager {
	public static void main(final String[] args) throws Exception {
		TinkerforgeStackAgentIdentifier identifier=new TinkerforgeStackAgentIdentifier("localhost");
		TinkerforgeStackAgent agent1=TinkerforgeStackAgency.getInstance().getStackAgent(identifier);
		
		new Thread(){
			public void run() {javafx.application.Application.launch(GUIApplication.class,args);}
		}.start();
		AltitudeSensorFusionApplication application=new AltitudeSensorFusionApplication();
		agent1.addApplication(application);
		System.in.read();
		agent1.removeApplication(application);
		GUIApplication.finish();
		
	}
}
