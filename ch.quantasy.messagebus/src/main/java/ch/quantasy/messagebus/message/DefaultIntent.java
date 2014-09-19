/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.message;

import ch.quantasy.messagebus.worker.definition.Agent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DefaultIntent extends AMessage implements Intent {

    public DefaultIntent(Agent intentSender) {
	setSenderID(intentSender.getID());
    }

    public DefaultIntent(Agent intentSender, String... intentReceivers) {
	setSenderID(intentSender.getID());
	addReceiverIDs(intentReceivers);
    }

}
