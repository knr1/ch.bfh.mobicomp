/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.stack;

import ch.quantasy.messagebus.message.DefaultIntent;
import ch.quantasy.messagebus.worker.definition.Agent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
class TinkerforgeStackIntent extends DefaultIntent {

    private StackConnectionIntentState stackConnectionIntentState;

    public TinkerforgeStackIntent(Agent intentSender) {
	super(intentSender);
    }

    public TinkerforgeStackIntent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
    }

    public void setStackIntentState(StackConnectionIntentState stackIntentState) {
	this.stackConnectionIntentState = stackIntentState;
    }

    public StackConnectionIntentState getStackIntentState() {
	return stackConnectionIntentState;
    }

    @Override
    public String toString() {
	return "TinkerforgeStackIntent{" + "stackConnectionIntentState=" + stackConnectionIntentState + '}';
    }

}
