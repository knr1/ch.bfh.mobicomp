/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.worker.definition;

import ch.quantasy.messagebus.message.Event;
import ch.quantasy.messagebus.message.Intent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public interface Agent<SEND extends Intent, RECEIVE extends Event> extends Worker<SEND, RECEIVE> {
}
