/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome;

import ch.quantasy.messagebus.message.implementation.AnEvent;
import ch.quantasy.messagebus.message.implementation.AnIntent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.smarthome.content.ColorContent;
import ch.quantasy.smarthome.content.Type;
import ch.quantasy.smarthome.content.TypeContent;
import ch.quantasy.smarthome.message.SmartHomeEvent;
import ch.quantasy.smarthome.message.SmartHomeIntent;
import ch.quantasy.tinkerbus.agent.device.deviceAmbientLight.TinkerforgeAmbientLightAgent;
import ch.quantasy.tinkerbus.agent.stack.TinkerforgeStackAgent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeService;
import ch.quantasy.tinkerbus.service.location.serviceLocation.ServiceLocationService;
import ch.quantasy.tinkerbus.service.stack.registration.TinkerforgeStackRegistrationService;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 *
 */
@ServerEndpoint(value = "/smarthomeendpoint", encoders = {FigureEncoder.class}, decoders = {FigureDecoder.class})
public class SmartHome extends ATinkerforgeService<SmartHomeIntent, SmartHomeEvent> {

    public static final ServiceLocationService locationService;
    public static final TinkerforgeStackRegistrationService stackRegistrationService;
    public static final TinkerforgeStackAgent stackAgent;
    public static final TinkerforgeAmbientLightAgent ambientLightAgent;
    public static final LightAgent ledAgent;

    static {
	locationService = new ServiceLocationService();
	stackRegistrationService = new TinkerforgeStackRegistrationService();
	stackAgent = new TinkerforgeStackAgent();
	ambientLightAgent = new TinkerforgeAmbientLightAgent();
	//TinkerforgeDCAgent dcAgent = new TinkerforgeDCAgent();
	ledAgent = new LightAgent();
	//TinkerforgeWavingLEDAgent ledAgent = new TinkerforgeWavingLEDAgent();
	//TinkerforgeLivingLEDAgent ledAgent = new TinkerforgeLivingLEDAgent();
	stackAgent.register();
    }
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @Override
    protected void handleIntent(SmartHomeIntent message) {
	//Do nothing so far
    }

    //@OnMessage
    //public void broadcastFigure(Figure figure, Session session) throws IOException, EncodeException {
    //	System.out.println("broadcastFigure: " + figure);
    //	for (Session peer : peers) {
    //	    if (!peer.equals(session)) {
    //		peer.getBasicRemote().sendObject(figure);
    //	    }
    //	}
    //}
    @OnMessage
    public void sendEvent(Figure figure, Session session) throws IOException, EncodeException {
	for (Type type : Type.values()) {
	    SmartHomeEvent evt = createEvent();
	    evt.addContents(new ColorContent(figure.getColor()));
	    //evt.addContents(new TypeContent(figure.getType()));
	    evt.addContents(new TypeContent(type));
	    publish(evt);
	}
	for (Session peer : peers) {
	    if (!peer.equals(session)) {
		peer.getBasicRemote().sendObject(figure);
	    }
	}
	System.out.println("After Publish");

    }

    @OnOpen
    public void onOpen(Session s) {
	peers.add(s);
    }

    @OnClose
    public void onClose(Session s) {
	peers.remove(s);
    }

    @Override
    public SmartHomeEvent createEvent() {
	return new Event(this);
    }

    public static SmartHomeIntent createIntent(Agent agent) {
	SmartHomeIntent intent = new Intent(agent);
	return intent;
    }

    @Override
    public String getID() {
	return "SmartHomeUI";
    }

}

class Event extends AnEvent implements SmartHomeEvent {

    public Event(Service eventSender) {
	super(eventSender);
    }

}

class Intent extends AnIntent implements SmartHomeIntent {

    public Intent(Agent intentSender) {
	super(intentSender);
    }

    public Intent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
    }

}
