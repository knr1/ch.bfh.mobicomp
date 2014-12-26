/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome.remoteSwitch;

import ch.quantasy.messagebus.message.implementation.AnEvent;
import ch.quantasy.messagebus.message.implementation.AnIntent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.smarthome.WorkerFactory;
import ch.quantasy.smarthome.remoteSwitch.content.RemoteSwitchContent;
import ch.quantasy.smarthome.remoteSwitch.message.RemoteSwitch;
import ch.quantasy.smarthome.remoteSwitch.message.RemoteSwitchDecoder;
import ch.quantasy.smarthome.remoteSwitch.message.RemoteSwitchEncoder;
import ch.quantasy.smarthome.remoteSwitch.message.RemoteSwitchEvent;
import ch.quantasy.smarthome.remoteSwitch.message.RemoteSwitchIntent;
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
@ServerEndpoint(value = "/remoteswitchendpoint", encoders = {RemoteSwitchEncoder.class}, decoders = {RemoteSwitchDecoder.class})
public class RemoteSwitchWeb extends ATinkerforgeService<RemoteSwitchIntent, RemoteSwitchEvent> {

    public final static WorkerFactory factory = new WorkerFactory();

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @Override
    protected void handleIntent(RemoteSwitchIntent message) {
	//Do nothing so far
    }

    @OnMessage
    public void sendEvent(RemoteSwitch remoteSwitch, Session session) throws IOException, EncodeException {

	RemoteSwitchEvent evt = createEvent();
	evt.addContents(new RemoteSwitchContent(remoteSwitch));
	publish(evt);

	for (Session peer : peers) {
	    if (!peer.equals(session)) {
		peer.getBasicRemote().sendObject(remoteSwitch);
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
    public RemoteSwitchEvent createEvent() {
	return new Event(this);
    }

    public static RemoteSwitchIntent createIntent(Agent agent) {
	RemoteSwitchIntent intent = new Intent(agent);
	return intent;
    }

    @Override
    public String getID() {
	return "SmartHomeRemoteSwitchUI";
    }

}

class Event extends AnEvent implements RemoteSwitchEvent {

    public Event(Service eventSender) {
	super(eventSender);
    }

}

class Intent extends AnIntent implements RemoteSwitchIntent {

    public Intent(Agent intentSender) {
	super(intentSender);
    }

    public Intent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
    }

}
