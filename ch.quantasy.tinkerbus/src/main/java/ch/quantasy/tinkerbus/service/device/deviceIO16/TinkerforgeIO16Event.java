/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceIO16;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.messagebus.worker.definition.Service;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeIO16Event extends DefaultEvent {

    public TinkerforgeIO16Event(Service eventSender) {
	super(eventSender);
    }

}
