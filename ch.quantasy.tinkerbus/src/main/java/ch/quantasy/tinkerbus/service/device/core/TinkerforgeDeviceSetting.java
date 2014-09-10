/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.core;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 * @param <S>
 */
public abstract class TinkerforgeDeviceSetting<S extends TinkerforgeDeviceSetting<S>> implements Cloneable {

    @Override
    public S clone() {
	try {
	    return (S) super.clone();
	} catch (CloneNotSupportedException ex) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	}
	return null;
    }
}
