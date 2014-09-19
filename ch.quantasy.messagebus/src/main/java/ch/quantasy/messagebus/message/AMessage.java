/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.message;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <M>
 */
public abstract class AMessage implements Message {

    private final long serialVersionUID = 1L;

    private long timeStamp;
    private String senderID;
    private Set<String> receiverIDs;

    protected void setSenderID(String senderID) {
	this.senderID = senderID;
    }

    protected void setTimeStamp(long timeStamp) {
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

    public void addReceiverIDs(String... receiverIds) {
	if (this.receiverIDs == null) {
	    this.receiverIDs = new HashSet<>();
	}
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
	return this.receiverIDs != null;

    }

    @Override
    public String toString() {
	return "AMessage{" + "timeStamp=" + timeStamp + ", senderID=" + senderID + ", receiverIDs=" + receiverIDs + '}';
    }

}
