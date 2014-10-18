/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.message.implementation;

import ch.quantasy.messagebus.message.definition.Intent;
import ch.quantasy.messagebus.worker.definition.Agent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class AnIntent extends AMessage<Agent> implements Intent {

    public AnIntent(Agent intentSender) {
	super(intentSender);
    }

    public AnIntent(Agent intentSender, String... intentReceivers) {
	super(intentSender);
	addReceiverIDs(intentReceivers);
    }

}
