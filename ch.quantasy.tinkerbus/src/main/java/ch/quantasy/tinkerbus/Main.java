/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus;

import ch.quantasy.tinkerbus.agent.device.DualRelay.TinkerforgeDualRelayAgent;
import ch.quantasy.tinkerbus.agent.device.deviceLED.TinkerforgeLEDAgent;
import ch.quantasy.tinkerbus.agent.stack.TinkerforgeStackAgent;
import ch.quantasy.tinkerbus.service.location.serviceLocation.ServiceLocationService;
import ch.quantasy.tinkerbus.service.stack.registration.TinkerforgeStackRegistrationService;
import java.io.IOException;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class Main {

    public static void main(String[] args) throws IOException {
	ServiceLocationService locationService = new ServiceLocationService();
	TinkerforgeStackRegistrationService stackRegistrationService = new TinkerforgeStackRegistrationService();
	TinkerforgeStackAgent stackAgent = new TinkerforgeStackAgent();
	TinkerforgeDualRelayAgent dualRelayAgent = new TinkerforgeDualRelayAgent();
	//TinkerforgeAmbientLightAgent ambientLightAgent = new TinkerforgeAmbientLightAgent();
	//TinkerforgeDCAgent dcAgent = new TinkerforgeDCAgent();
	TinkerforgeLEDAgent ledAgent = new TinkerforgeLEDAgent();
	//TinkerforgeWavingLEDAgent ledAgent = new TinkerforgeWavingLEDAgent();
	//TinkerforgeLivingLEDAgent ledAgent = new TinkerforgeLivingLEDAgent();
	stackAgent.register();
	System.in.read();
	System.out.println(dualRelayAgent);
	System.out.println(ledAgent);
	System.out.println(stackRegistrationService);
	System.out.println(stackAgent);
    }
}
