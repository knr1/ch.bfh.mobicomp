/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceIO16;

import ch.quantasy.messagebus.message.DefaultIntent;
import ch.quantasy.messagebus.worker.definition.Agent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeIO16Intent extends DefaultIntent {

    public TinkerforgeIO16Intent(Agent intentSender) {
	super(intentSender);
    }

    public TinkerforgeIO16Intent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
    }

}
