/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome;

import ch.quantasy.smarthome.agent.LightAgent;
import ch.quantasy.smarthome.agent.RemoteSwitchAgent;
import ch.quantasy.tinkerbus.agent.device.deviceAmbientLight.TinkerforgeAmbientLightAgent;
import ch.quantasy.tinkerbus.agent.stack.TinkerforgeStackAgent;
import ch.quantasy.tinkerbus.service.location.serviceLocation.ServiceLocationService;
import ch.quantasy.tinkerbus.service.stack.registration.TinkerforgeStackRegistrationService;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class WorkerFactory {

    public static final ServiceLocationService locationService;
    public static final TinkerforgeStackRegistrationService stackRegistrationService;
    public static final TinkerforgeStackAgent stackAgent;
    public static final TinkerforgeAmbientLightAgent ambientLightAgent;
    public static final LightAgent ledAgent;
    public static final RemoteSwitchAgent remoteSwitchAgent;

    static {
	locationService = new ServiceLocationService();
	stackRegistrationService = new TinkerforgeStackRegistrationService();
	stackAgent = new TinkerforgeStackAgent();
	ambientLightAgent = new TinkerforgeAmbientLightAgent();
	ledAgent = new LightAgent();
	remoteSwitchAgent = new RemoteSwitchAgent();
	stackAgent.register();
    }

}
