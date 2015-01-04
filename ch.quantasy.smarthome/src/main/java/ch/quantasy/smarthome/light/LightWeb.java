/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome.light;

import ch.quantasy.messagebus.message.implementation.AnEvent;
import ch.quantasy.messagebus.message.implementation.AnIntent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.smarthome.WorkerFactory;
import ch.quantasy.smarthome.light.content.ColorContent;
import ch.quantasy.smarthome.light.content.Type;
import ch.quantasy.smarthome.light.content.TypeContent;
import ch.quantasy.smarthome.light.message.LightEvent;
import ch.quantasy.smarthome.light.message.LightIntent;
import ch.quantasy.tinkerbus.bus.ATinkerforgeService;
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
@ServerEndpoint(value = "/lightendpoint", encoders = {LEDColorEncoder.class}, decoders = {LEDColorDecoder.class})
public class LightWeb extends ATinkerforgeService<LightIntent, LightEvent> {

    public final static WorkerFactory factory = new WorkerFactory();

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @Override
    protected void handleIntent(LightIntent message) {
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
    public void sendEvent(LEDColor ledColor, Session session) throws IOException, EncodeException {
	for (Type type : Type.values()) {
	    LightEvent evt = createEvent();
	    evt.addContents(new ColorContent(ledColor.getColor()));
	    evt.addContents(new TypeContent(type));
	    publish(evt);
	}
	for (Session peer : peers) {
	    if (!peer.equals(session)) {
		peer.getBasicRemote().sendObject(ledColor);
	    }
	}
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
    public LightEvent createEvent() {
	return new Event(this);
    }

    public static LightIntent createIntent(Agent agent) {
	LightIntent intent = new Intent(agent);
	return intent;
    }

    @Override
    public String getID() {
	return "SmartHomeLightUI";
    }

}

class Event extends AnEvent implements LightEvent {

    public Event(Service eventSender) {
	super(eventSender);
    }

}

class Intent extends AnIntent implements LightIntent {

    public Intent(Agent intentSender) {
	super(intentSender);
    }

    public Intent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
    }

}
