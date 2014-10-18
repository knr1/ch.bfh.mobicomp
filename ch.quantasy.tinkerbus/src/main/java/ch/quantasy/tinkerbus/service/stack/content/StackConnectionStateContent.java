/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.stack.content;

import ch.quantasy.tinkerbus.service.content.ValueContent;
import ch.quantasy.tinkerbus.service.stack.StackConnectionState;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class StackConnectionStateContent extends ValueContent<StackConnectionState> {

    public StackConnectionStateContent(StackConnectionState value) {
	super(value);
    }

}
