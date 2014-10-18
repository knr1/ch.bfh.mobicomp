/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.message.implementation;

import ch.quantasy.messagebus.message.definition.Content;
import ch.quantasy.messagebus.message.definition.Message;
import ch.quantasy.messagebus.worker.definition.Worker;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <C>
 */
public abstract class AMessage<W extends Worker> implements Message {

    private final long serialVersionUID = 1L;

    private long timeStamp;
    private String senderID;
    private final Set<String> receiverIDs;
    private final HashMap<Class, Content> contentMap;

    public AMessage(W worker) {
	if (worker == null) {
	    throw new IllegalArgumentException();
	}
	this.senderID = worker.getID();
	receiverIDs = new HashSet<>();
	contentMap = new HashMap<>();
	setTimeStamp(System.nanoTime());

    }

    @Override
    public void addContents(Content... contents) {
	if (contents == null || contents.length == 0) {
	    throw new IllegalArgumentException();
	}
	for (Content content : contents) {
	    if (content != null) {
		this.contentMap.put(content.getClass(), content);
	    }
	}

    }

    public HashMap getContentClone() {
	return new HashMap(contentMap);
    }

    @Override
    public Content getContentByID(Class contentID) {
	return contentMap.get(contentID);
    }

    @Override
    public Collection<Content> getContents() {
	return contentMap.values();
    }

    @Override
    public boolean containsContent() {
	return !this.contentMap.isEmpty();
    }

    protected final void setSenderID(String senderID) {
	this.senderID = senderID;
    }

    protected final void setTimeStamp(long timeStamp) {
	this.timeStamp = timeStamp;
    }

    @Override
    public long getTimeStamp() {
	return this.timeStamp;
    }

    @Override
    public String getSenderID() {
	return senderID;
    }

    @Override
    public void addReceiverIDs(String... receiverIds) {
	this.receiverIDs.addAll(Arrays.asList(receiverIds));
    }

    @Override
    public boolean containsReceiverID(String receiverID) {
	if (!containsReceiverIDs()) {
	    return false;
	}
	return this.receiverIDs.contains(receiverID);
    }

    @Override
    public boolean containsReceiverIDs() {
	return !this.receiverIDs.isEmpty();

    }

    @Override
    public String toString() {
	String string = this.getID() + '{' + "timeStamp=" + timeStamp + ", senderID=" + senderID + ", receiverIDs=" + receiverIDs + '}';

	return string;
    }

    @Override
    public String getID() {
	return this.getClass().getName();
    }

}
