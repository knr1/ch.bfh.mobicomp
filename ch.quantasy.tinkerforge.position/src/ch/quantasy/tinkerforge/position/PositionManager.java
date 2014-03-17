package ch.quantasy.tinkerforge.position;

import java.io.IOException;

import javafx.stage.Stage;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgentIdentifier;

import com.sun.glass.ui.Platform;

public class PositionManager {
	public static void main(final String[] args) throws Exception {
		TinkerforgeStackAgentIdentifier identifier=new TinkerforgeStackAgentIdentifier("localhost");
		TinkerforgeStackAgent agent1=TinkerforgeStackAgency.getInstance().getStackAgent(identifier);
		
		new Thread(){
			public void run() {javafx.application.Application.launch(View.class,args);}
		}.start();
		
		HightSensorFusionApplication application=new HightSensorFusionApplication();
		agent1.addApplication(application);
		System.in.read();
		
		agent1.removeApplication(application);
		
	}
}
